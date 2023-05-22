package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Misc.Direction;

/**
 * Represents the power lines
 */
public class PowerLineCell extends CityCell{



    /**
     * Constructs a  with coordinates set from parameters
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     */
    public PowerLineCell(int x, int y, TiledMapTileLayer tileLayer) {

        super(x, y, tileLayer);

        this.upkeepCost = 1;
        this.price = 5;
        this.isWired = true;

        if (this.getNeighbor(Direction.WEST) instanceof PowerLineCell || this.getNeighbor(Direction.EAST) instanceof PowerLineCell){
            this.setRotation(1);
        }

    }

}
