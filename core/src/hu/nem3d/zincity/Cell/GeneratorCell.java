package hu.nem3d.zincity.Cell;

public class GeneratorCell extends BuildingCell{
    public GeneratorCell(int range, int maintenanceFee) {
        super(range, maintenanceFee, false);
        this.name = "Generator";
    }
}
