package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.BuildingEffect;
import hu.nem3d.zincity.Misc.CellException;
import hu.nem3d.zincity.Misc.Direction;

/**
 * Provides base class for building cell tiles with various effects.
 * @see com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
 * @see hu.nem3d.zincity.Cell.CityCell
 */
public abstract class BuildingCell extends CityCell {

    /**
     * Represents the specific parts of a multi-cell building
     */
    public enum BuildingPart{
        NorthWest, NorthEast, SouthWest, SouthEast
    }
    protected String name;  //Need for stats --Newton

    protected BuildingPart part;
    protected int range = 0;
    //protected int maintenanceFee; removed in favour of universal "upkeepCost" variable.

    /**
     * Constructs a standard instance of a BuildingCell, with values set to the values of the parameters
    **/
    protected BuildingCell(int x, int y, TiledMapTileLayer tileLayer) throws CellException {
        super(x, y, tileLayer);
        this.isWired = true;
        if (!hasRoadNeighbor()){
            throw new CellException("Can't build that there");
        }
    }

    /**
     * Constructs a specific part of a building, with values set to the values of the parameters
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     * @param tileLayer The TiledMapTileLayer that this is on
     * @param part The specific part of the multi-cell building
     * @throws CellException If this is constructed without a neighboring Road
     */
    protected BuildingCell(int x, int y, TiledMapTileLayer tileLayer, BuildingPart part) throws CellException {
        super(x, y, tileLayer);

        this.isWired = true;
        this.part = part;
        if (!hasRoadNeighbor2x2(x,y,tileLayer)){
            throw new CellException("Can't build that there");
        }

    }

    /**
     * Gets the name of this
     * @return The name of this
     */
    public String getName(){
        return this.name;
    }

    public BuildingPart getPart() {
        return part;
    }

    private boolean hasRoadNeighbor(){

            return (this.getNeighbor(Direction.NORTH) != null && this.getNeighbor(Direction.NORTH).getClass() == RoadCell.class ||
                    this.getNeighbor(Direction.SOUTH) != null && this.getNeighbor(Direction.SOUTH).getClass() == RoadCell.class ||
                    this.getNeighbor(Direction.EAST) != null && this.getNeighbor(Direction.EAST).getClass() == RoadCell.class ||
                    this.getNeighbor(Direction.WEST) != null && this.getNeighbor(Direction.WEST).getClass() == RoadCell.class);
    }

    private boolean hasRoadNeighbor2x2(int x, int y, TiledMapTileLayer layer){
        int partValue = part.ordinal();
        switch (partValue){
            case(1):
                x = x - 1;
                break;
            case(2):
                y = y + 1;
                break;
            case(3):
                x = x - 1;
                y = y + 1;
                break;
        }
        return (layer.getCell(x,y + 1) != null && layer.getCell(x,y + 1).getClass() == RoadCell.class ||
                layer.getCell(x + 1,y+1) != null && layer.getCell(x + 1,y + 1).getClass() == RoadCell.class ||
                layer.getCell(x + 2,y) != null && layer.getCell(x + 2,y).getClass() == RoadCell.class ||
                layer.getCell(x + 2,y - 1) != null && layer.getCell(x + 2,y - 1).getClass() == RoadCell.class ||
                layer.getCell(x - 1,y) != null && layer.getCell(x - 1,y).getClass() == RoadCell.class ||
                layer.getCell(x - 1,y - 1) != null && layer.getCell(x - 1,y - 1).getClass() == RoadCell.class ||
                layer.getCell(x,y - 2) != null && layer.getCell(x,y - 2).getClass() == RoadCell.class ||
                layer.getCell(x + 1,y - 2) != null && layer.getCell(x + 1,y - 2).getClass() == RoadCell.class
                );
    }

    /**
     * Abstract method that gets the BuildingEffect of this (mainly, the children of this)
     * @return The BuildingEffect of this
     */
    public abstract BuildingEffect getMyEffect();

    /**
     * Returns a boolean value, if whether the given cell is in the range of this or not
     * @param cell The cell, that needs to be checked, if it is in the range
     * @return True, if the range of this is equal to, or smaller, than the distance by air between this and the given cell
     */
    public boolean isInRange(CityCell cell){
        if(cell == null){return false;}
        return (range >= this.auraRadiusSize(cell));
    }

    /**
     * Spreads the effect of this building to every cell, that is in range
     */
    protected void spreadEffect(){
        for (int i = Math.max(0, x-range); i <= Math.min(tileLayer.getWidth()-1, x+range); i++) {
            for (int j = Math.max(0, y-range); j <= Math.min(tileLayer.getHeight()-1, y+range); j++) {
                CityCell cell = (CityCell)tileLayer.getCell(i, j);
                if(isInRange(cell)){
                    cell.addEffect(getMyEffect());
                    //System.out.println(this.getClass().getSimpleName() + " effects " + i + " " + j);
                }
            }
        }
    }

    /**
     * Removes the effect of this BuildingCell
     * (It is like spreadEffect method, but backwards)
     */
    public void removeSpreadEffect(){
        for (int i = Math.max(0, x-range); i <= Math.min(tileLayer.getWidth()-1, x+range); i++) {
            for (int j = Math.max(0, y-range); j <= Math.min(tileLayer.getHeight()-1, y+range); j++) {
                CityCell cell = (CityCell)tileLayer.getCell(i, j);
                if(isInRange(cell)){
                    cell.removeEffect(getMyEffect());
                    //System.out.println(this.getClass().getSimpleName() + " removed effects from " + i + " " + j);
                }
            }
        }
    }

    /**
     * Activates the spreading of effects of buildings, that have the same type as this do
     */
    public void spreadSiblingsEffects(){
        for (int i = Math.max(0, x-(range*2)); i <= Math.min(tileLayer.getWidth()-1, x+(range*2)); i++) {
            for (int j = Math.max(0, y-(range*2)); j <= Math.min(tileLayer.getHeight()-1, y+(range*2)); j++) {
                CityCell cell = (CityCell)tileLayer.getCell(i, j);
                if(cell.getClass() == this.getClass() && !(i == x && j == y)){
                    ((BuildingCell)cell).spreadEffect();
                }
            }
        }
    }
}


