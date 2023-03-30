package hu.nem3d.zincity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.*;
import javax.swing.plaf.synth.SynthTableHeaderUI;

public class MenuBar {


    private TextureAtlas atlas;
    private Skin skin;

    private Table currTable;

    private Stage stage;

    public MenuBar(Stage stage_) {
        atlas = new TextureAtlas(Gdx.files.internal("PlaceHolderMenu\\uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("PlaceHolderMenu\\uiskin.json"), atlas);
        stage = stage_;
    }

    public Table setTable(int id, final float width, final float height) {
        //switch case for id

        //Create Table
        currTable = new Table(skin);
        //Set table to fill stage
        //currTable.setFillParent(true);
        currTable.background("dialog");
        //Set alignment of contents in the table.
        currTable.top();

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.setSize(width / 9, (float) ((height * 0.15) / 2));
                //Create buttons
                TextButton buildButton = new TextButton("Build", skin);
                buildButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton statButton = new TextButton("Stats", skin);
                statButton.setSize(width / 9, (float) ((height * 0.15) / 2));


                Label happiness = new Label("50% ", skin);
                happiness.setSize(width / 9, (float) ((height * 0.15) / 2));

                Label date = new Label("2023.03.30 ", skin);
                date.setSize(width / 9, (float) ((height * 0.15) / 2));

                Label money = new Label("500$ ", skin);
                money.setSize(width / 9, (float) ((height * 0.15) / 2));

                //Add listeners to buttons
                buildButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        
                    }
                });
                statButton.addListener((new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        MenuBar tmp = new MenuBar(stage);
                        currTable = tmp.setTable(1,width,height);
                    }
                }));
                exitButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Dialog dialog = new Dialog("Warning", skin, "dialog") {
                            public void result(Object obj) {
                                System.out.println("result " + obj);
                                boolean quitMenu = (Boolean) obj;
                                if (quitMenu) {
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
                //currTable.setBounds( 0, (float) (height * 0.8), width, (float) (height * 0.15));
                //currTable.setSize((float) Gdx.graphics.getWidth(), (float) (Gdx.graphics.getHeight() * 0.15));
                //Add rows to table
                //currTable.add(BuildButton);
                currTable.add(buildButton).spaceRight(10).expand().bottom().fill();
                //currTable.add(statButton);
                currTable.add(statButton).spaceRight(10).expand().bottom().fill();
                //currTable.add(exitButton);
                currTable.add(exitButton).spaceRight(10).expand().bottom().fill();
                //currTable.add(happiness);
                currTable.add(happiness).spaceRight(10).expand().bottom().fill();
                //currTable.add(date);
                currTable.add(date).spaceRight(10).expand().bottom().fill();
                currTable.add(money).expand().bottom().fill();
        return currTable;

    }
}
