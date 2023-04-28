package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
    boolean isSimple;
    protected BuildingPart part;
    protected int range;
    //protected int maintenanceFee; removed in favour of universal "upkeepCost" variable.



    /**
     * Constructs a standard instance of a BuildingCell, with values set to the values of the parameters
     * **/

    protected BuildingCell(int x, int y, TiledMapTileLayer tileLayer) throws CellException {
        super(x, y, tileLayer);
        isSimple = true; //not sure why we need this
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
    protected BuildingCell(int x, int y, TiledMapTileLayer tileLayer, BuildingPart part) throws CellException{
        super(x, y, tileLayer);
        isSimple = false; //not sure why we need this
        this.isWired = true;
        this.part = part;
        if (!hasRoadNeighbor()){
            throw new CellException("Can't build that here");
        }
    }

    /**
     * Gets the name of this
     * @return The name of this
     */
    public String getName(){
        return this.name;
    }


    public void setPart(BuildingPart part_){
        if(!isSimple){
            this.part = part_;
        }

    }
    public BuildingPart getPart() {
        return part;
    }

    private boolean hasRoadNeighbor(){ //i know it is stupid, but builder class takes care of the rest.
        if (isSimple){
            return (this.getNeighbor(Direction.NORTH) != null && this.getNeighbor(Direction.NORTH).getClass() == RoadCell.class ||
                    this.getNeighbor(Direction.SOUTH) != null && this.getNeighbor(Direction.SOUTH).getClass() == RoadCell.class ||
                    this.getNeighbor(Direction.EAST) != null && this.getNeighbor(Direction.EAST).getClass() == RoadCell.class ||
                    this.getNeighbor(Direction.WEST) != null && this.getNeighbor(Direction.WEST).getClass() == RoadCell.class);
        }
        else if (part == BuildingPart.NorthWest){ //only checking from the north west part now, because thats where it's "built from"
            CityCell northEastPartCell = this.getNeighbor(Direction.EAST);
            CityCell southWestPartCell = this.getNeighbor(Direction.SOUTH);
            CityCell southEastPartCell = this.getNeighbor(Direction.SOUTH).getNeighbor(Direction.EAST);

            return (this.getNeighbor(Direction.NORTH) != null && this.getNeighbor(Direction.NORTH).getClass() == RoadCell.class ||
                    this.getNeighbor(Direction.SOUTH) != null && this.getNeighbor(Direction.SOUTH).getClass() == RoadCell.class ||

                    northEastPartCell.getNeighbor(Direction.NORTH) != null && this.getNeighbor(Direction.NORTH).getClass() == RoadCell.class ||
                    northEastPartCell.getNeighbor(Direction.EAST) != null && this.getNeighbor(Direction.EAST).getClass() == RoadCell.class ||

                    southWestPartCell.getNeighbor(Direction.WEST) != null && this.getNeighbor(Direction.WEST).getClass() == RoadCell.class ||
                    southWestPartCell.getNeighbor(Direction.SOUTH) != null && this.getNeighbor(Direction.SOUTH).getClass() == RoadCell.class ||

                    southEastPartCell.getNeighbor(Direction.EAST) != null && this.getNeighbor(Direction.EAST).getClass() == RoadCell.class ||
                    southEastPartCell.getNeighbor(Direction.SOUTH) != null && this.getNeighbor(Direction.SOUTH).getClass() == RoadCell.class);

        }
        else{
            return true;
        }
    }
}


