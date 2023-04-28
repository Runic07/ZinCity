package hu.nem3d.zincity.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import hu.nem3d.zincity.Cell.*;
import hu.nem3d.zincity.Logic.City;


public class SettingsScreen {
    private final City city;
    private final TextureAtlas atlas;
    private final Skin skin;

    private final GameScreen screen;
    private final Stage stage;

    public SettingsScreen(Stage stage_, City city_, GameScreen screen_) {
        atlas = new TextureAtlas(Gdx.files.internal("PlaceHolderMenu\\uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("PlaceHolderMenu\\uiskin.json"), atlas);
        stage = stage_;
        city = city_;
        screen = screen_;
    }

    public Table settingsTable(final float width, final float height){
        Table settings = new Table(skin);
        settings.background("dialog");
        skin.getFont("commodore-64").getData().setScale(width/720f, height/480f);

        TiledMapTileLayer buildingLayer = (TiledMapTileLayer) city.getCityMap().getMap().getLayers().get(1);
        int industrialCount = 0;
        int serviceCount = 0;
        int populationCap = 0;
        int upkeep = 0;

        for (int i = 0; i < 30; i++){
            for (int j = 0; j < 20; j++){
                if(buildingLayer.getCell(i,j).getClass() == IndustrialZoneCell.class){
                    industrialCount++;
                }
                else if(buildingLayer.getCell(i,j).getClass() == LivingZoneCell.class){
                    populationCap += ((ZoneCell) buildingLayer.getCell(i,j)).getCapacity();
                }
                else if(buildingLayer.getCell(i,j).getClass() == ServiceZoneCell.class){
                    serviceCount++;
                }
                CityCell cell = (CityCell) buildingLayer.getCell(i,j);
                upkeep += cell.getUpkeepCost();
            }

        }
        double zoneRatio = industrialCount;

        if(serviceCount != 0){
            zoneRatio = industrialCount * 1.0 / serviceCount;
        }


        Label title = new Label("STATS: ",skin);

        Label population = new Label("Population: " + city.citizens.size() + " people", skin);
        Label maxPopulation = new Label("Capacity: " + populationCap,skin);

        Label industrial = new Label("Industrial zone count : " + industrialCount, skin);
        Label service = new Label("Service zone count: " + serviceCount,skin);
        Label ratio = new Label("Industrial to service ratio: " + zoneRatio,skin);

        Label tax = new Label("Current tax coefficient: " + city.taxCoefficient ,skin);
        Label taxSubmitText = new Label("Input new tax coefficient here: ",skin);
        final TextField taxInput = new TextField("", skin);
        TextButton submitButton = new TextButton("Submit",skin);

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String input = taxInput.getText();
                try {
                    double newTax = Double.parseDouble(input);
                    if(newTax < 0){
                        Dialog dialog = new Dialog("Warning", skin, "dialog");
                        dialog.text("Incorrect number");
                        dialog.button("OK", false);
                        dialog.getBackground().setMinWidth(200);
                        dialog.getBackground().setMinHeight(200);
                        dialog.show(stage);
                    }
                    else{
                        city.taxCoefficient = newTax;
                        Dialog dialog = new Dialog("Warning", skin, "dialog") {
                        };
                        dialog.text("The new tax coefficient is :" + city.taxCoefficient);
                        dialog.button("OK", false);
                        dialog.getBackground().setMinWidth(200);
                        dialog.getBackground().setMinHeight(200);
                        dialog.show(stage);
                    }
                }
                catch(NumberFormatException e) {
                    Dialog dialog = new Dialog("Warning", skin, "dialog");
                    dialog.text("Incorrect format");
                    dialog.button("OK", false);
                    dialog.getBackground().setMinWidth(200);
                    dialog.getBackground().setMinHeight(200);
                    dialog.show(stage);
                }

            }
        });

        Label income = new Label("Income: " + city.taxCoefficient * city.baseTaxAmount * city.citizens.size() ,skin);
        Label upkeepCost = new Label("Upkeep cost: " + upkeep, skin);

        TextButton saveButton = new TextButton("Save game", skin);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    //TODO save file
            }
        });

        settings.add(title);
        settings.row();
        settings.add(population);
        settings.row();
        settings.add(maxPopulation);
        settings.row();
        settings.add(industrial);
        settings.row();
        settings.add(service);
        settings.row();
        settings.add(ratio);
        settings.row();
        settings.add(tax);
        settings.row();
        settings.add(taxSubmitText);
        settings.row();
        settings.add(taxInput);
        settings.row();
        settings.add(submitButton);
        settings.row();
        settings.add(income);
        settings.row();
        settings.add(upkeepCost);
        settings.row();
        settings.add(saveButton);





        return settings;
    }

}
