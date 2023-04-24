package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.CellException;

/**
 * Represents a cell of a living zone
 */
public class LivingZoneCell extends ZoneCell {



    /**
     * Constructs an LivingZoneCell with coordinates and capacity set from parameters
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     * @param capacity The maximum number of occupants this zone can haves
     */
    public LivingZoneCell(int x, int y, TiledMapTileLayer tileLayer, int capacity) throws CellException {
        super(x, y, tileLayer, capacity);
        this.price = 20;

    }

}
