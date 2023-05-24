package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.BuildingEffect;
import hu.nem3d.zincity.Misc.CellException;

/**
 * Provides a class of a police station in the city
 */
public class PoliceCell extends BuildingCell{
    public PoliceCell(int x, int y, TiledMapTileLayer tileLayer, boolean forced) throws CellException {
        super(x, y, tileLayer, forced);
        this.name = "Police station";
        this.price = 80;
        this.upkeepCost = 10;
        this.range = 10;

        if(isElectrified()){spreadEffect();}
    }


    @Override
    public BuildingEffect getMyEffect() {
        return BuildingEffect.Police;
    }
}
