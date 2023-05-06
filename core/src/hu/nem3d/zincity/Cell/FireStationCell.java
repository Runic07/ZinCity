package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.BuildingEffect;
import hu.nem3d.zincity.Misc.CellException;

public class FireStationCell extends BuildingCell{
    public FireStationCell(int x, int y, TiledMapTileLayer tileLayer) throws CellException {
        super(x, y, tileLayer);
        this.name = "Fire station";
        this.price = 20;
        this.upkeepCost = 5;
        this.range = 15;

        spreadEffect();
    }

    @Override
    public BuildingEffect getMyEffect() {
        return BuildingEffect.FireStation;
    }

}
