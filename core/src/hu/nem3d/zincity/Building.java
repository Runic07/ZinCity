package hu.nem3d.zincity;

public abstract class Building extends CityCell{

    protected enum BuildingPart{
        UpLeft, UpRight, DownLeft, DownRight;
    }

    protected BuildingPart part;
    protected int range;
    protected int maintenanceFee;

    //Temp constructor for testing purposes
    protected Building(int range, int maintenanceFee) {
        super();
        this.range = range;
        this.maintenanceFee = maintenanceFee;
    }

    protected Building(int x, int y, int range, int maintenanceFee) {
        super(x, y);
        this.range = range;
        this.maintenanceFee = maintenanceFee;
        this.part = BuildingPart.UpLeft;
        //TODO decide whether this calls the creation of the other tiles or the CityMap does all 4 together
            //based on the choice, we may refactor the 'BuildingPart' enum
    }

    protected Building(int x, int y, int range, int maintenanceFee, BuildingPart part) {
        super(x, y);
        this.range = range;
        this.maintenanceFee = maintenanceFee;
        this.part = part;
    }

    public abstract void doEffect();
}


