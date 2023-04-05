package hu.nem3d.zincity;

import java.util.HashSet;
import java.util.LinkedList;

public abstract class Zone extends CityCell{
    protected int capacity;
    protected int level = 1;
    protected final LinkedList<Citizen> occupants = new LinkedList<>();

    //I'm not quite sure, whether if it's the best choice for storing this data, but it ensures the uniqueness of each element
    protected HashSet<Direction> roadDirections = new HashSet<>();

    protected Zone(int capacity) {
        super();
        this.isWired = true;
        this.capacity = capacity;
    }

    protected Zone(int x, int y, int capacity) {
        super(x, y);
        this.isWired = true;
        this.capacity = capacity;
    }

    //Basic getters (may add/remove some (or add setters), if that will be necessary)
    public int getCapacity() {return capacity;}
    public int getLevel() {return level;}
    public int getOccCount() {return occupants.size();}
    public boolean isFull() {return capacity == occupants.size();};
    public HashSet<Direction> getRoadDirections() {return roadDirections;}

    //Methods revolving around roadDirections
    public boolean addDirection(Direction dir){
        return roadDirections.add(dir);
    }

    public boolean removeDirection(Direction dir){
        return roadDirections.remove(dir);
    }

    //Methods revolving around the moving of citizens
    public boolean addOccupant(Citizen occ){
        if(!isFull()){
            occupants.add(occ);
            return true;
        }else{
            return false;
        }
    }
    public boolean removeOccupant(Citizen occ){
        return occupants.remove(occ);
    }

    //Only way to change the value of level, capacity (this may change later)
    //Based on the math behind the game, this setter procedure may be changed
    // to a simple multiplication or summation in that case, no parameters needed
    public boolean levelUp(int cap){
        if(level == 3){
            return false;
        }else{
            level++;
            capacity = cap;
            return true;
        }
    }
}

//Temp class
//TODO remove this, after issue "24-create-citizen-class" is resolved
class Citizen{}
