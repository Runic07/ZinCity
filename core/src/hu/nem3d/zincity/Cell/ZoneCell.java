package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Logic.Citizen;
import hu.nem3d.zincity.Misc.CellException;
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
    //im not sure if this is even useful - jaksy


    /**
     * Constructs an ZoneCell with coordinates and capacity set from parameters
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     * @param capacity The maximum number of occupants this zone can haves
     */

    protected ZoneCell(int x, int y, TiledMapTileLayer tileLayer, int capacity) throws CellException {
        super(x, y, tileLayer);
        this.upkeepCost = 0;

        if ((hasRoadNeighbor() && tileLayer.getCell(x,y).getClass() == EmptyCell.class))
        {
            this.isWired = true;
            this.capacity = capacity;
        }
        else{
            throw new CellException("Building failed - no road neighbors");
        }
    }

    private boolean hasRoadNeighbor(){
        return (this.getNeighbor(Direction.NORTH) != null && this.getNeighbor(Direction.NORTH).getClass() == RoadCell.class ||
                this.getNeighbor(Direction.SOUTH) != null && this.getNeighbor(Direction.SOUTH).getClass() == RoadCell.class ||
                this.getNeighbor(Direction.EAST) != null && this.getNeighbor(Direction.EAST).getClass() == RoadCell.class ||
                this.getNeighbor(Direction.WEST) != null && this.getNeighbor(Direction.WEST).getClass() == RoadCell.class);
    }
    //Basic getters (may add/remove some (or add setters), if that will be necessary)

    /**
     * Gets the capacity of this
     * @return The maximum number of occupants this zone can haves
     */
    public int getCapacity() {return capacity;}

    /**
     * Gets the number of Citizens that belongs to this cell
     * @return the number of Citizens that belongs to this cell
     */
    public int getOccupants(){return occupants;}

    /**
     * Changes the number of occupants of this to value of the parameter (if it is less than the capacity)
     * @param occ The number of occupants this will have
     * @return True, if this action is successful
     */
    public boolean setOccupants(int occ){
        if(occ <= capacity && occ >= 0){
            this.occupants = occupants;
            return true;
        }
        return false;
    }

    /**
     * Gets the current level of this ZoneCell
     * @return The value, between 1 and 3, of level of this
     */
    public int getLevel() {return level;}
    //public int getOccCount() {return occupants.size();}

    /**
     * Returns true, if the number of occupants reached the value of capacity
     * @return True, if the number of occupants is equal to the capacity
     */
    public boolean isFull() {return capacity == occupants;}

    /**
     * Returns the set that contains the directions which this ZoneCell has access to a RoadCell
     * @return The HashSet that contains the directions which this ZoneCell has access to a RoadCell
     */
    public HashSet<Direction> getRoadDirections() {return roadDirections;}

    //Methods revolving around roadDirections
    /**
     * Adds a direction to the set of directions
     * @param dir The direction of the RoadCell, this gained access to
     * @return True, if this action is successful
     */
    public boolean addDirection(Direction dir){
        return roadDirections.add(dir);
    }

    /**
     * Removes a direction from the set of directions
     * @param dir The direction of the RoadCell, this lost access to
     * @return True, if this action is successful
     */
    public boolean removeDirection(Direction dir){
        return roadDirections.remove(dir);
    }

    //Methods revolving around the moving of citizens
    /**
     * Increases occupant count by one, if this is not full
     * @return True, if this action is successful
     */
    public boolean addOccupant(){ //May add a Citizen parameter for checking it is not null (so it exists)
        if(!isFull()){
            occupants++;
            return true;
        }else{
            return false;
        }
    }

    /**
     * Decreases occupant count by one
     * @param occ The Citizen that left this
     * @return The current amount of occupants of this
     */
    public int removeOccupant(Citizen occ){
        return --occupants;
    }
    /*
    //Other version, that is similar to addOccupant()
    public boolean removeOccupant(){ //May add a Citizen parameter for checking it is not null (so it exists)
        if(occupants > 0){
            occupants--;
            return true;
        }else{
            return false;
        }
    }
    */


    //Only way to change the value of level, capacity (this may change later)
    //Based on the math behind the game, this setter procedure may be changed
    // to a simple multiplication or summation in that case, no parameters needed
    /**
     * Increases the level of this, unless it has already reached the maximum level
     * @param cap The new maximum capacity of this, after the upgrade
     * @return True, if this action is successful
     */
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
