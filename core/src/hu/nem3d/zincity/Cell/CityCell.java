package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.Direction;
import hu.nem3d.zincity.Screen.Effects;
import hu.nem3d.zincity.Screen.StatUI;

import java.util.ArrayList;

/**
 * Provides base class for cell tiles.
 * @see com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
 *
 */
public abstract class CityCell extends TiledMapTileLayer.Cell {
    //CLASS FOR EACH TILE ON THE MAP
    //should contain logic data.
    //equivalent with "tile" class in UML diagram. Renamed to avoid confusion.

    protected int x, y; //necessary for selecting tiles

    //It may need a rework, but now for efficiency purposes, those are 2 booleans
    //Obviously, each tile starts with no wire, electricity
    protected boolean isElectrified = false;
    protected boolean isWired = false;

    protected double upkeepCost;
    protected double price;

    protected boolean onFire = false;
    protected int onFireFor;

    TiledMapTileLayer tileLayer; //in case the cell knows the tile layer it's located on.



    /**
     * Constructs a CityCell
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     * @param tileLayer the layer this cell is being constructed on. Always add the correct layer!
     *
     */
    public CityCell(int x, int y, TiledMapTileLayer tileLayer){
        super();
        this.x = x;
        this.y = y;
        this.tileLayer = tileLayer; //this way, the cell can locate tiles adjacent to itself.
        this.onFireFor = 0;
    }

    /**
     * Gets the x value of the coordinate of this
     * @return The distance of this from the origin on the horizontal axis
     */
    public int getX() {return x;}

    /**
     * Changes the x value of this to the value of the parameter
     * @param x The distance of this from the origin on the horizontal axis
     */
    public void setX(int x) {this.x = x;}

    /**
     * Gets the y value of the coordinate of this
     * @return The distance of this from the origin on the vertical axis
     */
    public int getY() {return y;}

    /**
     * Changes the x value of this to the value of the parameter
     * @param y The distance of this from the origin on the vertical axis
     */
    public void setY(int y) {this.y = y;}

    public double getUpkeepCost() {
        return upkeepCost;
    }

    public void setUpkeepCost(double upkeepCost) {
        this.upkeepCost = upkeepCost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns true if this cell has wires
     * @return True, if this cell has wires
     */
    public boolean isWired() {return isWired;}

    /**
     * Changes the value of isWired to the value of the parameter
     * @param wired The value, which isWired will be set to
     */
    public void setWired(boolean wired) {isWired = wired;}

    /**
     * Returns a true if this cell gets electricity from one of the neighbour of this
     * @return True, if this cell is electrified
     */
    public boolean isElectrified() {return isElectrified;}

    /**
     * Changes the value of isElectrified to the value of the parameter (if this has wires)
     * @param elect The value, which isElectrified will be set to (if it has wires)
     * @return True, if this action is successful
     */
    public boolean setElectrified(boolean elect) {
        if(elect){
            if (isWired){  //Electricity requires wires to flow through
                isElectrified = true;
                return true;
            }else{
                return false;
            }
        }else{
            isElectrified = false;
            return true;
        }

    }

    public TiledMapTileLayer getTileLayer() {
        return tileLayer;
    }

    /**
     * Calculates the distance between two CityCells by air
     * @param c The other Citycell from the same map
     * @return The value of the distance by air of this and c
     */
    public double auraRadiusSize(CityCell c) {
        return Math.sqrt(Math.pow((c.getX() - this.x), 2) + Math.pow((c.getY() - this.y), 2));
    }

    /**
     * Calls the StatUI to call a statCall on this
     * @param statscreen The StatUI that will be called on this
     */
    public void statCall(StatUI statscreen){
        statscreen.statCall(this);
    }

    /**
     * Helper method to get neighbors faster. Only works if this.tileLayer is specified.
     * @return Neighboring tile, in spacified direction. null if neighbor doesn't exist.
     */
    public CityCell getNeighbor(Direction direction){

        switch (direction){
            case NORTH: return (CityCell) tileLayer.getCell(x, y+1 );
            case EAST : return (CityCell) tileLayer.getCell(x+1, y );
            case SOUTH: return (CityCell) tileLayer.getCell(x, y-1 );
            case WEST : return (CityCell) tileLayer.getCell(x-1, y );
            default: return null;
        }

    }

    public void burning(){

        ArrayList<Class> nonoZones = new ArrayList<>();

        nonoZones.add(EmptyCell.class);
        nonoZones.add(BlockedCell.class);
        nonoZones.add(RoadCell.class);
        nonoZones.add(PowerLineCell.class);
        nonoZones.add(FireStationCell.class);

        if(onFireFor > 5){  //if it was burning for more than 5 turns, spread the fire
            if( getNeighbor(Direction.NORTH)!= null && !nonoZones.contains(getNeighbor(Direction.NORTH).getClass())){
                getNeighbor(Direction.NORTH).setFire(true);
            }
            if(getNeighbor(Direction.WEST)!= null && !nonoZones.contains(getNeighbor(Direction.WEST).getClass())){
                getNeighbor(Direction.WEST).setFire(true);
            }
            if(getNeighbor(Direction.SOUTH)!= null && !nonoZones.contains(getNeighbor(Direction.SOUTH).getClass())){
                getNeighbor(Direction.SOUTH).setFire(true);
            }
            if(getNeighbor(Direction.EAST)!= null && !nonoZones.contains(getNeighbor(Direction.EAST).getClass())){
                getNeighbor(Direction.EAST).setFire(true);
            }
        }
        onFireFor++;
    }

    public int getOnFireFor(){
        return onFireFor;
    }


    public void setFire(boolean fire){
        onFire = fire;
        if(!onFire){
            onFireFor = 0;
        }
    }
    public boolean getOnFire(){
        return onFire;
    }



}
