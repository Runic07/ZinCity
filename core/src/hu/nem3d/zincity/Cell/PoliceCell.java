package hu.nem3d.zincity.Cell;

public class PoliceCell extends BuildingCell{
    public PoliceCell(int range, int maintenanceFee){
        super(range, maintenanceFee, true);
        this.name = "Police station";
    }
    public PoliceCell(int x, int y, int range, int maintenanceFee) {
        super(x, y, range, maintenanceFee, false);
        this.name = "Police station";
    }

    public PoliceCell(int x, int y, int range, int maintenanceFee, BuildingPart part) {
        super(x, y, range, maintenanceFee, part);
        this.name = "Police station";
    }
}
