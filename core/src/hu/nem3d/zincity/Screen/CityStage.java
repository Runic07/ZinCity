package hu.nem3d.zincity.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import hu.nem3d.zincity.Cell.CityCell;
import hu.nem3d.zincity.Logic.City;
import hu.nem3d.zincity.Logic.CityMap;
import hu.nem3d.zincity.Misc.Builder;

import java.util.ArrayList;

/**
 * This is a stage which has TiledMapActors as it actor which are the cells, so it makes a clickable layer above the map which is connected to the cells below.
 */
public class CityStage extends Stage {

        private StatUI stats;
        private TiledMap tiledMap;
        private CityMap cityMap;

        private City city;

        private int UIid;

        private int buildCode;

        private ArrayList<TiledMapActor> actors;

        Builder builder;

    /**
     * Constructor sets the city (where the date is from), id which is the current UIs id which determines the actions that are doable, the stat is a StatUI
     * which is responsible for getting each cells stats.
     * @param city
     * @param id
     * @param stat
     */
        public CityStage(City city, int id, StatUI stat, Builder builder_) {
            this.city = city;
            this.cityMap = city.getCityMap();
            this.stats = stat;
            this.UIid = id;
            this.buildCode = 0;
            this.tiledMap = cityMap.getMap();
            this.actors = new ArrayList<>();
            this.builder = builder_;

            /*MapLayer layer = tiledMap.getLayers().get(id);
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer)layer;
            createActorsForLayer(tiledLayer);*/

            for (MapLayer layer : tiledMap.getLayers()) {
                TiledMapTileLayer tiledLayer = (TiledMapTileLayer)layer;
                createActorsForLayer(tiledLayer);
            }
        }

    /**
     * BuildCode, and UIid determines what the Builder class will do.
     * @param buildCode_
     * @param UIid_
     */
    public void setBuildProperties(int buildCode_, int UIid_){
            this.buildCode = buildCode_;
            this.UIid = UIid_;
        }

    /**
     * Adds tiledMapActors for each layer in a 30x20 grid and sets properties.
     * @param tiledLayer
     */
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
                    EventListener eventListener = new TiledMapClickListener(actor, stats, city, this, builder);
                    actor.addListener(eventListener);
                    actors.add(actor);
                }
            }
        }

    public int getUIid() {
        return UIid;
    }

    public int getBuildCode() {
        return buildCode;
    }

    public CityCell getCell(int x, int y, TiledMapTileLayer tiledLayer){
        return  (CityCell) tiledLayer.getCell(x, y);
    }

    public TiledMapActor getActor(int x, int y){
        for(int i = 0; i < actors.size(); i++){
            if(actors.get(i).getPosY() == y && actors.get(i).getPosX() == x){
                System.out.println( actors.get(i).getCell().getClass() + " getActor");
                return actors.get(i);
            }
        }
        return null;
    }


}