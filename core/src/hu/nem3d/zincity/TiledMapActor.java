package hu.nem3d.zincity;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TiledMapActor extends Actor {

    private CityMap tiledMap;

    private TiledMapTileLayer tiledLayer;

    public CityMap getTiledMap() {
        return tiledMap;
    }

    public void setTiledMap(CityMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    public TiledMapTileLayer getTiledLayer() {
        return tiledLayer;
    }

    public void setTiledLayer(TiledMapTileLayer tiledLayer) {
        this.tiledLayer = tiledLayer;
    }

    public CityCell getCell() {
        return cell;
    }

    public void setCell(CityCell cell) {
        this.cell = cell;
    }

    private CityCell cell;

    public TiledMapActor(CityMap tiledMap, TiledMapTileLayer tiledLayer, CityCell cell) {
        this.tiledMap = tiledMap;
        this.tiledLayer = tiledLayer;
        this.cell = cell;
    }

}
