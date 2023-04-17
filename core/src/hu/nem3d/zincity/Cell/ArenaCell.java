package hu.nem3d.zincity.Cell;

public class ArenaCell extends BuildingCell{   //It's a dummy class for building implementation

    public ArenaCell(int range, int maintenanceFee){
        super(range, maintenanceFee, false);
        this.name = "Arena";
    }
    @Override
    public void doEffect(){
    }
}
