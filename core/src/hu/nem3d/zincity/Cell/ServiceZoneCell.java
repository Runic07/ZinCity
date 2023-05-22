package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.CellException;

/**
 * Represents a cell of a service zone
 */
public class ServiceZoneCell extends ZoneCell {

    /**
     * Constructs an ServiceZoneCell with coordinates and capacity set from parameters
     *
     * @param x      The distance of this from the origin on the horizontal axis
     * @param y      The distance of this from the origin on the vertical axis
     * @param forced
     */
    public ServiceZoneCell(int x, int y, TiledMapTileLayer tileLayer, boolean forced) throws CellException {
        super(x, y, tileLayer, forced);
        this.price = 30;
        this.capacity = 4;

    }
}

