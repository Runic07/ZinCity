package hu.nem3d.zincity;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

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
            for (int x = 0; x < tiledLayer.getWidth(); x++) {
                for (int y = 0; y < tiledLayer.getHeight(); y++) {
                    CityCell cell = (CityCell) tiledLayer.getCell(x, y);
                    TiledMapActor actor = new TiledMapActor(city, tiledLayer, cell);
                    actor.setBounds(x * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(), tiledLayer.getTileWidth(), tiledLayer.getTileHeight());
                    addActor(actor);
                    EventListener eventListener = new TiledMapClickListener(actor);
                    actor.addListener(eventListener);
                }
            }
        }
    }