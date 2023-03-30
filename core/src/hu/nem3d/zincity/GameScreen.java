package hu.nem3d.zincity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameScreen implements Screen { //draft
    //ZinCity zinCity;
    CityMap cityMap;
    OrthographicCamera camera;
    TiledMapRenderer mapRenderer;

    //Need this for menuBar
    TextureAtlas atlas;
    Skin skin;
    protected Stage stage;
    private Viewport viewport;
    private SpriteBatch batch;
    int uiId = 0;

    MenuBar menuBar;
    public GameScreen(){
        cityMap = new CityMap();
        //render map
        float unitScale = 1 / 24f;
        mapRenderer = new OrthogonalTiledMapRenderer(cityMap.getMap(), unitScale);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);
        mapRenderer.setView(camera);

        //MenuBar rendering
        atlas = new TextureAtlas(Gdx.files.internal("PlaceHolderMenu\\uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("PlaceHolderMenu\\uiskin.json"), atlas);
        batch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();
        stage = new Stage(viewport, batch);
        menuBar = new MenuBar(atlas,skin,stage);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table = menuBar.setTable(uiId);
        table.setBounds((float) ((float) 720*0.0), (float) (480 * 0.9), (float) ((float) 720*0.9), (float) (480 * 0.15));
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
        skin.dispose();
        atlas.dispose();
    }
}

