package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.CellException;

public class GeneratorCell extends BuildingCell {

    public GeneratorCell(int x, int y, TiledMapTileLayer tileLayer, BuildingPart part) throws CellException {
        super(x, y, tileLayer, part);
        this.name = "Generator";
        this.price = 20;
        this.upkeepCost = 7.5;
    }
}
