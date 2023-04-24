package hu.nem3d.zincity.Cell;

public class GeneratorCell extends BuildingCell {
    public GeneratorCell(int x, int y, int range, int maintenanceFee) {
        super(x, y, range, false);
        this.name = "Generator";
        this.price = 20; //fair if price reduction is called 4 times. Not fair if only once.
        this.upkeepCost = 7.5;
    }

    public GeneratorCell(int x, int y, int range, int maintenanceFee, BuildingPart part) {
        super(x, y, range, maintenanceFee, part);
        this.name = "Generator";
        this.price = 20;
        this.upkeepCost = 7.5;
    }
}
