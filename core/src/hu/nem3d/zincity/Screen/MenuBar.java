package hu.nem3d.zincity.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import hu.nem3d.zincity.Logic.City;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Menubar represents the upper UI bar, both the buttons and the stats for the whole city
 */
public class MenuBar {

    City city;

    public int day = 0;

    private int buildCode = 0;

    private  int currId = 0;
    private int idTo = 0;
    private final TextureAtlas atlas;
    private final Skin skin;

    private Table currTable;
    private Table statTable;

    private final Stage stage;

    private final GameScreen screen;

    private String speedText;

    private boolean speedChange;

    /**
     * Constructor sets the skin and TextureAtlas and skin that the UI uses, stage is a Stage where the UI is on as an actor and the city provides the data.
     * @param stage_
     * @param city_
     * @param screen_
     */
    public MenuBar(Stage stage_, City city_, GameScreen screen_) {
        atlas = new TextureAtlas(Gdx.files.internal("PlaceHolderMenu\\uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("PlaceHolderMenu\\uiskin.json"), atlas);
        stage = stage_;
        city = city_;
        screen = screen_;
        speedChange = false;
        int curSpeed = screen.getSpeed();
        switch (curSpeed){
            case(600):
                speedText= ">";
                break;
            case(300):
                speedText= ">>";
                break;
            case(60):
                speedText= ">>>";
                break;
            default:
                speedText = "||";
                break;
        }
    }

    /**
     * It gives back a table with textButtons on it which represents the options.Id is the current Id option menu that is currently on screen.
     * Width and height are the properties of the table( it is set in GameScreen due to resizes). In the function every button has its own eventListener
     * which dictates what the button does. IdTo is what UI should be called next, buildCode is only relevant when building something or removing, but that is just
     * set here the handling is in Builder.
     * @param id
     * @param width
     * @param height
     * @return
     */
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
        skin.getFont("commodore-64").getData().setScale(width/720f+Float.MIN_VALUE, height/480f+Float.MIN_VALUE);

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
                    stage.clear();
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


                TextButton speedButton = new TextButton(speedText, skin);
                speedButton.setSize(width / 9, (float) ((height * 0.15) / 2));

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

                speedButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        int curSpeed = screen.getSpeed();
                        String speedmsg = "";
                        switch (curSpeed){
                            case(600):
                                screen.setSpeed(300);
                                speedText= ">>";
                                speedChange = true;
                                break;
                            case(300):
                                screen.setSpeed(60);
                                speedText= ">>>";
                                speedChange = true;
                                break;
                            case(60):
                                screen.setSpeed(0);
                                speedText= "||";
                                speedChange = true;
                                break;
                            default:
                                screen.setSpeed(600);
                                speedText = ">";
                                speedChange = true;
                                break;
                        }
                    }
                });

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
                currTable.add(speedButton).spaceRight(10).expand().bottom().fill();
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



                networkButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        idTo = 5;
                    }
                });

                settingsButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        stage.clear();
                        SettingsScreen settings = new SettingsScreen(stage,city,screen);
                        Table settingsTable = settings.settingsTable(width,height);
                        TextButton backButton = new TextButton("Back", skin);
                        backButton.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                stage.clear();
                                idTo = 0;
                            }
                        });
                        settingsTable.row();
                        settingsTable.add(backButton);
                        settingsTable.setBounds(0,(float) (height * 1.5 - (float) (height * 0.77 *1.5)), (float) (width * 1.5), (float) (height * 0.6 *1.5) );

                        stage.addActor(settingsTable);
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

    /**
     * statTable gives back the current stats (days, satisfaction and budget) on a table, width and height is the tables height not the screens.
     * @param width
     * @param height
     * @return
     */
    public Table statTable(final float width, final float height){
        statTable = new Table(skin);
        //Set table to fill stage
        //currTable.setFillParent(true);
        statTable.background("dialog");
        //Set alignment of contents in the table.
        statTable.top();

        skin.getFont("commodore-64").getData().setScale(width/720f+Float.MIN_VALUE, height/480f+Float.MIN_VALUE);

        DecimalFormat df = new DecimalFormat("###.#");

        Label happiness = new Label( " " + df.format(city.satisfaction * 100) + "% ", skin);
        happiness.setSize(width / 9, (float) ((height * 0.15) / 2));

        Label date = new Label(day + "D", skin);
        date.setSize(width / 9, (float) ((height * 0.15) / 2));

        Label money = new Label(city.budget + "$", skin);
        money.setSize(width / 9, (float) ((height * 0.15) / 2));

        Label population = new Label("Pop:" + city.citizens.size(), skin);
        population.setSize(width / 9, (float) ((height * 0.15) / 2));

        statTable.add(happiness).spaceRight(10).expand().bottom().fill();
        //currTable.add(date);
        statTable.add(date).spaceRight(10).expand().bottom().fill();
        statTable.add(money).spaceRight(10).expand().bottom().fill();
        statTable.add(population).expand().bottom().fill();

        return statTable;
    }

    public void isGameOver(){
        if(city.citizens.size() == 0) {
            screen.setSpeed(0);
            Dialog dialog = new Dialog("Warning", skin, "dialog") {
                public void result(Object obj) {
                    System.out.println("result " + obj);
                    boolean quitMenu = (Boolean) obj;
                    if (quitMenu) {
                        Gdx.app.exit();
                    }
                }
            };
            dialog.text("GAME OVER");
            dialog.button("oof", true);
            dialog.getBackground().setMinWidth(200);
            dialog.getBackground().setMinHeight(200);
            dialog.show(stage);
        }

    }

    public boolean reRenderSpeed(){
        if(speedChange){
            speedChange = false;
            return true;
        }
        return false;
    }


    public int getIdTo(){
        return idTo;
    }

    public int getBuildCode(){return buildCode;}
}
