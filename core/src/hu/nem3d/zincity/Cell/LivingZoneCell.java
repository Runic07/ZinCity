package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.CellException;

/**
 * Represents a cell of a living zone
 */
public class LivingZoneCell extends ZoneCell {



    /**
     * Constructs an LivingZoneCell with coordinates and capacity set from parameters
     *
     * @param x      The distance of this from the origin on the horizontal axis
     * @param y      The distance of this from the origin on the vertical axis
     * @param forced
     */
    public LivingZoneCell(int x, int y, TiledMapTileLayer tileLayer, boolean forced) throws CellException {
        super(x, y, tileLayer, forced);
        this.price = 20;
        this.capacity = 4;
    }

}
