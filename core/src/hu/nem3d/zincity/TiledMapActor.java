package hu.nem3d.zincity;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TiledMapActor extends Actor {

    private CityMap tiledMap;

    private TiledMapTileLayer tiledLayer;

    private CityCell cell;

    public CityCell getCell() {
        return cell;
    }

    public void setCell(CityCell cell) {
        this.cell = cell;
    }

    public TiledMapActor(CityMap tiledMap, TiledMapTileLayer tiledLayer, CityCell cell) {
        this.tiledMap = tiledMap;
        this.tiledLayer = tiledLayer;
        this.cell = cell;
    }

}
