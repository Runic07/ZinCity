package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.BuildingEffect;
import hu.nem3d.zincity.Misc.Direction;
import hu.nem3d.zincity.Screen.StatUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

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

    TiledMapTileLayer tileLayer; //in case the cell knows the tile layer it's located on.

    HashSet<BuildingEffect> effects = new HashSet<>();



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

        checkElectricity();
        collectEffects();
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

    /**
     * Gets the annual upkeep cost of this
     * @return The annual cost of this
     */
    public double getUpkeepCost() {
        return upkeepCost;
    }

    /**
     * Sets the annual upkeep cost of this
     * @param upkeepCost The annual cost of this to be set
     */
    public void setUpkeepCost(double upkeepCost) {
        this.upkeepCost = upkeepCost;
    }

    /**
     * Gets the one-time build cost of this
     * @return The one-time build cost of this
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the one-time build cost of this
     * @param price The one-time build cost of this to be set
     */
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
     * Changes the value of isElectrified to the value of the parameter (if this has wires),
     * and calls this method on each of its neighbors (in some sort of recursive way),
     * on which is present, wired, and not has the same value as the parameter
     * @param elect The value, which isElectrified will be set to (if it has wires)
     * @return True, if this action is successful on this cell
     */
    public boolean electrify(boolean elect) {
        if(isWired){ //Electricity requires wires to flow through (electrified can't be true, without isWired)
            isElectrified = elect;
            System.out.println("Electricity is " + elect + " at " + getClass().getSimpleName() + " on " + x + " " + y);

            //Electrifying neighbors
            for(Direction dir : Direction.values()){
                CityCell neighbor = getNeighbor(dir);
                if(neighbor != null && neighbor.isWired() && neighbor.isElectrified() != elect){
                    neighbor.electrify(elect);
                }
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * Gets the tileLayer of this
     * @return the tileLayer of this
     */
    public TiledMapTileLayer getTileLayer() {
        return tileLayer;
    }

    /**
     * Adding an effect to the effects set
     * @param effect The BuildingEffect that is to be added
     * @return True, if this action is successful, otherwise false
     */
    public boolean addEffect(BuildingEffect effect){
        return effects.add(effect);
    }

    /**
     * Removes an effect from an effects set
     * @param effect The BuildingEffect that is to be removed
     * @return True, if this action is successful, otherwise false
     */
    public boolean removeEffect(BuildingEffect effect){
        return effects.remove(effect);
    }

    /**
     * Gets the set of the effects of this
     * @return the HashSet of effects of this
     */
    public HashSet<BuildingEffect> getEffects(){
        return effects;
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

    /**
     * Returns neighbors 1 cell away.
     * @return ArrayList containing the cells
     */
    public ArrayList<CityCell> getImmediateNeighbors(){
        ArrayList<CityCell> l = new ArrayList<CityCell>();

        l.add(this.getNeighbor(Direction.NORTH));
        l.add(this.getNeighbor(Direction.EAST));
        l.add(this.getNeighbor(Direction.WEST));
        l.add(this.getNeighbor(Direction.SOUTH));

        if(this.getNeighbor(Direction.NORTH) != null){
            l.add(this.getNeighbor(Direction.NORTH).getNeighbor(Direction.EAST));
            l.add(this.getNeighbor(Direction.NORTH).getNeighbor(Direction.WEST));
        }

        if(this.getNeighbor(Direction.SOUTH) != null){
            l.add(this.getNeighbor(Direction.SOUTH).getNeighbor(Direction.EAST));
            l.add(this.getNeighbor(Direction.SOUTH).getNeighbor(Direction.WEST));
        }
        l.removeIf(Objects::isNull);
        return l;
    }

    /**
     * Helper method, that searches for and adds all the effects that nearby buildings has on this
     */
    private void collectEffects(){
        for (int i = 0; i < tileLayer.getWidth(); i++) {
            for (int j = 0; j < tileLayer.getHeight(); j++) {
                if(tileLayer.getCell(i, j) instanceof BuildingCell){
                    BuildingCell building = (BuildingCell) tileLayer.getCell(i, j);
                    if(building.isInRange(this)){
                        addEffect(building.getMyEffect());
                    }
                }
            }
        }
    }

    /**
     * Checks if any of the 4 neighbouring cells provide electricity, then this electrifies itself
     */
    private void checkElectricity() {
        if(Arrays.stream(Direction.values()).map(this::getNeighbor)
                .filter(Objects::nonNull).anyMatch(CityCell::isElectrified)) {
            electrify(true);
        }
    }
}
