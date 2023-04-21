package hu.nem3d.zincity.Misc;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import hu.nem3d.zincity.Cell.*;
import hu.nem3d.zincity.Logic.City;
import hu.nem3d.zincity.Logic.CityMap;

public class Builder {
    private int buildId;
    private int code = 0;
    CityMap cityMap;

    City city;

    private int x,y;

    TiledMapTileSet tileSet;

    TiledMapTileLayer buildLayer;
    //TODO for all of the build...(cell) functions budget and fee calc and ifs but since city is here it is easy to do but no specs as of now.

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

    public Builder(int id, int code_, City city_){
        this.buildId = id;
        this.code = code_;
        this.city = city_;
        cityMap = city.getCityMap();
        this.tileSet = cityMap.getTileSet();
        this.buildLayer = cityMap.getBuildingLayer();
    }

    public CityCell build(CityCell cell){
        x = cell.getX();
        y = cell.getY();
        switch (buildId){
            case(1):
                cell = buildZone(cell);
                break;
            case(2):
                cell = buildSpecial(cell);
                break;
            case(5):
                cell = buildNetwork(cell);
                break;
        }
        cityMap.setBuildingLayer(buildLayer);
        return cell;
    }

    public CityCell buildZone(CityCell cell){
        if(cell.getClass() == EmptyCell.class) {
            switch (code) {
                case (1):
                    try {
                        cell = new IndustrialZoneCell(x,y,buildLayer, 2);
                    } catch (CellException e) {
                        System.err.println("Can't build that there");
                    }
                    cell.setTile(tileSet.getTile(8));

                    buildLayer.setCell(x, y, cell);
                    break;
                case (2):
                    try {
                        cell = new ServiceZoneCell(x,y,buildLayer,2);
                    } catch (CellException e) {
                        System.err.println("Can't build that there");
                    }
                    cell.setTile(tileSet.getTile(11));

                    buildLayer.setCell(x, y, cell);
                    break;
                case (3):
                    try {
                        cell = new LivingZoneCell(x,y,buildLayer,4 );
                    } catch (CellException e) {
                        System.err.println("Can't build that there");
                    }
                    cell.setTile(tileSet.getTile(5));

                    buildLayer.setCell(x, y, cell);
                    break;
                case(4):
                    //TODO write upgrade method in each zone in there respectice classes!!!
                    break;
            }
        }
        return cell;

    }

    public CityCell buildSpecial(CityCell cell){ //can only do forest since this is the only one implemented
        if(cell.getClass() == EmptyCell.class) {
            switch (code) {
                case(5):
                    cell = new ForestCell(x,y, buildLayer);
                    cell.setTile((tileSet.getTile(2)));
                    buildLayer.setCell(x,y,cell);
                    break;
            }
        }


        return  cell;
    }

    public CityCell buildNetwork(CityCell cell){
        switch (code){
            case(2):
                if(cell.getClass() == EmptyCell.class) {
                    cell = new RoadCell(x,y, buildLayer);
                    cell.setTile((tileSet.getTile(4)));

                    buildLayer.setCell(x, y, cell);
                }
                break;
        }
        return cell;
    }


}
