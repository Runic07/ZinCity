package hu.nem3d.zincity.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import hu.nem3d.zincity.Logic.City;
import hu.nem3d.zincity.Misc.CitySerializer;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class MainMenu implements Screen {
    File loadFile;
    TextureAtlas atlas;
    Skin skin;
    protected Stage stage;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final SpriteBatch batch;
    public static Texture backgroundTexture;
    public static Sprite backgroundSprite;

    public MainMenu() {
        atlas = new TextureAtlas(Gdx.files.internal("PlaceHolderMenu\\uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("PlaceHolderMenu\\uiskin.json"), atlas);

        backgroundTexture = new Texture(Gdx.files.internal("boxart3_noGrid.png"));
        backgroundSprite = new Sprite(backgroundTexture);

        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(720,480, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }


    @Override
    public void show() {
        //Stage should control input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table(skin);
        //Set table to fill stage
        //mainTable.setFillParent(true);
        mainTable.background("window");
        //Set alignment of contents in the table.
        mainTable.top();

        //Create buttons
        TextButton playButton = new TextButton("Play", skin);
        TextButton loadButton = new TextButton("Load", skin);
        TextButton exitButton = new TextButton("Exit", skin);
        Label title = new Label("ZimCity", skin);
        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });
        loadButton.addListener((new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                JFileChooser chooser = new JFileChooser();
                JFrame f = new JFrame();
                f.setVisible(true);
                f.toFront();
                f.setVisible(false);
                int res = chooser.showSaveDialog(f);
                f.dispose();
                if (res == JFileChooser.APPROVE_OPTION) { //If selected file is accesible
                    loadFile = chooser.getSelectedFile();
                    String loadFileName = loadFile.getName().toLowerCase();
                    if(loadFile != null && loadFile.isFile() && loadFileName.endsWith(".json")){  //Checking if loadFile is correct
                            //Call load function when it is implemented for now it is just a dialog as a signal fo succes

                        StringBuilder stringBuilder = new StringBuilder();
                        Scanner scanner = null;
                        try {
                            scanner = new Scanner(loadFile);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }

                        while (scanner.hasNextLine()) {
                            stringBuilder.append(scanner.nextLine());
                        }

                        scanner.close();


                        Json json = new Json();

                        json.setSerializer(City.class, new CitySerializer());
                        City city = json.fromJson(City.class, stringBuilder.toString());
                        Dialog dialog = new Dialog("Success", skin, "dialog") {
                            public void result(Object obj) {
                            }
                        };
                        dialog.text("Correct file format");
                        dialog.button("Back",true);
                        dialog.getBackground().setMinWidth(200);
                        dialog.getBackground().setMinHeight(200);


                        dialog.show(stage);
                        GameScreen loadedGameScreen = new GameScreen();
                        loadedGameScreen.city = city;
                        ((Game)Gdx.app.getApplicationListener()).setScreen(loadedGameScreen);
                    }
                    else{
                        Dialog dialog = new Dialog("Warning", skin, "dialog") {
                            public void result(Object obj) {
                            }
                        };
                        dialog.text("Incorrect file format");
                        dialog.button("Back",true);
                        dialog.getBackground().setMinWidth(200);
                        dialog.getBackground().setMinHeight(200);
                        dialog.show(stage);
                    }
                }
                        //todo file opening and loading save file
            }
        }));
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("Warning", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result "+obj);
                        boolean quitMenu = (Boolean) obj;
                        if(quitMenu){
                            Gdx.app.exit();
                        }
                    }
                };
                dialog.text("Are you sure you want to quit?");
                dialog.button("Yes", true);
                dialog.button("No", false);
                dialog.getBackground().setMinWidth(200);
                dialog.getBackground().setMinHeight(200);
                dialog.show(stage);

            }
        });

        //Add rows to table
        mainTable.add(title);
        mainTable.row();
        mainTable.add(playButton);
        mainTable.row();
        mainTable.add(loadButton);
        mainTable.row();
        mainTable.add(exitButton);
        mainTable.setSize(200,200);
        mainTable.setPosition(260,140);
        //Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();
        stage.act();
        stage.draw();

    }
    /*
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }*/

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
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