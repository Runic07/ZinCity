package hu.nem3d.zincity.Cell;

public class FireStationCell extends BuildingCell {
    public FireStationCell(int range, int maintenanceFee) {
        super(range, maintenanceFee, true);
    }

    public FireStationCell(int x, int y, int range, int maintenanceFee) {
        super(x, y, range, maintenanceFee, true);
    }
}
