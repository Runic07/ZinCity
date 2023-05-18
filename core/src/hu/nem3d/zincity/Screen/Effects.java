package hu.nem3d.zincity.Screen;

import hu.nem3d.zincity.Logic.CityMap;

//fire is 28th id tile
//Empty is 29th id
public class Effects {
    private CityMap map;

    Effects(CityMap map_){
        map = map_;
    }

    public void setFire(int x, int y){
        map.getEffectsLayer().getCell(x,y).setTile(map.getTileSet().getTile(28));
    }

    public void putOutFire(int x, int y){
        map.getEffectsLayer().getCell(x,y).setTile(map.getTileSet().getTile(29));
    }


}
