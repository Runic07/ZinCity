package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Represents the cell, which citizen can travel on
 */
public class RoadCell extends CityCell{



    /**
     * Constructs a RoadCell with coordinates set from parameters
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     */
    public RoadCell(int x, int y, TiledMapTileLayer tileLayer) {
        super(x, y, tileLayer);
    }

}
