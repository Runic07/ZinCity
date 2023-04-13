package hu.nem3d.zincity.Cell;

/**
 * Represents a cell of a forest
 */
public class ForestCell extends CityCell{
    private int age;

    /**
     * Constructs a ForestCell with the coordinates of origin and 0 as an age
     */
    public ForestCell() {
        super();
        this.age = 0;
    }

    /**
     * Constructs a ForestCell with parameters set as the coordinates
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     */
    public ForestCell(int x, int y) {
        super(x, y);
        this.age = 0;
    }

    /**
     * Gets the age of this
     * @return The age of this
     */
    public int getAge() {return age;}

    /**
     * Changes the age of this to the value of parameter
     * @param age The age of this to be set to
     */
    public void setAge(int age) {this.age = age;}

    /**
     * Ages this by one unit (unless it reached the max value (10))
     */
    public void increaseAge(){age = (age == 10) ? 10 : age+1;}
}
