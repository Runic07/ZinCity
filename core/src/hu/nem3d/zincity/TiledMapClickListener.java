package hu.nem3d.zincity;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TiledMapClickListener extends ClickListener {

    private TiledMapActor actor;

    public TiledMapClickListener(TiledMapActor actor) {
        this.actor = actor;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        CityCell cell = actor.getCell();
        System.out.println(cell + " has been clicked. " + cell.getX() +" "+ cell.getY() + " x:" + x + " y: " + y + " ");
    }
}