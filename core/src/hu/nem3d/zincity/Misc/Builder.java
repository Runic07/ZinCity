package hu.nem3d.zincity.Misc;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import hu.nem3d.zincity.Cell.*;
import hu.nem3d.zincity.Logic.Citizen;
import hu.nem3d.zincity.Logic.City;
import hu.nem3d.zincity.Logic.CityMap;
import java.util.ArrayList;

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

    public ArrayList<CityCell> build(CityCell cell){
        ArrayList<CityCell> cells = new ArrayList<>();
        x = cell.getX();
        y = cell.getY();
        switch (buildId){
            case(1):
                cells.add(buildZone(cell));
                break;
            case(2):
                cells = buildSpecial(cell);
                break;
            case(5):
                cells.add(buildNetwork(cell));
                break;
            case(6):
                cells = deleteCell(cell);
                break;
        }
        cityMap.setBuildingLayer(buildLayer);
        return cells;
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
                    boolean isFree = true;
                    for(int i = 0; i > -2; i-- ){
                        for(int j = 0; j < 2; j++){
                            if(x+j < 0 || x+j > 29 || y + i < 0 || y - i > 19){
                                isFree = false;
                            }
                            else if(buildLayer.getCell(x + j, y + i).getClass() != EmptyCell.class){
                                isFree = false;
                            }
                        }
                    }
                    if(isFree){
                        cell = new ArenaCell(3, 100);
                        ((ArenaCell) cell).setPart(BuildingCell.BuildingPart.valueOf("NorthWest"));
                        for(int i = 0; i > -2; i-- ){
                            for(int j = 0; j < 2; j++){
                                ArenaCell tmpCell = new ArenaCell(3, 100);
                                int step = j;
                                if(i == -1){
                                    step = 1;
                                    if(j == 1){
                                        step = 2;
                                        tmpCell.setPart(BuildingCell.BuildingPart.valueOf("SouthEast"));
                                    }
                                    else{
                                        tmpCell.setPart(BuildingCell.BuildingPart.valueOf("SouthWest"));
                                    }
                                }
                                else{
                                    if(j == 1){
                                        tmpCell.setPart(BuildingCell.BuildingPart.valueOf("NorthEast"));
                                    }
                                    else{
                                        tmpCell.setPart(BuildingCell.BuildingPart.valueOf("NorthWest"));
                                    }
                                }
                                tmpCell.setTile((tileSet.getTile(16 + step + (i *-1))));
                                buildLayer.setCell(x + j,y + i, tmpCell);
                                tmpCell.setX(x+j);
                                tmpCell.setY(y+i);
                                returnCells.add(tmpCell);
                            }
                        }
                        cell.setTile(tileSet.getTile(16));
                        cell.setX(x);
                        cell.setY(y);
                        returnCells.add(cell);
                    }
                    break;
                case(4):
                    boolean isFreeGen = true;
                    for(int i = 0; i > -2; i-- ){
                        for(int j = 0; j < 2; j++){
                            if(x + j < 0 || x + j > 29 || y + i < 0 || y - i > 19){
                                isFreeGen = false;
                            }
                            else if(buildLayer.getCell(x + j, y + i).getClass() != EmptyCell.class){
                                isFreeGen = false;
                            }
                        }
                    }
                    if(isFreeGen){
                        cell = new GeneratorCell(3, 200);
                        ((GeneratorCell) cell).setPart(BuildingCell.BuildingPart.valueOf("NorthWest"));;
                        for(int i = 0; i > -2; i-- ){
                            for(int j = 0; j < 2; j++){
                                GeneratorCell tmpCell = new GeneratorCell(3, 200);
                                int step = j;
                                if(i == -1){
                                    step = 1;
                                    if(j == 1){
                                        step = 2;
                                        tmpCell.setPart(BuildingCell.BuildingPart.valueOf("SouthEast"));
                                    }
                                    else{
                                        tmpCell.setPart(BuildingCell.BuildingPart.valueOf("SouthWest"));
                                    }
                                }
                                else{
                                    if(j == 1){
                                        tmpCell.setPart(BuildingCell.BuildingPart.valueOf("NorthEast"));
                                    }
                                    else{
                                        tmpCell.setPart(BuildingCell.BuildingPart.valueOf("NorthWest"));
                                    }
                                }
                                tmpCell.setTile((tileSet.getTile(20 + step + (i *-1))));
                                buildLayer.setCell(x + j,y + i, tmpCell);
                                tmpCell.setX(x+j);
                                tmpCell.setY(y+i);
                                returnCells.add(tmpCell);
                            }
                        }
                        cell.setTile(tileSet.getTile(20));
                        cell.setX(x);
                        cell.setY(y);
                        returnCells.add(cell);
                    }
                    break;


                case(5):
                    cell = new ForestCell(x,y);
                    cell.setTile((tileSet.getTile(2)));
                    break;
            }
            if(code != 3 && code != 4){
                cell.setX(x);
                cell.setY(y);
                buildLayer.setCell(x,y,cell);
                returnCells.add(cell);
            }
        }


        return  returnCells;
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

    public ArrayList <CityCell> deleteCell(CityCell cell){
        ArrayList <CityCell> returnCells = new ArrayList<>();
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
            if(cell.getClass() == ArenaCell.class || cell.getClass() == GeneratorCell.class){
                BuildingCell.BuildingPart part = ((BuildingCell) cell).getPart();
                if(part == BuildingCell.BuildingPart.valueOf("NorthEast")){
                    x = x - 1;
                }
                else if(part == BuildingCell.BuildingPart.valueOf("SouthWest")){
                    y = y + 1;
                }
                else if(part == BuildingCell.BuildingPart.valueOf("SouthEast")){
                    x = x - 1;
                    y = y + 1;
                }

                for(int i = 0; i > -2; i-- ){
                    for(int j = 0; j < 2; j++){
                        EmptyCell tmpCell = new EmptyCell();
                        tmpCell.setTile((tileSet.getTile(0)));
                        buildLayer.setCell(x + j,y + i, tmpCell);
                        tmpCell.setX(x+j);
                        tmpCell.setY(y+i);
                        returnCells.add(tmpCell);
                    }
                }

            }
            cell = new EmptyCell();
            cell.setTile((tileSet.getTile(0)));
            cell.setX(x);
            cell.setY(y);
            buildLayer.setCell(x, y, cell);
        }
        return  returnCells;
    }

}
