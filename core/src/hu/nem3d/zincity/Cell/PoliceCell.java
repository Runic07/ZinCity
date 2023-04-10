package hu.nem3d.zincity.Cell;

public class PoliceCell extends BuildingCell {

    public PoliceCell(int range, int maintenanceFee) {
        super(range, maintenanceFee, true);
    }

    public PoliceCell(int x, int y, int range, int maintenanceFee) {
        super(x, y, range, maintenanceFee, true);
    }
}
