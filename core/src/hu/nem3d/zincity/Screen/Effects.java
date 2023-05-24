package hu.nem3d.zincity.Screen;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Cell.CityCell;
import hu.nem3d.zincity.Logic.CityMap;

//fire is 28th id tile
//Empty is 30th id
public class Effects {
    private CityMap map;

    public Effects(CityMap map_){
        map = map_;
    }

    public void setOnFire(int x, int y){

        map.getEffectsLayer().getCell(x,y).setTile(map.getTileSet().getTile(28));
        CityCell cell = (CityCell) map.getBuildingLayer().getCell(x,y);
        cell.setFire(true);
    }

    public void putOutFire(int x, int y){
        map.getEffectsLayer().getCell(x,y).setTile(map.getTileSet().getTile(30));
        CityCell cell = (CityCell) map.getBuildingLayer().getCell(x,y);
        cell.setFire(false);
    }


}
