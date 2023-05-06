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

        spreadEffect();
    }

    @Override
    public BuildingEffect getMyEffect() {
        return BuildingEffect.Arena;
    }

    @Override
    protected void spreadEffect() {
        for (int i = Math.max(0, x-range); i < Math.min(tileLayer.getWidth(), x+range); i++) {
            for (int j = Math.max(0, y-range); j < Math.min(tileLayer.getHeight(), y+range); j++) {
                CityCell cell = (CityCell)tileLayer.getCell(i, j);
                if(isInRange(cell) && (cell.getClass() == LivingZoneCell.class
                        || cell.getClass() == IndustrialZoneCell.class || cell.getClass() == ServiceZoneCell.class)){
                    cell.addEffect(getMyEffect());
                    //System.out.println(this.getClass().getSimpleName() + " effects " + cell.getX() + " " + cell.getY());
                }
            }
        }
    }
}
