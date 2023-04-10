package hu.nem3d.zincity.Cell;

public class StadiumCell extends BuildingCell {
    public StadiumCell(int range, int maintenanceFee) {
        super(range, maintenanceFee, false);
    }

    public StadiumCell(int x, int y, int range, int maintenanceFee) {
        super(x, y, range, maintenanceFee, false);
    }

    public StadiumCell(int x, int y, int range, int maintenanceFee, BuildingPart part) {
        super(x, y, range, maintenanceFee, part);
    }
}
