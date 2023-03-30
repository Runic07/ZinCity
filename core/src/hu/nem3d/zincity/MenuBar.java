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

public class MenuBar{


    private TextureAtlas atlas;
    private Skin skin;

    private Table currTable;

    private Stage stage;

    public MenuBar(TextureAtlas atlas_, Skin skin_, Stage stage_){
        atlas = atlas_;
        skin = skin_;
        stage = stage_;
    }

    public Table setTable(int id) {
        //switch case for id

        //Create Table
        currTable = new Table(skin);
        //Set table to fill stage
        //currTable.setFillParent(true);
        currTable.background("window");
        //Set alignment of contents in the table.
        currTable.top();

        float height = (float) (Gdx.graphics.getHeight() * 0.15);
        float width = (float) Gdx.graphics.getWidth();

        //Create buttons
        TextButton buildButton = new TextButton("Build", skin);
        buildButton.setSize(width/8, (float)((height * 0.15) / 2));

        TextButton statButton = new TextButton("Stats", skin);
        statButton.setSize(width/8, (float)((height * 0.15) / 2));

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.setSize(width/8, (float)((height * 0.15) / 2));

        Label happiness = new Label("50% ", skin);
        happiness.setSize(width/8, (float)((height * 0.15) / 2));

        Label date = new Label("2023.03.30 ", skin);
        date.setSize(width/8, (float)((height * 0.15) / 2));

        Label money = new Label("500$ ", skin);
        money.setSize(width/8, (float)((height * 0.15) / 2));

        //Add listeners to buttons
        buildButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        statButton.addListener((new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
        currTable.add(buildButton).spaceRight(10).expand().bottom().fillX();
        //currTable.add(statButton);
        currTable.add(statButton).spaceRight(10).expand().bottom().fillX();
        //currTable.add(exitButton);
        currTable.add(exitButton).spaceRight(10).expand().bottom().fillX();
        //currTable.add(happiness);
        currTable.add(happiness).spaceRight(10).expand().bottom().fillX();
        //currTable.add(date);
        currTable.add(date).spaceRight(10).expand().bottom().fillX();
        currTable.add(money).expand().bottom().fillX();


        return currTable;

    }
}
