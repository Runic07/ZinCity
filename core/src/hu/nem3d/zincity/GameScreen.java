package hu.nem3d.zincity;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;





public class GameScreen implements Screen { //draft
    //ZinCity zinCity;
    CityMap cityMap;
    OrthographicCamera camera;
    TiledMapRenderer mapRenderer;
    public GameScreen(){

        cityMap = new CityMap();
        //render map
        float unitScale = 1 / 24f;
        mapRenderer = new OrthogonalTiledMapRenderer(cityMap.getMap(), unitScale);
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

