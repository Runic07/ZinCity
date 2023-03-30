package hu.nem3d.zincity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameScreen implements Screen { //draft
    //ZinCity zinCity;
    CityMap cityMap;
    OrthographicCamera camera;
    TiledMapRenderer mapRenderer;

    //Need this for menuBar
    protected Stage stage;
    private Viewport viewport;
    private SpriteBatch batch;
    int uiId = 0;
    OrthographicCamera UICamera;
    MenuBar menuBar;

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();



    public GameScreen(){
        cityMap = new CityMap();
        //render map
        float unitScale = 1 / 24f;
        mapRenderer = new OrthogonalTiledMapRenderer(cityMap.getMap(), unitScale);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);
        mapRenderer.setView(camera);

        //MenuBar rendering
        UICamera = new OrthographicCamera();
        UICamera.setToOrtho(false, (float) (screenWidth * 1.5), (float) (screenHeight*1.5));

        batch = new SpriteBatch();
        //viewport = new FitViewport(width, height, UICamera);
        //Creating a virtual vieqówport with 1.5 times the size of the window as to scale the UI properly
        viewport = new FillViewport((float) (screenWidth *1.5), (float) (screenHeight * 1.5), UICamera);
        viewport.apply();
        stage = new Stage(viewport, batch);
        menuBar = new MenuBar(stage);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table = menuBar.setTable(uiId);
        //Setting the bounds og the UI to start at 0 at 90% of the virtual ScreenHeight and with 90% of the virtual screen width
        //and to be 20% of the normal screen height (so the size is 90% width and 20% of height at the top of screen
        table.setBounds(0, (float) (screenHeight * 0.9 *1.5), (float) ((float) screenWidth *0.9 * 1.5), (float) (screenHeight * 0.2));
        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        mapRenderer.render();
        stage.act();
        stage.draw();
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

