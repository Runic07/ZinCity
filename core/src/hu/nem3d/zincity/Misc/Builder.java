package hu.nem3d.zincity.Misc;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import hu.nem3d.zincity.Cell.*;
import hu.nem3d.zincity.Logic.Citizen;
import hu.nem3d.zincity.Logic.City;
import hu.nem3d.zincity.Logic.CityMap;
import java.util.ArrayList;

/**
 * This handles the changes in a cell due to user input. Later in the code you can see which UiId and buildCode combinations refer to which action.
 */
public class Builder {
    private int UiId;
    private int buildCode = 0;
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

    /**
     * Setting default values.
     * @param id
     * @param code_
     * @param city_
     */
    public Builder(int id, int code_, City city_){
        this.UiId = id;
        this.buildCode = code_;
        this.city = city_;
        cityMap = city.getCityMap();
        this.tileSet = cityMap.getTileSet();
        this.buildLayer = cityMap.getBuildingLayer();
    }

    /**
     * We call the 4 different actions based on the UiId and after that we return the affected cells in an ArrayList.
     * @param cell
     * @return
     */
    public ArrayList<CityCell> build(CityCell cell){
        ArrayList<CityCell> cells = new ArrayList<>();
        x = cell.getX();
        y = cell.getY();
        switch (UiId){
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

    /**
     * Handles the zone Building, if you want to build a zone it checks for if its empty and then builds it.
     * If its an upgrade it doubles capacity and steps to the correct sprite based on the tier of upgrade.
     * @param cell
     * @return
     */
    public CityCell buildZone(CityCell cell){
            switch (buildCode) {
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
            switch (buildCode) {
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
                                if(i == -1){
                                    if(j == 1){
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
                                tmpCell.setTile((tileSet.getTile(16 +tmpCell.getPart().ordinal())));
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
                                if(i == -1){
                                    if(j == 1){
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
                                tmpCell.setTile((tileSet.getTile(20 + tmpCell.getPart().ordinal())));
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
            if(buildCode != 3 && buildCode != 4){
                cell.setX(x);
                cell.setY(y);
                buildLayer.setCell(x,y,cell);
                returnCells.add(cell);
            }
        }


        return  returnCells;
    }

    /**
     * Builds a road or a poweLine !!!still needs more implementation!!!.
     * @param cell
     * @return
     */
    public CityCell buildNetwork(CityCell cell){
        switch (buildCode){
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

    /**
     * Deletes a cell if its a workingZone it makes its workers jobless if its a home all occupants are removed from the workplaces and from the city itself
     * If its a 2x2 first it sets the starting cell as the NorthWest cell and deletes itself just like it build itself but it sets it as an EmptyCell instead of
     * a 2x2 cell. If its just a 1x1 and not a zone it just sets itself as an EmptyCell.
     * @param cell
     * @return
     */
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
