package hu.nem3d.zincity.Cell;

public class PoliceCell extends BuildingCell{
    public PoliceCell(int x, int y, int range, int maintenanceFee) {
        super(x, y, range, false);
        this.name = "Police station";
        this.price = 80;
        this.upkeepCost = 10;
    }

    public PoliceCell(int x, int y, int range, int maintenanceFee, BuildingPart part) {
        super(x, y, range, maintenanceFee, part);
        this.name = "Police station";
        this.price = 80;
        this.upkeepCost = 10;
    }
}
