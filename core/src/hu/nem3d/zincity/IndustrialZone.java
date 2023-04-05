package hu.nem3d.zincity;

public class IndustrialZone extends Zone{
    public IndustrialZone(int capacity) {
        super(capacity);
    }

    public IndustrialZone(int x, int y, int capacity) {
        super(x, y, capacity);
    }

    //For efficiency purposes: because of this, there is no need for checking class
    @Override
    public boolean isIndustrial() {
        return true;
    }
}
