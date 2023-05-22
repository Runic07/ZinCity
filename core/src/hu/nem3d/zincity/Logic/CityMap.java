package hu.nem3d.zincity.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import hu.nem3d.zincity.Cell.*;
import hu.nem3d.zincity.Misc.*;


import java.util.Random;


/**
 * Responsible for all operations related to generating, and storing the game map.
 */
public class CityMap {
    TiledMap map;
    TiledMapTileLayer baseLayer; //contains Cell objects that point to TiledMapTile objects

    TiledMapTileLayer buildingLayer; //more layers can be added on demand
    transient TiledMapTileSet tileSet; //contains TiledMapTile objects.
    transient Texture texture;

    Builder builder;
    /**
     * Generates a 30x20 map with random Simplex-noise, adds water, trees and grass.
     * Generates map layers for buildings and base tiles.
     *
     *
     */
    public CityMap() {

        FileHandle handle = Gdx.files.internal("texture.png");
        texture = new Texture(handle.path());
        Builder builder = new Builder(0,0,null);

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

        //generate starter city
        //rendering is still bugged here
        boolean starterCityGenerated = false;
        while(!starterCityGenerated){
            i = r.nextInt(5,25);
            j = r.nextInt(5, 15);
            if ( //

                    buildingLayer.getCell(i+1,j-1) instanceof EmptyCell &&
                    buildingLayer.getCell(i+1,j) instanceof EmptyCell &&
                    buildingLayer.getCell(i+1,j+1) instanceof EmptyCell &&
                    buildingLayer.getCell(i,j-1) instanceof EmptyCell &&
                    buildingLayer.getCell(i,j) instanceof EmptyCell &&
                    buildingLayer.getCell(i,j+1) instanceof EmptyCell &&
                    buildingLayer.getCell(i-1,j-1) instanceof EmptyCell &&
                    buildingLayer.getCell(i-1,j) instanceof EmptyCell &&
                    buildingLayer.getCell(i-1,j+1) instanceof EmptyCell
            ) {
                starterCityGenerated = true;
                System.out.println("Found suitable place");
                try {

                    buildingLayer.setCell(i + 1, j, new RoadCell(i + 1, j, buildingLayer));
                    buildingLayer.getCell(i + 1, j).setTile((tileSet.getTile(4)));

                    buildingLayer.setCell(i, j - 1, new RoadCell(i, j - 1, buildingLayer));
                    buildingLayer.getCell(i, j - 1).setTile((tileSet.getTile(4)));

                    buildingLayer.setCell(i, j, new RoadCell(i, j, buildingLayer));
                    buildingLayer.getCell(i, j).setTile((tileSet.getTile(4)));

                    buildingLayer.setCell(i, j + 1, new RoadCell(i, j + 1, buildingLayer));
                    buildingLayer.getCell(i, j + 1).setTile((tileSet.getTile(4)));

                    buildingLayer.setCell(i - 1, j, new RoadCell(i - 1, j, buildingLayer));
                    buildingLayer.getCell(i - 1, j).setTile((tileSet.getTile(4)));

                    buildingLayer.setCell(i + 1, j - 1, new LivingZoneCell(i + 1, j - 1, buildingLayer, false));
                    buildingLayer.getCell(i + 1, j - 1).setTile((tileSet.getTile(24)));

                    buildingLayer.setCell(i + 1, j + 1, new LivingZoneCell(i + 1, j + 1, buildingLayer, false));
                    buildingLayer.getCell(i + 1, j + 1).setTile((tileSet.getTile(24)));

                    buildingLayer.setCell(i - 1, j - 1, new ServiceZoneCell(i - 1, j - 1, buildingLayer, false));
                    buildingLayer.getCell(i - 1, j - 1).setTile((tileSet.getTile(26)));

                    buildingLayer.setCell(i - 1, j + 1, new IndustrialZoneCell(i - 1, j + 1, buildingLayer, false));
                    buildingLayer.getCell(i - 1, j + 1).setTile((tileSet.getTile(25)));

                } catch (CellException e) {
                    System.err.println("failed building starter city");
                }
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
        for (int i = 0; i < buildingLayer.getHeight(); i++) {
            for (int j = 0; j < buildingLayer.getWidth(); j++) {
                if (buildingLayer.getCell(i,j) instanceof ServiceZoneCell){
                    count++;
                }
            }

        }
        return count;
    }

    public int IndustrialZoneCount(){
        int count = 0;
        for (int i = 0; i < buildingLayer.getHeight(); i++) {
            for (int j = 0; j < buildingLayer.getWidth(); j++) {
                if (buildingLayer.getCell(i,j) instanceof IndustrialZoneCell){
                    count++;
                }
            }

        }
        return count;
    }

    public TiledMapTile tileType(CityCell cell){
        if (cell.getClass() == EmptyCell.class ){
            return tileSet.getTile(0);
        }
        else if(cell.getClass() == BlockedCell.class){
            return tileSet.getTile(1);
        }
        else if(cell.getClass() == ForestCell.class){
            if(((ForestCell) cell).getAge() >= 10){
                System.out.println("forrest");
                return tileSet.getTile(3);
            }
            else{
                return tileSet.getTile(2);
            }
        }
        else if(cell.getClass() == RoadCell.class){
            return tileSet.getTile(4);
        }
        else if(cell.getClass() == LivingZoneCell.class){
            if(((LivingZoneCell) cell).getLevel() == 1 && ((LivingZoneCell) cell).getOccupants() < ((LivingZoneCell) cell).getCapacity() / 2){
                return tileSet.getTile(24);
            }
            else if(((LivingZoneCell) cell).getLevel() == 1){
                return tileSet.getTile(5);
            }
            else if(((LivingZoneCell) cell).getLevel() == 2){
                return tileSet.getTile(6);
            }
            else if(((LivingZoneCell) cell).getLevel() == 3){
                return tileSet.getTile(7);
            }
        }
        else if(cell.getClass() == IndustrialZoneCell.class){
            if(((IndustrialZoneCell) cell).getLevel() == 1 && ((IndustrialZoneCell) cell).getOccupants() < ((IndustrialZoneCell) cell).getCapacity() / 2){
                return tileSet.getTile(25);
            }
            else if(((IndustrialZoneCell) cell).getLevel() == 1){
                return tileSet.getTile(8);
            }
            else if(((IndustrialZoneCell) cell).getLevel() == 2){
                return tileSet.getTile(9);
            }
            else if(((IndustrialZoneCell) cell).getLevel() == 3){
                return tileSet.getTile(10);
            }
        }
        else if(cell.getClass() == ServiceZoneCell.class){
            if(((ServiceZoneCell) cell).getLevel() == 1 && ((ServiceZoneCell) cell).getOccupants() < ((ServiceZoneCell) cell).getCapacity() / 2){
                return tileSet.getTile(26);
            }
            else if(((ServiceZoneCell) cell).getLevel() == 1){
                return tileSet.getTile(11);
            }
            else if(((ServiceZoneCell) cell).getLevel() == 2){
                return tileSet.getTile(12);
            }
            else if(((ServiceZoneCell) cell).getLevel() == 3){
                return tileSet.getTile(13);
            }
        }
        else if(cell.getClass() == PoliceCell.class){
            return tileSet.getTile(14);
        }
        else if(cell.getClass() == FireStationCell.class){
            return tileSet.getTile(15);
        }
        else if(cell.getClass() == ArenaCell.class){
            if(((ArenaCell) cell).getPart() == BuildingCell.BuildingPart.NorthWest){
                return tileSet.getTile(16);
            }
            else if(((ArenaCell) cell).getPart() == BuildingCell.BuildingPart.NorthEast){
                return tileSet.getTile(17);
            }
            else if(((ArenaCell) cell).getPart() == BuildingCell.BuildingPart.SouthWest){
                return tileSet.getTile(18);
            }
            else if(((ArenaCell) cell).getPart() == BuildingCell.BuildingPart.SouthEast){
                return tileSet.getTile(19);
            }
        }
        else if(cell.getClass() == GeneratorCell.class){
            if(((GeneratorCell) cell).getPart() == BuildingCell.BuildingPart.NorthWest){
                return tileSet.getTile(20);
            }
            else if(((GeneratorCell) cell).getPart() == BuildingCell.BuildingPart.NorthEast){
                return tileSet.getTile(21);
            }
            else if(((GeneratorCell) cell).getPart() == BuildingCell.BuildingPart.SouthWest){
                return tileSet.getTile(22);
            }
            else if(((GeneratorCell) cell).getPart() == BuildingCell.BuildingPart.SouthEast){
                return tileSet.getTile(23);
            }

        }



        return null;
    }

}
