package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.BuildingEffect;
import hu.nem3d.zincity.Misc.CellException;

public class ArenaCell extends BuildingCell {

    public ArenaCell(int x, int y, TiledMapTileLayer tileLayer, BuildingPart part) throws CellException {
        super(x, y, tileLayer, part);
        this.range = 10;
        this.name = "Arena";
        this.price = 50;
        this.upkeepCost = 20;

        spreadEffect(this, (cell) -> (cell.getClass() == LivingZoneCell.class
                        || cell.getClass() == IndustrialZoneCell.class || cell.getClass() == ServiceZoneCell.class));
    }

    @Override
    public BuildingEffect getMyEffect() {
        return BuildingEffect.Arena;
    }
}
