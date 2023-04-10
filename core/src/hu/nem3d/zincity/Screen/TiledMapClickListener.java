package hu.nem3d.zincity.Screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import hu.nem3d.zincity.Cell.CityCell;

public class TiledMapClickListener extends ClickListener {

    private StatUI stats;

    private TiledMapActor actor;

    public TiledMapClickListener(TiledMapActor actor, StatUI stat) {
        this.actor = actor;
        this.stats = stat;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        CityCell cell = actor.getCell();
        System.out.println(cell + " has been clicked. " + cell.getX() +" "+ cell.getY() + " x:" + x + " y: " + y + " ");
        stats.isShown(cell);
        cell.statCall(stats);
        System.out.println(stats.getShown());
    }
}