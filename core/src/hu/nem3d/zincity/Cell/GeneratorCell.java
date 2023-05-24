package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.BuildingEffect;
import hu.nem3d.zincity.Misc.CellException;
import hu.nem3d.zincity.Misc.Direction;

import java.util.Arrays;
import java.util.LinkedList;

public class GeneratorCell extends BuildingCell {

    public GeneratorCell(int x, int y, TiledMapTileLayer tileLayer, BuildingPart part, boolean forced) throws CellException {
        super(x, y, tileLayer, part, forced);
        this.name = "Generator";
        this.price = 20;
        this.upkeepCost = 7.5;
        spreadEffect();
    }
    public GeneratorCell(int x, int y, TiledMapTileLayer tileLayer, boolean forced) throws CellException {
        super(x, y, tileLayer, BuildingPart.NorthWest, forced);

        this.name = "Generator";
        this.price = 20;
        this.range = 0;
        this.upkeepCost = 7.5;

        spreadEffect();
    }

    @Override
    public BuildingEffect getMyEffect() {
        return null;
    }

    @Override
    protected void spreadEffect() {
        if (part == BuildingPart.SouthEast) {
            electrify(true);
        }
    }

    @Override
    public void removeSpreadEffect() {
        electrify(false);
    }

    @Override
    public void spreadSiblingsEffects() {
        for (int i = 0; i < tileLayer.getWidth(); i++) {
            for (int j = 0; j < tileLayer.getHeight(); j++) {
                if(tileLayer.getCell(i, j).getClass() == GeneratorCell.class && !(i == x && j == y)){
                    GeneratorCell cell = (GeneratorCell) tileLayer.getCell(i, j);
                    if(cell.getPart() == BuildingPart.SouthEast){
                        cell.spreadEffect();
                    }
                }
            }
        }
    }

    @Override
    public boolean electrify(boolean elect) {
        isElectrified = elect;
        System.out.println("Electricity is " + elect + " at " + getClass().getSimpleName() + " on " + x + " " + y);

        for(Direction dir : Direction.values()){
            CityCell neighbor = getNeighbor(dir);
            if(neighbor != null && neighbor.isWired() && neighbor.isElectrified() != elect) {
                neighbor.electrify(elect);
            }
        }
        return true;
    }
}
