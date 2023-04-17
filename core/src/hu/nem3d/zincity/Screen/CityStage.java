package hu.nem3d.zincity.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import hu.nem3d.zincity.Cell.CityCell;
import hu.nem3d.zincity.Logic.City;
import hu.nem3d.zincity.Logic.CityMap;

import java.util.ArrayList;

public class CityStage extends Stage {

        private StatUI stats;
        private TiledMap tiledMap;
        private CityMap cityMap;

        private City city;

        private int UIid;

        private int buildCode;

        public CityStage(City city, int id, StatUI stat) {
            this.city = city;
            this.cityMap = city.getCityMap();
            this.stats = stat;
            this.UIid = id;
            this.buildCode = 0;
            this.tiledMap = cityMap.getMap();

            /*MapLayer layer = tiledMap.getLayers().get(id);
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer)layer;
            createActorsForLayer(tiledLayer);*/

            for (MapLayer layer : tiledMap.getLayers()) {
                TiledMapTileLayer tiledLayer = (TiledMapTileLayer)layer;
                createActorsForLayer(tiledLayer);
            }
        }

        public void setBuildProperties(int buildCode_, int UIid_){
            this.buildCode = buildCode_;
            this.UIid = UIid_;
        }

        private void createActorsForLayer(TiledMapTileLayer tiledLayer) {
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();
            float mapWidth = tiledLayer.getWidth();
            float mapHeight = tiledLayer.getHeight();

            //System.out.println(screenHeight + " " + screenWidth);
            //System.out.println(mapWidth + " " + mapHeight);

            //Going through with all the tiles and setting boundaries
            for (int x = 0; x < mapWidth; x++) {
                for (int y = 0; y < mapHeight; y++) {
                    CityCell cell = (CityCell) tiledLayer.getCell(x, y);
                    TiledMapActor actor = new TiledMapActor(cityMap, tiledLayer, cell);
                    actor.setBounds(x * screenWidth / mapWidth , y * screenHeight / mapHeight , screenWidth / mapWidth, screenHeight / mapHeight);
                    actor.setPosX(x);
                    actor.setPosY(y);
                    addActor(actor);
                    EventListener eventListener = new TiledMapClickListener(actor, stats, city, this);
                    actor.addListener(eventListener);
                }
            }
        }

    public int getUIid() {
        return UIid;
    }

    public int getBuildCode() {
        return buildCode;
    }

}