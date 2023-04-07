package hu.nem3d.zincity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import java.util.Random;





public class CityMap {
    TiledMap map;
    TiledMapTileLayer baseLayer; //contains Cell objects that point to TiledMapTile objects
    TiledMapTileLayer buildingLayer; //more layers can be added on demand
    TiledMapTileSet tileSet; //contains TiledMapTile objects.
    Texture texture;
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
                CityCell cell = new CityCell();
                CityCell forestCell = new CityCell();

                if (OpenSimplex2S.noise2(seedWater, i*0.05, j*0.05) > -0.3){ //change these threshold values to modify world gen
                    cell.setTile(tileSet.getTile(0));
                }
                else{
                    cell.setTile(tileSet.getTile(1));
                }
                baseLayer.setCell(i, j, cell);

                if (OpenSimplex2S.noise2(seedTrees, i*0.05, j*0.05) > 0.65  ){
                    //add dense forest
                    forestCell.setTile((tileSet.getTile(3)));
                }
                else if (OpenSimplex2S.noise2(seedTrees, i*0.05, j*0.05) > 0.5){
                    //add sparse forest
                    forestCell.setTile((tileSet.getTile(2)));
                }

                buildingLayer.setCell(i,j,forestCell);

            }
        }



        map = new TiledMap();
        map.getLayers().add(baseLayer);
        map.getLayers().add(buildingLayer);
    }
    public TiledMap getMap(){
        return map;
    }
}
