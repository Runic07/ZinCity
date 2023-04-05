package hu.nem3d.zincity;

public class LivingZone extends Zone{
    public LivingZone(int capacity) {
        super(capacity);
    }
    public LivingZone(int x, int y, int capacity) {
        super(x, y, capacity);
    }

    //For efficiency purposes: because of this, there is no need for checking class
    @Override
    public boolean isLiving() {return true;}
}
