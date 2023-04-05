package hu.nem3d.zincity;

public class Road extends CityCell{
    public Road() {
    }

    public Road(int x, int y) {
        super(x, y);
    }

    //For efficiency purposes: because of this, there is no need for checking class
    @Override
    public boolean isRoad() {return true;}
}
