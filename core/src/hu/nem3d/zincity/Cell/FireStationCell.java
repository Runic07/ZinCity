package hu.nem3d.zincity.Cell;

public class FireStationCell extends BuildingCell{
    public FireStationCell(int range, int maintenanceFee){
        super(range, maintenanceFee, true);
        this.name = "Fire station";
    }
    @Override
    public void doEffect() {

    }
}
