package hu.nem3d.zincity.Cell;

public class FireStationCell extends BuildingCell{
    public FireStationCell(int range, int maintenanceFee){
        super(range, maintenanceFee, true);
        this.name = "Fire station";
    }
    public FireStationCell(int x, int y, int range, int maintenanceFee) {
        super(x, y, range, maintenanceFee, false);
        this.name = "Fire station";
    }

    public FireStationCell(int x, int y, int range, int maintenanceFee, BuildingPart part) {
        super(x, y, range, maintenanceFee, part);
        this.name = "Fire station";
    }
}
