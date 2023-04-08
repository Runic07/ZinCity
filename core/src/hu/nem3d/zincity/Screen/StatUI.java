package hu.nem3d.zincity.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hu.nem3d.zincity.Cell.*;

public class StatUI {

    boolean shown;
    private String name;
    private String dataString;

    private String tier;
    private TextureAtlas atlas;
    private Skin skin;

    public int x, y;
    public StatUI(){
        name = "";
        dataString = "null";
        tier = "null";
        atlas = new TextureAtlas(Gdx.files.internal("PlaceHolderMenu\\uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("PlaceHolderMenu\\uiskin.json"), atlas);
        shown = false;
    };
    public void statCall(CityCell cell){
        x = cell.getX();
        y = cell.getY();
        //System.out.println("Called: " + x + y);
        if(cell.getClass() == ForestCell.class ){
            name = "Forest";
            dataString = "Age: " + ((ForestCell) cell).getAge();
            tier = "null";
        }
        else if(cell.getClass() == BlockedCell.class){
            name = "Blocked cell"; // can rewrite into blockedCell.info but this is better for now.
            dataString = "null";
            tier = "null";

        }
        else if(cell.getClass() == RoadCell.class){
            name = "Road";
            dataString = "null";
            tier = "null";
        }
        else if(cell.getClass() == BuildingCell.class){
            name = ((BuildingCell)cell).getName();
            dataString = "Fee: " + ((BuildingCell)cell).getMaintenanceFee();
            tier = "null";
        }
        else if(cell.getClass() == EmptyCell.class){
            name = "Empty";
            dataString = "null";
            tier = "null";
        }
        else if(cell.getClass() == LivingZoneCell.class){
            name = "Living zone";
            dataString = "Capacity: "+ ((LivingZoneCell) cell).getOccCount() + " / " + ((LivingZoneCell) cell).getCapacity();
            tier = "Tier: " +  ((LivingZoneCell) cell).getLevel();
        }
        else if(cell.getClass() == IndustrialZoneCell.class){
            name = "Industrial zone";
            dataString = "Capacity: " + ((IndustrialZoneCell) cell).getCapacity() +  " / " + ((IndustrialZoneCell) cell).getCapacity();
            tier = "Tier: " + ((IndustrialZoneCell) cell).getLevel();
        }
        else if(cell.getClass() == ServiceZoneCell.class){
            name = "Service zone";
            dataString = "Capacity: " + ((ServiceZoneCell) cell).getOccCount() + " / " + ((ServiceZoneCell) cell).getCapacity();
            tier = "Tier: " + ((ServiceZoneCell) cell).getLevel();
        }
        else {
            name = "";
            dataString = "null";
            tier = "null";
        }

    }
    public Table cellStatTable(final float width, final float height){
        Table table = new Table(skin);
        table.background("dialog");
        table.top();

        Label nameLabel = new Label( name, skin);
        nameLabel.setSize(width / 9, (float) ((height * 0.15) / 2));

        Label dataLabel = new Label( dataString, skin);
        dataLabel.setSize(width / 9, (float) ((height * 0.15) / 2));

        Label tierLabel = new Label(tier, skin);
        tierLabel.setSize(width / 9, (float) ((height * 0.15) / 2));

        table.add(nameLabel);
        if(dataString != "null"){
            table.row();
            table.add(dataLabel);
        }
        if(tier != "null"){
            table.row();
            table.add(tierLabel);
        }
        return table;
    }

    public void isShown(CityCell cell){
        if(x == cell.getX() && y == cell.getY()){
            shown = !shown;
        }
        else{
            shown = true;
        }
    }
    public boolean getShown(){
        return shown;
    }



}
