package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.CellException;

public class ArenaCell extends BuildingCell {



    public ArenaCell(int x, int y, TiledMapTileLayer tileLayer, BuildingPart part) throws CellException {
        super(x, y, tileLayer, part);
        this.range = 10;
        this.name = "Arena";
        this.price = 50;
        this.upkeepCost = 20;
    }
}
