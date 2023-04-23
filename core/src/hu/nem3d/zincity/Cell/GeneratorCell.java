package hu.nem3d.zincity.Cell;

public class GeneratorCell extends BuildingCell {
    public GeneratorCell(int range, int maintenanceFee) {
        super(range, maintenanceFee, false);
        this.name = "Generator";
    }

    public GeneratorCell(int x, int y, int range, int maintenanceFee) {
        super(x, y, range, maintenanceFee, false);
    }

    public GeneratorCell(int x, int y, int range, int maintenanceFee, BuildingPart part) {
        super(x, y, range, maintenanceFee, part);
    }
}
