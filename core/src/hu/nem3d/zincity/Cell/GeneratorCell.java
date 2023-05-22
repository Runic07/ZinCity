package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.CellException;

public class GeneratorCell extends BuildingCell {

    public GeneratorCell(int x, int y, TiledMapTileLayer tileLayer, BuildingPart part, boolean forced) throws CellException {
        super(x, y, tileLayer, part, forced);
        this.name = "Generator";
        this.price = 20;
        this.upkeepCost = 7.5;
    }
    public GeneratorCell(int x, int y, TiledMapTileLayer tileLayer, boolean forced) throws CellException {
        super(x, y, tileLayer, BuildingPart.NorthWest, forced);

        this.name = "Generator";
        this.price = 20;
        this.upkeepCost = 7.5;
    }
}
