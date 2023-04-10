package hu.nem3d.zincity.Cell;

public class PowerPlantCell extends BuildingCell {
    public PowerPlantCell(int range, int maintenanceFee) {
        super(range, maintenanceFee, false);
    }

    public PowerPlantCell(int x, int y, int range, int maintenanceFee) {
        super(x, y, range, maintenanceFee, false);
    }

    public PowerPlantCell(int x, int y, int range, int maintenanceFee, BuildingPart part) {
        super(x, y, range, maintenanceFee, part);
    }
}
