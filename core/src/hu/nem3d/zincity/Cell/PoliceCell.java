package hu.nem3d.zincity.Cell;

public class PoliceCell extends BuildingCell{
    public PoliceCell(int range, int maintenanceFee){
        super(range, maintenanceFee, true);
        this.name = "Police station";
    }
}
