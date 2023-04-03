package hu.nem3d.zincity;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class CityCell extends TiledMapTileLayer.Cell {
    //CLASS FOR EACH TILE ON THE MAP
    //should contain logic data.
    //equvivalent with "tile" class in UML diagram. Renamed to avoid confusion.
    int x;
    int y;
    public CityCell() {
        super();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
