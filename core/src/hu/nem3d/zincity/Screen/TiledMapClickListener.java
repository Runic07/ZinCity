package hu.nem3d.zincity.Screen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import hu.nem3d.zincity.Cell.ArenaCell;
import hu.nem3d.zincity.Cell.CityCell;
import hu.nem3d.zincity.Cell.EmptyCell;
import hu.nem3d.zincity.Cell.GeneratorCell;
import hu.nem3d.zincity.Logic.City;
import hu.nem3d.zincity.Logic.CityMap;
import hu.nem3d.zincity.Misc.Builder;
import java.util.ArrayList;

/**
 * This class is responsible for handling what happens when you click on a cell.
 */
public class TiledMapClickListener extends ClickListener {

    private StatUI stats;
    private int UIid;
    private City city;

    private CityStage stage;

    private TiledMapActor actor;
    private int buildCode;

    /**
     * Setting basic properties.
     * @param actor
     * @param stat
     * @param city_
     * @param stage_
     */
    public TiledMapClickListener(TiledMapActor actor, StatUI stat, City city_, CityStage stage_) {
        this.actor = actor;
        this.stats = stat;
        this.stage = stage_;
        this.UIid = stage.getUIid();
        this.buildCode = stage.getBuildCode();
        this.city = city_;
    }

    /**
     * Handling a click.
     * Cells is an array list in which all the cells are listed which are changed due to the click on this cell with the certain action.
     * If UI-id is 7 or 8 those are the same Ui-id 1 and 2 but had to be handled like this due to the adding of a back button
     * while using the functions ofUI 1 or 2.
     * BuildCode and UIid given to Builder to determine the affected cells and this cell.
     * After that we change the affected cells (if cells is only 1 long than only this cells else we go through all the cells and the map to change them)
     * We call isShown to know if the stats are shown to this cell and then we statCall to reflect on the changes in the cell.
     * @param event
     * @param x
     * @param y
     */
    @Override
    public void clicked(InputEvent event, float x, float y) {

        ArrayList<CityCell> cells = new ArrayList<>();

        //System.out.println(cell + " has been clicked. " + cell.getX() +" "+ cell.getY() + " x:" + x + " y: " + y + " ");
        this.UIid = stage.getUIid();
        if(UIid == 7){
            UIid = 1;
        }
        else if(UIid == 8){
            UIid = 2;
        }
        this.buildCode = stage.getBuildCode();

        //System.out.println(UIid + " " + buildCode);
        Builder builder = new Builder(UIid, buildCode, city);
        cells =  builder.build(actor.getCell());
        if(actor.getCell() != null && cells.size() > 1) {
            actor.setCell(cells.get(0));
        }

        if(cells.size() > 2){
            for(Actor actors : stage.getActors()){
                TiledMapActor actorTmp = (TiledMapActor) actors;
                for(CityCell cellTmp : cells){
                    if(cellTmp.getX() == actorTmp.getPosX() && cellTmp.getY() == actorTmp.getPosY()){
                        actorTmp.setCell(cellTmp);
                    }
                }
            }
        }
        //System.out.println(actor.getCell().getClass());

        stats.isShown(actor.getCell());
        actor.getCell().statCall(stats);
        //actor.setCell(actor.getCell());
    }
}