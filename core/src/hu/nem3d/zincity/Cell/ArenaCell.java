package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.BuildingEffect;
import hu.nem3d.zincity.Misc.CellException;

public class ArenaCell extends BuildingCell {

    public ArenaCell(int x, int y, TiledMapTileLayer tileLayer, BuildingPart part, boolean forced) throws CellException {
        super(x, y, tileLayer, part, forced);
        this.range = 10;
        this.name = "Arena";
        this.price = 50;
        this.upkeepCost = 20;
        if(isElectrified()){spreadEffect();}
    }
    public ArenaCell(int x, int y, TiledMapTileLayer tileLayer, boolean forced) throws CellException {
        super(x, y, tileLayer, BuildingPart.NorthWest, forced);
        this.range = 10;
        this.name = "Arena";
        this.price = 50;
        this.upkeepCost = 20;

        if(isElectrified()){spreadEffect();}
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
                if(isInRange(cell) && (cell instanceof ZoneCell)){
                    cell.addEffect(getMyEffect());
                    //System.out.println(this.getClass().getSimpleName() + " effects " + cell.getX() + " " + cell.getY());
                }
            }
        }
    }
    @Override
    public void removeSpreadEffect() {
        int er = range+1;
        for (int i = Math.max(0, x-er); i <= Math.min(tileLayer.getWidth()-1, x+er); i++) {
            for (int j = Math.max(0, y-er); j <= Math.min(tileLayer.getHeight()-1, y+er); j++) {
                CityCell cell = (CityCell)tileLayer.getCell(i, j);
                if(isInRange(cell) && (cell instanceof ZoneCell)){
                    cell.removeEffect(getMyEffect());
                    //System.out.println(this.getClass().getSimpleName() + " removed effects from " + i + " " + j);
                }
            }
        }
    }

    @Override
    public void spreadSiblingsEffects() {
        int er = range+1;
        for (int i = Math.max(0, x-(er*2)); i <= Math.min(tileLayer.getWidth()-1, x+(er*2)); i++) {
            for (int j = Math.max(0, y-(er*2)); j <= Math.min(tileLayer.getHeight()-1, y+(er*2)); j++) {
                if((tileLayer.getCell(i, j).getClass() == ArenaCell.class && !(i == x && j == y))){
                    ((BuildingCell) tileLayer.getCell(i, j)).spreadEffect();
                }
            }
        }
    }
}
