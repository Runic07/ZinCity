package hu.nem3d.zincity.Cell;

/**
 * Represents a cell of a service zone
 */
public class ServiceZoneCell extends ZoneCell {

    /**
     * Constructs an ServiceZoneCell with coordinates and capacity set from parameters
     * @param capacity The maximum number of occupants this zone can haves
     */
    public ServiceZoneCell(int capacity) {
        super(capacity);
    }

    /**
     * Constructs an ServiceZoneCell with coordinates and capacity set from parameters
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     * @param capacity The maximum number of occupants this zone can haves
     */
    public ServiceZoneCell(int x, int y, int capacity) {
        super(x, y, capacity);
    }
}

