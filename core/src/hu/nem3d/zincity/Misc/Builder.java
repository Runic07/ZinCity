package hu.nem3d.zincity.Misc;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import hu.nem3d.zincity.Cell.*;
import hu.nem3d.zincity.Logic.Citizen;
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
     UIid = 6 --> delete
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
            case(6):
                cell = deleteCell(cell);
                break;
        }
        cityMap.setBuildingLayer(buildLayer);
        return cell;
    }

    public CityCell buildZone(CityCell cell){
            switch (code) {
                case (1):
                    if(cell.getClass() == EmptyCell.class) {
                        cell = new IndustrialZoneCell(2);
                        cell.setTile(tileSet.getTile(8));
                        cell.setX(x);
                        cell.setY(y);
                        buildLayer.setCell(x, y, cell);
                    }
                    break;
                case (2):
                    if(cell.getClass() == EmptyCell.class) {
                        cell = new ServiceZoneCell(2);
                        cell.setTile(tileSet.getTile(11));
                        cell.setX(x);
                        cell.setY(y);
                        buildLayer.setCell(x, y, cell);
                    }
                    break;
                case (3):
                    if(cell.getClass() == EmptyCell.class) {
                        cell = new LivingZoneCell(4);
                        cell.setTile(tileSet.getTile(5));
                        cell.setX(x);
                        cell.setY(y);
                        buildLayer.setCell(x, y, cell);
                    }
                    break;
                case(4):
                    //TODO implement price and budget changes!
                    if(LivingZoneCell.class == cell.getClass()){
                        if(((LivingZoneCell) cell).levelUp(((LivingZoneCell) cell).getCapacity() * 2)) {
                            cell.setTile(tileSet.getTile(5 + ((LivingZoneCell) cell).getLevel() - 1 ));
                        }
                    }
                    else if(ServiceZoneCell.class == cell.getClass()){
                        if(((ServiceZoneCell) cell).levelUp(((ServiceZoneCell) cell).getCapacity() * 2)){
                            cell.setTile(tileSet.getTile(11 + ((ServiceZoneCell) cell).getLevel() - 1 ));
                        }
                    }
                    else if(IndustrialZoneCell.class == cell.getClass()){
                        if(((IndustrialZoneCell) cell).levelUp(((IndustrialZoneCell) cell).getCapacity() * 2)){
                            cell.setTile(tileSet.getTile(8 + ((IndustrialZoneCell) cell).getLevel() - 1));
                        }
                    }
                    cell.setX(x);
                    cell.setY(y);
                    buildLayer.setCell(x, y, cell);
                    break;
            }
        return cell;

    }

    /**
     * For 1x1 cells its the same as seen in buildZone, but in 2x2 cells it starts form northWest checks if its free and NE and SW and SE is free, if it is
     * than it builds on them and sets its place in the building(NW, NE, SW, SE) and its properties.
     * With step the setting of step is steps the correct number in the sprites id
     * to get to the correct sprite for the building.
     * 
     * @param cell
     * @return
     */

    public ArrayList<CityCell> buildSpecial(CityCell cell){
        ArrayList <CityCell> returnCells = new ArrayList<>();
        if(cell.getClass() == EmptyCell.class) {
            switch (code) {
                case(1):
                    cell = new PoliceCell(2,100);
                    cell.setTile((tileSet.getTile(14)));
                    break;
                case(2):
                    cell = new FireStationCell(2,100);
                    cell.setTile((tileSet.getTile(15)));
                    break;
                case(3):

                case(5):
                    cell = new ForestCell(x,y);
                    cell.setTile((tileSet.getTile(2)));
                    break;
            }
            cell.setX(x);
            cell.setY(y);
            buildLayer.setCell(x,y,cell);
        }


        return  cell;
    }

    public CityCell buildNetwork(CityCell cell){
        switch (code){
            case(2):
                if(cell.getClass() == EmptyCell.class) {
                    cell = new RoadCell();
                    cell.setTile((tileSet.getTile(4)));
                    cell.setX(x);
                    cell.setY(y);
                    buildLayer.setCell(x, y, cell);
                }
                break;
        }
        return cell;
    }

    public CityCell deleteCell(CityCell cell){
        if(cell.getClass() != BlockedCell.class){   //TODO implement conflicting delete here in if --> example: cell.ConflictDelete in if statement
            if(cell.getClass() == LivingZoneCell.class){
                for (Citizen citizen : city.citizens) {
                    if(citizen.getHome().getX() == y && citizen.getHome().getY() == x){
                        citizen.getWorkplace().removeOccupant(citizen);
                        citizen.setWorkplace(null);
                        citizen.setHome(null);
                        city.citizens.remove(citizen);
                    }
                }
            }
            else if(cell.getClass() == ServiceZoneCell.class || cell.getClass() == IndustrialZoneCell.class){
                for (Citizen citizen : city.citizens) {
                    if(citizen.getWorkplace().getX() == y && citizen.getWorkplace().getY() == x){
                        citizen.getWorkplace().removeOccupant(citizen);
                        citizen.setWorkplace(null);
                    }
                }
            }
            cell = new EmptyCell();
            cell.setTile((tileSet.getTile(0)));
            cell.setX(x);
            cell.setY(y);
            buildLayer.setCell(x, y, cell);
        }
      return cell;
    }

}
