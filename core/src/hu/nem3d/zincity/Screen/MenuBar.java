package hu.nem3d.zincity.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import hu.nem3d.zincity.Logic.City;

import java.text.DecimalFormat;

public class MenuBar {

    City city;

    public int day = 0;

    private int buildCode = 0;

    private  int currId = 0;
    private int idTo = 0;
    private TextureAtlas atlas;
    private Skin skin;

    private Table currTable;
    private Table statTable;

    private Stage stage;

    private GameScreen screen;

    public MenuBar(Stage stage_, City city_, GameScreen screen_) {
        atlas = new TextureAtlas(Gdx.files.internal("PlaceHolderMenu\\uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("PlaceHolderMenu\\uiskin.json"), atlas);
        stage = stage_;
        city = city_;
        screen = screen_;
    }

    public Table setTable(final int id, final float width, final float height) {
        //switch case for id when multiple screens are implemented
        currId = id;
        //Create Table
        currTable = new Table(skin);
        //Set table to fill stage
        //currTable.setFillParent(true);
        currTable.background("dialog");
        //Set alignment of contents in the table.
        currTable.top();

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.setSize(width / 9, (float) ((height * 0.15) / 2));
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

        TextButton backButton = new TextButton("Back", skin);
        backButton.setSize(width / 9, (float) ((height * 0.15) / 2));
        backButton.addListener(new ClickListener() {            //If anything more is needed than going back to mainUI than implement here this is the back button
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currId == 5 || currId == 6){
                    idTo = 4;
                }
                else{
                    idTo = 0;
                }
                if(currId != 7 && currId != 8){
                    buildCode = 0;
                }
            }
        });
        switch (currId) {

            case (0):
                buildCode = 0;
                //Create buttons
                final TextButton buildButton = new TextButton("Zones", skin);
                buildButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton specialButton = new TextButton("Special", skin);
                specialButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton statButton = new TextButton("Stats", skin);
                statButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton moreButton = new TextButton("More", skin);
                moreButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                //Add listeners to buttons
                buildButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        idTo = 1;
                        //System.out.println("Build");
                    }
                });
                specialButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        idTo = 2;
                        //System.out.println("Build");
                    }
                });
                statButton.addListener((new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        idTo = 3;  //TODO call statScreen when clicked (ID set stays!)
                    }
                }));

                moreButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        idTo = 4;
                    }
                });

                //currTable.setBounds( 0, (float) (height * 0.8), width, (float) (height * 0.15));
                //currTable.setSize((float) Gdx.graphics.getWidth(), (float) (Gdx.graphics.getHeight() * 0.15));
                //Add rows to table
                //currTable.add(BuildButton);
                currTable.add(buildButton).spaceRight(10).expand().bottom().fill();
                currTable.add(specialButton).spaceRight(10).expand().bottom().fill();
                //currTable.add(statButton);
                currTable.add(statButton).spaceRight(10).expand().bottom().fill();
                currTable.add(moreButton).spaceRight(10).expand().bottom().fill();
                break;
            case(1):
                TextButton industrialButton = new TextButton("Industrial", skin);
                industrialButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton serviceButton = new TextButton("Service", skin);
                serviceButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton livingButton = new TextButton("Living", skin);
                livingButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton upgradeButton = new TextButton("Upgrade", skin);
                upgradeButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                industrialButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buildCode = 1;
                        idTo = 7;
                    }
                });

                serviceButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buildCode = 2;
                        idTo = 7;
                    }
                });

                livingButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buildCode = 3;
                        idTo = 7;
                    }
                });

                upgradeButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buildCode = 4;
                        idTo = 7;
                    }
                });
                currTable.add(industrialButton).spaceRight(10).expand().bottom().fill();
                currTable.add(serviceButton).spaceRight(10).expand().bottom().fill();
                //currTable.add(statButton);
                currTable.row();
                currTable.add(livingButton).spaceRight(10).expand().bottom().fill();
                currTable.add(upgradeButton).spaceRight(10).expand().bottom().fill();
                break;

            case(2):
                TextButton fireDepButton = new TextButton("Fire station", skin);
                fireDepButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton policeButton = new TextButton("Police", skin);
                policeButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton arenaButton = new TextButton("Arena", skin);
                arenaButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton generatorButton = new TextButton("Generator", skin);
                generatorButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton forestButton = new TextButton("Forest", skin);
                forestButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                policeButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buildCode = 1;
                        idTo = 8;
                    }
                });

                fireDepButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buildCode = 2;
                        idTo = 8;
                    }
                });

                arenaButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buildCode = 3;
                        idTo = 8;
                    }
                });

                generatorButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buildCode = 4;
                        idTo = 8;
                    }
                });

                forestButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buildCode = 5;
                        idTo = 8;
                    }
                });
                currTable.add(policeButton).spaceRight(10).expand().bottom().fill();
                currTable.add(fireDepButton).spaceRight(10).expand().bottom().fill();
                currTable.add(arenaButton).spaceRight(10).expand().bottom().fill();
                currTable.row();
                currTable.add(generatorButton).spaceRight(10).expand().bottom().fill();
                currTable.add(forestButton).spaceRight(10).expand().bottom().fill();
                break;

            case(3):            //If the stat screen need anything other than backing out implement here
                break;

            case(4):
               TextButton networkButton = new TextButton("Network", skin);
                networkButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton settingsButton = new TextButton("Setting", skin);
                settingsButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton deleteButton = new TextButton("Delete", skin);
                deleteButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton speedButton = new TextButton("Speed", skin);
                speedButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                networkButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        idTo = 5;
                    }
                });

                settingsButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                    }
                });

                speedButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        int curSpeed = screen.getSpeed();
                        switch (curSpeed){
                            case(600):
                                screen.setSpeed(300);
                                break;
                            case(300):
                                screen.setSpeed(60);
                                break;
                            case(60):
                                screen.setSpeed(6000);
                                break;
                            default:
                                screen.setSpeed(600);
                                break;
                        }
                        Dialog dialog = new Dialog("Speed", skin, "dialog") {
                            public void result(Object obj) {
                                System.out.println("result " + obj);
                                boolean quitMenu = (Boolean) obj;
                                if (quitMenu) {
                                    Gdx.app.exit();
                                }
                            }
                        };
                        dialog.text("Current speed: " + screen.getSpeed());
                        dialog.button("OK", false);
                        dialog.getBackground().setMinWidth(200);
                        dialog.getBackground().setMinHeight(200);
                        dialog.show(stage);
                    }
                });

                deleteButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        idTo = 6;
                    }
                });

                currTable.add(networkButton).spaceRight(10).expand().bottom().fill();
                currTable.add(deleteButton).spaceRight(10).expand().bottom().fill();
                currTable.add(speedButton).spaceRight(10).expand().bottom().fill();
                currTable.add(settingsButton).spaceRight(10).expand().bottom().fill();

                break;

            case(5):
                TextButton roadButton = new TextButton("Roads", skin);
                roadButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                TextButton elecButton = new TextButton("Electric lines", skin);
                elecButton.setSize(width / 9, (float) ((height * 0.15) / 2));

                elecButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buildCode = 1;
                    }
                });
                roadButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buildCode = 2;
                    }
                });

                currTable.add(elecButton).spaceRight(10).expand().bottom().fill();
                currTable.add(roadButton).spaceRight(10).expand().bottom().fill();

                break;

            case(6):        //if delete needs anything other than backing out of it implement here.
                break;

        }

                if(currId == 0) {
                    currTable.add(exitButton).spaceRight(10).expand().bottom().fill();
                }
                else {
                    currTable.add(backButton).spaceRight(10).expand().bottom().fill();
                }
        return currTable;
    }

    public Table statTable(final float width, final float height){
        statTable = new Table(skin);
        //Set table to fill stage
        //currTable.setFillParent(true);
        statTable.background("dialog");
        //Set alignment of contents in the table.
        statTable.top();
        DecimalFormat df = new DecimalFormat("#.###");

        Label happiness = new Label( df.format(city.satisfaction) + " ", skin);
        happiness.setSize(width / 9, (float) ((height * 0.15) / 2));

        Label date = new Label("Day: " + day, skin);  //TODO import date instead of this placeholder
        date.setSize(width / 9, (float) ((height * 0.15) / 2));

        Label money = new Label(city.budget + "", skin);
        money.setSize(width / 9, (float) ((height * 0.15) / 2));

        statTable.add(happiness).spaceRight(10).expand().bottom().fill();
        //currTable.add(date);
        statTable.add(date).spaceRight(10).expand().bottom().fill();
        statTable.add(money).expand().bottom().fill();

        return statTable;
    }


    public int getIdTo(){
        return idTo;
    }

    public int getBuildCode(){return buildCode;}
}
