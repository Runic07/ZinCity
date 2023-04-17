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

public class TiledMapClickListener extends ClickListener {

    private StatUI stats;
    private int UIid;
    private City city;

    private CityStage stage;

    private TiledMapActor actor;
    private int buildCode;

    public TiledMapClickListener(TiledMapActor actor, StatUI stat, City city_, CityStage stage_) {
        this.actor = actor;
        this.stats = stat;
        this.stage = stage_;
        this.UIid = stage.getUIid();
        this.buildCode = stage.getBuildCode();
        this.city = city_;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {

        ArrayList<CityCell> cells;

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

        if(actor.getCell().getClass() == ArenaCell.class || actor.getCell().getClass() == GeneratorCell.class){
            for(Actor actors : stage.getActors()){
                TiledMapActor actorTmp = (TiledMapActor) actors;
                for(CityCell cellTmp : cells){
                    if(cellTmp.getX() == actor.getPosX() && cellTmp.getY() == actor.getPosY()){
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