package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Logic.Citizen;
import hu.nem3d.zincity.Misc.CellException;
import hu.nem3d.zincity.Misc.Direction;




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




    /**
     * Constructs an ZoneCell with coordinates and capacity set from parameters
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis

     */

    protected ZoneCell(int x, int y, TiledMapTileLayer tileLayer) throws CellException {
        super(x, y, tileLayer);
        this.upkeepCost = 0;


        if (!hasRoadNeighbor() || (tileLayer.getCell(x,y).getClass() != EmptyCell.class))
        {

            throw new CellException("Building failed - no road neighbors, or non-empty cell");
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
     * @return The current amount of occupants of this
     */
    public int removeOccupant(){
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
