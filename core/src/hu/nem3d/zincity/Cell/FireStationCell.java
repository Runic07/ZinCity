package hu.nem3d.zincity.Cell;

public class FireStationCell extends BuildingCell{
    public FireStationCell(int x, int y, int range, int maintenanceFee) {
        super(x, y, range, false);
        this.name = "Fire station";
        this.price = 20;
        this.upkeepCost = 5;
    }

    public FireStationCell(int x, int y, int range, int maintenanceFee, BuildingPart part) {
        super(x, y, range, maintenanceFee, part);
        this.name = "Fire station";
        this.price = 20;
        this.upkeepCost = 5;
    }
}
