package hu.nem3d.zincity;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.*;




public class GameScreen implements Screen { //draft
    ZinCity zinCity;
    OrthographicCamera camera;
    TiledMap map;
    TiledMapTileLayer baseLayer; //contains Cell objects that point to TiledMapTile objects
    TiledMapTileLayer buildingLayer; //more layers can be added on demand
    TiledMapTileSet tileSet; //contains TiledMapTile objects.
    Texture texture;
    StaticTiledMapTile tile_grass;
    StaticTiledMapTile tile_water;
    TiledMapRenderer mapRenderer;
    public GameScreen(){
        //create the tiles
        texture = new Texture(System.getProperty("user.dir") + "\\TexturePlaceholder.png");
        tile_grass = new StaticTiledMapTile(new TextureRegion(texture,0,0,32,32));
        tile_water = new StaticTiledMapTile(new TextureRegion(texture,32,0,32,32)); //todo check if coordinates are measured the standard way.
        //create the tileset
        tileSet = new TiledMapTileSet();
        tileSet.putTile(0, tile_grass);
        tileSet.putTile(1, tile_water);
        //create the layer
        baseLayer = new TiledMapTileLayer(30,20,32,32);
        //fill the layer with tiles
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell(); //NOT the type of cell a table has!
                cell.setTile(tileSet.getTile(0));

                baseLayer.setCell(i,j,cell);

            }
            for (int j = 10; j < 20; j++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell(); //NOT the type of cell a table has!
                cell.setTile(tileSet.getTile(1));

                baseLayer.setCell(i,j,cell);
            }
        }

        //add the layer to the map
        map = new TiledMap();
        map.getLayers().add(baseLayer);

        //render map
        float unitScale = 1 / 32f;
        mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);
        mapRenderer.setView(camera);
    }


    @Override
    public void show() {



    }

    @Override
    public void render(float delta) {
        mapRenderer.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
