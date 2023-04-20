package hu.nem3d.zincity.Cell;

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
    protected int maintenanceFee;

    //Temp constructor for testing purposes
    /**
     * Constructs a standard instance of a BuildingCell,
     * with (coordinates of origin and) values set to the values of the parameters
     * @param range The maximum distance between this and a cell, that this can effect
     * @param maintenanceFee The annual cost of maintaining this
     * @param isSimple It's true, if it is a single-cell building, else it is false
     */
    protected BuildingCell(int range, int maintenanceFee, boolean isSimple) {
        super();
        this.range = range;
        this.maintenanceFee = maintenanceFee;
        this.isSimple = isSimple;
    }

    /**
     * Constructs a standard instance of a BuildingCell, with values set to the values of the parameters
     * (If this is a multi-cell building, this constructs the other parts of the building)
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     * @param range The maximum distance between this and a cell, that this can effect
     * @param maintenanceFee The annual cost of maintaining this
     * @param isSimple True, if it is a single-cell building, else it is false
     */
    protected BuildingCell(int x, int y, int range, int maintenanceFee, boolean isSimple) {
        super(x, y);
        this.range = range;
        this.maintenanceFee = maintenanceFee;
        this.isWired = true;
        if (!isSimple){
            this.part = BuildingPart.NorthWest;
            //TODO call build method for other parts
        }
    }

    /**
     * Constructs a specific part of a building, with values set to the values of the parameters
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     * @param range The maximum distance between this and a cell, that this can effect
     * @param maintenanceFee The annual cost of maintaining this
     * @param part The specific part of the multi-cell building
     */
    protected BuildingCell(int x, int y, int range, int maintenanceFee, BuildingPart part) {
        super(x, y);
        this.range = range;
        this.isWired = true;
        this.maintenanceFee = maintenanceFee;
        this.part = part;
    }

    /**
     * Gets the name of this
     * @return The name of this
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets the maintenance fee of this
     * @return the annual cost of maintaining this
     */
    public int getMaintenanceFee(){
        return this.maintenanceFee;
    }

    /**
     * The effect this has
     */
    public abstract void doEffect();

    public void setPart(BuildingPart part_){
        if(!isSimple){
            this.part = part_;
        }

    }
    public BuildingPart getPart() {
        return part;
    }
}


