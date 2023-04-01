package hu.nem3d.zincity;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class CityCell extends TiledMapTileLayer.Cell {
    //CLASS FOR EACH TILE ON THE MAP
    //should contain logic data.
    //equvivalent with "tile" class in UML diagram. Renamed to avoid confusion.

    public CityCell() {
        super();
        this.x = 0;
        this.y = 0;
    }
}
