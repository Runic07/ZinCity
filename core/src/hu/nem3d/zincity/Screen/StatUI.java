package hu.nem3d.zincity.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hu.nem3d.zincity.Cell.*;

/**
 * This is what returns the Stats of a cell to GameScreen as a table and that is then rendered on screen and shows its stats.
 */
public class StatUI {

    boolean shown;
    private String name;
    private String dataString;
    private String tier;
    private final TextureAtlas atlas;
    private final Skin skin;

    public int x, y;
    public StatUI(){
        name = "";
        dataString = "null";
        tier = "null";
        atlas = new TextureAtlas(Gdx.files.internal("PlaceHolderMenu\\uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("PlaceHolderMenu\\uiskin.json"), atlas);
        shown = false;
    }

    /**
     * Based on cell returns it stats value which are later made into a table.
     * @param cell
     */
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
        else if(cell.getClass() == PowerLineCell.class){
            name = "Power Line";
            dataString = "null";
            tier = "null";
        }
        else if(cell instanceof BuildingCell){
            name = ((BuildingCell)cell).getName();
            dataString = "Fee: " + cell.getUpkeepCost();
            tier = "null";
            if(cell.getClass() == ArenaCell.class || cell.getClass() == GeneratorCell.class){
                tier = "Part: " + ((BuildingCell) cell).getPart();
            }
        }
        else if(cell.getClass() == EmptyCell.class){
            name = "Empty";
            dataString = "null";
            tier = "null";
        }
        else if(cell.getClass() == LivingZoneCell.class){
            name = "Living zone";
            dataString = "Capacity: "+ ((LivingZoneCell) cell).getOccupants() + " / " + ((LivingZoneCell) cell).getCapacity();
            tier = "Tier: " +  ((LivingZoneCell) cell).getLevel();
        }
        else if(cell.getClass() == IndustrialZoneCell.class){
            name = "Industrial zone";
            dataString = "Capacity: " + ((IndustrialZoneCell) cell).getOccupants()  +  " / " + ((IndustrialZoneCell) cell).getCapacity();
            tier = "Tier: " + ((IndustrialZoneCell) cell).getLevel();
        }
        else if(cell.getClass() == ServiceZoneCell.class){
            name = "Service zone";
            dataString = "Capacity: " + ((ServiceZoneCell) cell).getOccupants()  + " / " + ((ServiceZoneCell) cell).getCapacity();
            tier = "Tier: " + ((ServiceZoneCell) cell).getLevel();
        }
        else {
            name = "";
            dataString = "null";
            tier = "null";
        }

    }

    /**
     * Makes a table out of the cells Stats which are given to GameScreen to render.
     * @param width
     * @param height
     * @return
     */
    public Table cellStatTable(final float width, final float height){
        Table table = new Table(skin);
        table.background("dialog");
        table.top();
        skin.getFont("commodore-64").getData().setScale(width/720f+Float.MIN_VALUE, height/480f+Float.MIN_VALUE);

        Label nameLabel = new Label( name, skin);
        nameLabel.setSize(width / 9, (float) ((height * 0.15) / 2));

        Label dataLabel = new Label( dataString, skin);
        dataLabel.setSize(width / 9, (float) ((height * 0.15) / 2));

        Label tierLabel = new Label(tier, skin);
        tierLabel.setSize(width / 9, (float) ((height * 0.15) / 2));

        table.add(nameLabel);
        if(!dataString.equals("null")){
            table.row();
            table.add(dataLabel);
        }
        if(!tier.equals("null")){
            table.row();
            table.add(tierLabel);
        }
        return table;
    }

    /**
     * It solves that if you click on the sameCell 2 times the StatUi becomes hidden, its just a switch mostly.
     * @param cell
     */
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
