package hu.nem3d.zincity.Screen;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import hu.nem3d.zincity.Cell.CityCell;
import hu.nem3d.zincity.Logic.CityMap;

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
