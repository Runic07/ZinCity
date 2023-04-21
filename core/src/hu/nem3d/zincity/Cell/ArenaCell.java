package hu.nem3d.zincity.Cell;

public class ArenaCell extends BuildingCell{   //It's a dummy class for building implementation

    public ArenaCell(int range, int maintenanceFee){
        super(range, maintenanceFee, false);
        this.name = "Arena";
    }
    public ArenaCell(int x, int y, int range, int maintenanceFee) {
        super(x, y, range, maintenanceFee, false);
        this.name = "Arena";
    }

    public ArenaCell(int x, int y, int range, int maintenanceFee, BuildingPart part) {
        super(x, y, range, maintenanceFee, part);
        this.name = "Arena";
    }
}
