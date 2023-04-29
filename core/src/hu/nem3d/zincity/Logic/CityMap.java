package hu.nem3d.zincity.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import hu.nem3d.zincity.Cell.*;
import hu.nem3d.zincity.Misc.OpenSimplex2S;
import hu.nem3d.zincity.Misc.TextureTiles;

import java.util.Random;


/**
 * Responsible for all operations related to generating, and storing the game map.
 */
public class CityMap {
    TiledMap map;
    TiledMapTileLayer baseLayer; //contains Cell objects that point to TiledMapTile objects

    TiledMapTileLayer buildingLayer; //more layers can be added on demand
    TiledMapTileSet tileSet; //contains TiledMapTile objects.
    Texture texture;

    /**
     * Generates a 30x20 map with random Simplex-noise, adds water, trees and grass.
     * Generates map layers for buildings and base tiles.
     *
     *
     */
    public CityMap() {
        FileHandle handle = Gdx.files.internal("texture.png");
        texture = new Texture(handle.path());

        //create the tileset
        tileSet = new TiledMapTileSet();
        int i = 0, j = 0;
        for (TextureTiles item : TextureTiles.values()) {
            if (i > 9){ //since there are 10 columns in the texture file.
                i = 0;
                j++;
            }
            tileSet.putTile(item.ordinal(), new StaticTiledMapTile(new TextureRegion(texture, i*24, j*24, 24,24)));
            i++;

        }

        //create the layer
        baseLayer = new TiledMapTileLayer(30,20,24,24);
        buildingLayer = new TiledMapTileLayer(30,20,24,24);

        Random r = new Random();
        long seedWater = r.nextLong();
        long seedTrees = r.nextLong();
        for ( i = 0; i < 30; i++) {
            for ( j = 0; j < 20; j++) {
                boolean isEmpty = false;
                if (OpenSimplex2S.noise2(seedWater, i*0.05, j*0.05) > -0.3){ //change these threshold values to modify world gen
                    CityCell cell = new EmptyCell(i,j, baseLayer);
                    cell.setTile(tileSet.getTile(0));
                    cell.setX(i);
                    cell.setY(j);
                    baseLayer.setCell(i, j, cell);
                    isEmpty = true;
                }
                else{
                    CityCell cell = new BlockedCell(i,j,baseLayer, "");
                    cell.setTile(tileSet.getTile(1));
                    cell.setX(i);
                    cell.setY(j);
                    baseLayer.setCell(i, j, cell);
                }

                if (OpenSimplex2S.noise2(seedTrees, i*0.05, j*0.05) > 0.7  ){
                    //add dense forest
                    CityCell cell = new ForestCell(i,j, buildingLayer);
                    cell.setTile((tileSet.getTile(3)));
                    cell.setX(i);
                    cell.setY(j);
                    buildingLayer.setCell(i,j,cell);
                }
                else if (OpenSimplex2S.noise2(seedTrees, i*0.05, j*0.05) > 0.6){
                    //add sparse forest
                    CityCell cell = new ForestCell(i,j, buildingLayer);
                    cell.setTile((tileSet.getTile(2)));
                    cell.setX(i);
                    cell.setY(j);
                    buildingLayer.setCell(i,j,cell);
                }
                else if(isEmpty){
                    CityCell cell = new EmptyCell(i,j,buildingLayer);
                    cell.setX(i);
                    cell.setY(j);
                    buildingLayer.setCell(i, j, cell);
                }
                else{
                    CityCell cell = new BlockedCell(i,j,buildingLayer, "");
                    cell.setX(i);
                    cell.setY(j);
                    buildingLayer.setCell(i, j, cell);
                }



            }
        }
        //generate beginner city
        boolean isStarterCityGenerated = false;
        while (!isStarterCityGenerated){
            int x = r.nextInt(3,27);
            int y = r.nextInt(3,17);
            if (buildingLayer.getCell(x, y) == null && baseLayer.getCell(x,y) instanceof EmptyCell){ //TODO this is never entered!

                //clears a 5*3 area
                for (i = -2; i <= 2; i++){
                    for (j = -1; j <=1; j++){
                        baseLayer.setCell(i + x,j + y,new EmptyCell(i+ x,j + y,baseLayer));
                    }

                    buildingLayer.setCell(i,y,new RoadCell(i,y,buildingLayer));
                }
                try{
                    buildingLayer.setCell(x+1, y, new LivingZoneCell(x+1, y, buildingLayer));
                    buildingLayer.setCell(x+2, y, new LivingZoneCell(x+1, y, buildingLayer));
                    buildingLayer.setCell(x, y-1, new ServiceZoneCell(x, y-1, buildingLayer));
                    buildingLayer.setCell(x, y+1, new IndustrialZoneCell(x, y+1, buildingLayer));

                    isStarterCityGenerated = true;
                } catch (Exception e){}



            }

            if (x == 3){
                isStarterCityGenerated = true;
            }
        }


        map = new TiledMap();
        map.getLayers().add(baseLayer);
        map.getLayers().add(buildingLayer);

    }
    public TiledMap getMap() {
        return map;
    }

    public TiledMapTileLayer getBuildingLayer() {
        return buildingLayer;
    }

    public void setBuildingLayer(TiledMapTileLayer buildingLayer) {
        this.buildingLayer = buildingLayer;
    }

    public TiledMapTileSet getTileSet() {
        return tileSet;
    }

    public int ServiceZoneCount(){
        int count = 0;
        for (int i = 0; i < buildingLayer.getWidth(); i++) {
            for (int j = 0; j < buildingLayer.getHeight(); j++) {
                if (buildingLayer.getCell(i,j) instanceof ServiceZoneCell){
                    count++;
                }
            }

        }
        return count;
    }

    public int IndustrialZoneCount(){
        int count = 0;
        for (int i = 0; i < buildingLayer.getWidth(); i++) {
            for (int j = 0; j < buildingLayer.getHeight(); j++) {
                if (buildingLayer.getCell(i,j) instanceof IndustrialZoneCell){
                    count++;
                }
            }

        }
        return count;
    }

}
