package hu.nem3d.zincity.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import hu.nem3d.zincity.Cell.CityCell;
import hu.nem3d.zincity.Logic.CityMap;

public class CityStage extends Stage {
        private TiledMap tiledMap;
        private CityMap city;

        public CityStage(CityMap city, int id) {
            this.tiledMap = city.getMap();
            this.city = city;

            MapLayer layer = tiledMap.getLayers().get(id);
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer)layer;
            createActorsForLayer(tiledLayer);

           /* for (MapLayer layer : tiledMap.getLayers()) {
                TiledMapTileLayer tiledLayer = (TiledMapTileLayer)layer;
                createActorsForLayer(tiledLayer);
            }*/
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
                    TiledMapActor actor = new TiledMapActor(city, tiledLayer, cell);
                    actor.setBounds(x * screenWidth / mapWidth , y * screenHeight / mapHeight , screenWidth / mapWidth, screenHeight / mapHeight);
                    addActor(actor);
                    EventListener eventListener = new TiledMapClickListener(actor);
                    actor.addListener(eventListener);
                }
            }
        }
    }