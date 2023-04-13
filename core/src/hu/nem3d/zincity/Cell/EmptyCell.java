package hu.nem3d.zincity.Cell;

/**
 * Represents a cell that can be built on
 */
public class EmptyCell extends CityCell{

    /**
     * Constructs an EmptyCell with coordinates of origin
     */
    public EmptyCell() {
        super();
    }

    /**
     * Constructs an EmptyCell with coordinates set from the parameters
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     */
    public EmptyCell(int x, int y) {
        super(x, y);
    }
}
