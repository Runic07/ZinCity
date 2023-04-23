package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Represents a cell that can be built on
 */
public class EmptyCell extends CityCell{


    /**
     * Constructs an EmptyCell with coordinates set from the parameters
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     * @deprecated
     *
     */
    public EmptyCell(int x, int y) {
        super(x, y);
    }
    /**
     * Constructs an EmptyCell with coordinates set from the parameters
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     * @param tileLayer the layer it is being constructed on
     *
     */
    public EmptyCell(int x, int y, TiledMapTileLayer tileLayer) {
        super(x, y, tileLayer);
    }
}
