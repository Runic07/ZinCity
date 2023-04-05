package hu.nem3d.zincity;

public class ServiceZone extends Zone{
    public ServiceZone(int capacity) {
        super(capacity);
    }

    public ServiceZone(int x, int y, int capacity) {
        super(x, y, capacity);
    }

    //For efficiency purposes: because of this, there is no need for checking class
    @Override
    public boolean isService() {
        return true;
    }
}

