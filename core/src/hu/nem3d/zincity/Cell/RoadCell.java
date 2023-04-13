package hu.nem3d.zincity.Cell;

/**
 * Represents the cell, which citizen can travel on
 */
public class RoadCell extends CityCell{

    /**
     * Constructs a RoadCell with coordinates of origin
     */
    public RoadCell() {
        super();
    }

    /**
     * Constructs a RoadCell with coordinates set from parameters
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     */
    public RoadCell(int x, int y) {
        super(x, y);
    }

}
