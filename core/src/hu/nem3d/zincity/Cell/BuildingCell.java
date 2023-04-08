package hu.nem3d.zincity.Cell;

public abstract class BuildingCell extends CityCell {

    protected enum BuildingPart{
        NorthEast, NorthWest, SouthEast, SouthWest
    }
    protected String name;  //Need for stats --Newton
    boolean isSimple;
    protected BuildingPart part;
    protected int range;
    protected int maintenanceFee;

    //Temp constructor for testing purposes
    protected BuildingCell(int range, int maintenanceFee, boolean isSimple) {
        super();
        this.range = range;
        this.maintenanceFee = maintenanceFee;
        this.isSimple = isSimple;
    }

    protected BuildingCell(int x, int y, int range, int maintenanceFee, boolean isSimple) {
        super(x, y);
        this.range = range;
        this.maintenanceFee = maintenanceFee;
        if (!isSimple){
            this.part = BuildingPart.NorthEast;
            //TODO call build method for other parts
        }
    }

    protected BuildingCell(int x, int y, int range, int maintenanceFee, BuildingPart part) {
        super(x, y);
        this.range = range;
        this.maintenanceFee = maintenanceFee;
        this.part = part;
    }

    public String getName(){
        return this.name;
    }

    public int getMaintenanceFee(){
        return this.maintenanceFee;
    }

    public abstract void doEffect();
}


