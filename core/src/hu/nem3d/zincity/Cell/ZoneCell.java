package hu.nem3d.zincity.Cell;

import hu.nem3d.zincity.Logic.Citizen;
import hu.nem3d.zincity.Misc.Direction;

import java.util.HashSet;
import java.util.LinkedList;
/**
 * Provides base class for Zone type cell tiles.
 * @see CityCell
 * @see com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
 */
public abstract class ZoneCell extends CityCell{
    protected int capacity;
    protected int level = 1;
    //protected final LinkedList<Citizen> occupants = new LinkedList<>(); //unnecessary, Citizens already know where they live.

    protected int occupants;

    //I'm not quite sure, whether if it's the best choice for storing this data, but it ensures the uniqueness of each element
    protected HashSet<Direction> roadDirections = new HashSet<>();

    protected ZoneCell(int capacity) {
        super();
        this.isWired = true;
        this.capacity = capacity;
    }

    protected ZoneCell(int x, int y, int capacity) {
        super(x, y);
        this.isWired = true;
        this.capacity = capacity;
    }

    //Basic getters (may add/remove some (or add setters), if that will be necessary)
    public int getCapacity() {return capacity;}

    public int getOccupants(){return occupants;}
    public int setOccupants(){return occupants;}
    public int getLevel() {return level;}
    //public int getOccCount() {return occupants.size();}
    public boolean isFull() {return capacity == occupants;}
    public HashSet<Direction> getRoadDirections() {return roadDirections;}

    //Methods revolving around roadDirections
    public boolean addDirection(Direction dir){
        return roadDirections.add(dir);
    }

    public boolean removeDirection(Direction dir){
        return roadDirections.remove(dir);
    }

    //Methods revolving around the moving of citizens
    public boolean addOccupant(){
        if(!isFull()){
            occupants++;
            return true;
        }else{
            return false;
        }
    }
    public int removeOccupant(Citizen occ){
        return --occupants;
    }

    //Only way to change the value of level, capacity (this may change later)
    //Based on the math behind the game, this setter procedure may be changed
    // to a simple multiplication or summation in that case, no parameters needed
    public boolean levelUp(int cap){
        if(level == 3){
            return false;
        }else{
            level++;
            capacity = cap;
            return true;
        }
    }
}
