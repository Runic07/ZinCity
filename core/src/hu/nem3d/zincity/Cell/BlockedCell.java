package hu.nem3d.zincity.Cell;

/**
 * Represents the cell that can not be built on, because of a certain terrain (e.g. river)
 */
public class BlockedCell extends CityCell{

    private String info; //For making StatBoard's info more unique (this attribute may contain data like "lake", "river")

    /**
     * Constructs a BlockedCell with no info
     */
    public BlockedCell() {
        super();
        this.info = "";
    }

    /**
     * Constructs a BlockedCell with value of the parameter as info (and coordinates of origin)
     * @param info The name of the terrain (that is the reason of forbidding the building)
     */
    public BlockedCell(String info) {
        super();
        this.info = info;
    }

    /**
     * Constructs a BlockedCell with coordinates, without info
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     */
    public BlockedCell(int x, int y) {
        super(x, y);
        this.info = "";
    }

    /**
     * Constructs a BlockedCell with all attributes, set with the values of the parameters
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     * @param info The name of the terrain (that is the reason of forbidding the building)
     */
    public BlockedCell(int x, int y, String info) {
        super(x, y);
        this.info = info;
    }

    /**
     * Gets the info of this
     * @return The name of the terrain (that is the reason of forbidding the building)
     */
    public String getInfo() {return info;}

    /**
     * Changes the info of this to the value of parameter
     * @param info The name of the terrain (that is the reason of forbidding the building)
     */
    public void setInfo(String info) {this.info = info;}
}
