package hu.nem3d.zincity.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import hu.nem3d.zincity.Logic.City;
import hu.nem3d.zincity.Misc.Builder;

/**
 * GameScreen is the class that handles the UI elements and the render of the Map
 */
public class GameScreen implements Screen { //draft

    City city;
    OrthographicCamera camera;
    TiledMapRenderer mapRenderer;

    public CityStage cityStage;

    //Need this for menuBar

    private StatUI stat;
    private Table cellStatTable;
    protected Stage stage;

    protected Stage statStage;
    private Viewport viewport;
    private final SpriteBatch batch;

    private int speed;
    int uiId = 0;
    Table UiTable;
    Table statTableUI;
    OrthographicCamera UICamera;
    MenuBar menuBar;

    private final Builder builder;

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();


    int frameCounter = 0; //not permanent, find a better solution to pass the time!



    /**
     * Constructor sets the default camera for the Map and CityStage (which is responsible for clicking cells), and calls the initial UIs.
     * ViewPort is 1.5* of the normal screen because of resolution and placement problems.
     */
    public GameScreen(){
        city = new City();

        builder = new Builder(0,0,city);
        //render map
        float unitScale = 1 / 24f;
        mapRenderer = new OrthogonalTiledMapRenderer(city.getCityMap().getMap(), unitScale);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);
        mapRenderer.setView(camera);
        cityStage = new CityStage(city, 0, stat, builder);

        //MenuBar rendering
        UICamera = new OrthographicCamera();
        UICamera.setToOrtho(false, (float) (screenWidth * 1.5), (float) (screenHeight*1.5));

        batch = new SpriteBatch();
        //viewport = new FitViewport(screenWidth, screenHeight, UICamera);
        //Creating a virtual viewport with 1.5 times the size of the window as to scale the UI properly
        viewport = new FillViewport((float) (screenWidth *1.5), (float) (screenHeight * 1.5), UICamera);
        viewport.apply();
        stage = new Stage(viewport, batch);
        statStage = new Stage(viewport, batch);
        menuBar = new MenuBar(stage, city, this);
        stat = new StatUI();
        speed = 600;
    }

    /**
     * Initial UI showing this is the basis for all of render. Multiplexing is required since we use 2 stages 1 for the UI clicking and the other (CityStage) is for
     * clicking on the map.Setting the bounds of the UI to start at 0 at 90% of the virtual ScreenHeight and with 60% of the virtual screen width
     * and to be 15% of the normal screen height (so the size is 100% width and 15% of height at the top of screen (this is the clickable menu), The stats are the
     * same height but with 40% of the virtual screen and it starts at the end of the UITable.
     */
    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(cityStage);
        Gdx.input.setInputProcessor(multiplexer);
        UiTable = menuBar.setTable(uiId, screenWidth, screenHeight);
        UiTable.setBounds(0, (float) (screenHeight * 0.90 *1.5), (float) (screenWidth *0.6 * 1.5), (float) (screenHeight * 0.15));
        stage.addActor(UiTable);

        statTableUI = menuBar.statTable( screenWidth, screenHeight);
        statTableUI.setBounds((float) (screenWidth *0.6 * 1.5), (float) (screenHeight * 0.90 *1.5), (float) (screenWidth *0.4 * 1.5), (float) (screenHeight * 0.15));
        stage.addActor(statTableUI);

    }
    @Override
    public void render(float delta) {
        if(speed != 0){
            frameCounter++;
        }
        if (frameCounter > speed){
            city.step();
            menuBar.day++;
            frameCounter=0;
        }
        mapRenderer.render();
        cityStage.act();
        cityStage.draw();

        cityStage.setBuildProperties(menuBar.getBuildCode(),uiId);
        if(menuBar.getIdTo() != uiId || menuBar.reRenderSpeed()){
            if(uiId == 2 || uiId == 1){
                stage.clear();
            }
            uiId = menuBar.getIdTo();
            UiTable = menuBar.setTable(uiId, screenWidth, screenHeight);
            UiTable.setBounds(0, (float) (screenHeight * 0.90 *1.5), (float) (screenWidth *0.6 * 1.5), (float) (screenHeight * 0.15));
            if(menuBar.getIdTo() == 2 || menuBar.getIdTo() == 1){
                UiTable.setBounds(0, (float) (screenHeight * 0.80 *1.5), (float) (screenWidth *0.6 * 1.5), (float) (screenHeight * 0.3));
            }
            stage.addActor(UiTable);
        }
        statTableUI = menuBar.statTable( screenWidth, screenHeight);
        statTableUI.setBounds((float) (screenWidth *0.6 * 1.5), (float) (screenHeight * 0.90 *1.5), (float) (screenWidth *0.4 * 1.5), (float) (screenHeight * 0.15));
        stage.addActor(statTableUI);
        stage.act();
        stage.draw();

        if(stat.getShown() && uiId == 0 && screenWidth != 0 && screenHeight != 0){
            cellStatTable = stat.cellStatTable(screenWidth, screenHeight);
            cellStatTable.setBounds(0, (float) (screenHeight * 0.77 *1.5), (float) (screenWidth *0.3 * 1.5), (float) (screenHeight * 0.2));
            statStage.addActor(cellStatTable);

        }
        if(!stat.getShown() || uiId != 0){
            statStage.clear();
        }
        statStage.act();
        statStage.draw();
    }

    /**
     * Resizes everything it is just a resize or reinitialize.
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        mapRenderer.setView(camera);
        cityStage = new CityStage(city, uiId, stat, builder);


        screenWidth = width;
        screenHeight = height;
        UICamera = new OrthographicCamera();
        UICamera.setToOrtho(false, (float) (screenWidth * 1.5), (float) (screenHeight*1.5));
        viewport = new FillViewport((float) (screenWidth *1.5), (float) (screenHeight * 1.5), UICamera);
        viewport.apply();


        stage = new Stage(viewport, batch);
        statStage = new Stage(viewport, batch);

        menuBar = new MenuBar(stage, city, this);

        this.show();
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
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}

