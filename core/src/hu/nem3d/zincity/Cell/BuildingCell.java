package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

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

    protected BuildingCell(int x, int y, TiledMapTileLayer tileLayer) {
        super(x, y, tileLayer);
        isSimple = true; //not sure why we need this
        this.isWired = true;

    }

    /**
     * Constructs a specific part of a building, with values set to the values of the parameters
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis

     *
     * @param part The specific part of the multi-cell building
     */
    protected BuildingCell(int x, int y, TiledMapTileLayer tileLayer, BuildingPart part) {
        super(x, y, tileLayer);
        isSimple = false; //not sure why we need this
        this.isWired = true;
        this.part = part;
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
}


