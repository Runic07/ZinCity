package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.CellException;
import hu.nem3d.zincity.Misc.Direction;

import java.util.ArrayList;

/**
 * Provides base class for building cell tiles with various effects.
 * @see com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
 * @see hu.nem3d.zincity.Cell.CityCell
 */
public abstract class BuildingCell extends CityCell {

    public void setPart(BuildingPart part) {
        this.part = part;
    }

    /**
     * Represents the specific parts of a multi-cell building
     */
    public enum BuildingPart{
        NorthWest, NorthEast, SouthWest, SouthEast
    }
    protected String name;  //Need for stats --Newton

    protected BuildingPart part;
    protected int range;
    //protected int maintenanceFee; removed in favour of universal "upkeepCost" variable.



    /**
     * Constructs a standard instance of a BuildingCell, with values set to the values of the parameters
     * **/

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

     *
     * @param part The specific part of the multi-cell building
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
}


