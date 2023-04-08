package hu.nem3d.zincity.Screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import hu.nem3d.zincity.Cell.CityCell;
import hu.nem3d.zincity.Logic.CityMap;
import hu.nem3d.zincity.Misc.Builder;

public class TiledMapClickListener extends ClickListener {

    private StatUI stats;
    private int UIid;
    private CityMap city;

    private CityStage stage;

    private TiledMapActor actor;
    private int buildCode;

    public TiledMapClickListener(TiledMapActor actor, StatUI stat,CityMap city_,CityStage stage_) {
        this.actor = actor;
        this.stats = stat;
        this.stage = stage_;
        this.UIid = stage.getUIid();
        this.buildCode = stage.getBuildCode();
        this.city = city_;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        CityCell cell = actor.getCell();
        System.out.println(cell + " has been clicked. " + cell.getX() +" "+ cell.getY() + " x:" + x + " y: " + y + " ");
        this.UIid = stage.getUIid();
        this.buildCode = stage.getBuildCode();

        System.out.println(UIid + " " + buildCode);
        Builder builder = new Builder(UIid, buildCode, city);
        builder.build(cell);
        System.out.println(cell.getClass());

        stats.isShown(cell);
        cell.statCall(stats);
        actor.setCell(cell);
        //System.out.println(stats.getShown());

    }
}