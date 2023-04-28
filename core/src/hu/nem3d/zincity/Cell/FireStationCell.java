package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class FireStationCell extends BuildingCell{
    public FireStationCell(int x, int y, TiledMapTileLayer tileLayer) {
        super(x, y, tileLayer);
        this.name = "Fire station";
        this.price = 20;
        this.upkeepCost = 5;
        this.range = 15;
    }

}
