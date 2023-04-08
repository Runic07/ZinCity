package hu.nem3d.zincity.Misc;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import hu.nem3d.zincity.Cell.CityCell;
import hu.nem3d.zincity.Cell.IndustrialZoneCell;
import hu.nem3d.zincity.Cell.LivingZoneCell;
import hu.nem3d.zincity.Cell.ServiceZoneCell;
import hu.nem3d.zincity.Logic.CityMap;

public class Builder {
    private int buildId;
    private int code = 0;
    CityMap city;

    private int x,y;

    TiledMapTileSet tileSet;

    TiledMapTileLayer buildLayer;
    /*
    This will signify which action to do in a given UI id selection
    UIid = 1 --> zones
        ->code = 1 --> industrial zone
        ->code = 2 --> service zone
        ->code = 3 --> living zone
        ->code = 4 --> upgrade given zone
     UIid = 2 --> buildings
        ->code = 1 --> police
        ->code = 2 --> fire dep
        ->code = 3 --> arena
        ->code = 4 --> generator
        ->code = 5 --> forest
     UIid = 5 --> networks/connections
        ->code = 1 --> electricity cables
        ->code = 1 --> roads
     */

    public Builder(int id, int code_, CityMap city_){
        this.buildId = id;
        this.code = code_;
        this.city = city_;
        this.tileSet = city.getTileSet();
        this.buildLayer = city.getBuildingLayer();
    }

    public void build(CityCell cell){
        x = cell.getX();
        y = cell.getY();
        System.out.println(buildId);
        System.out.println(code);
        switch (buildId){
            case(1):
                buildZone(cell);
                break;
            case(2):
        }
        city.setBuildingLayer(buildLayer);
    }

    public void buildZone(CityCell cell){
        switch (code){
            case(1):
                cell = new IndustrialZoneCell(2);
                cell.setTile(tileSet.getTile(8));
                cell.setX(x);
                cell.setY(y);
                buildLayer.setCell(x, y, cell);
                break;
            case(2):
                cell = new ServiceZoneCell(2);
                cell.setTile(tileSet.getTile(11));
                cell.setX(x);
                cell.setY(y);
                buildLayer.setCell(x,y, cell);
                System.out.println(cell.getClass());
                break;
            case(3):
                cell = new LivingZoneCell(4);
                cell.setTile(tileSet.getTile(5));
                cell.setX(x);
                cell.setY(y);
                buildLayer.setCell(x,y, cell);
                break;

        }

    }



}
