package hu.nem3d.zincity.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.Random;

/**
 * Represents a cell of a forest
 */
public class ForestCell extends CityCell{
    private int age;




    /**
     * Constructs a ForestCell with parameters set as the coordinates
     * @param x The distance of this from the origin on the horizontal axis
     * @param y The distance of this from the origin on the vertical axis
     */
    public ForestCell(int x, int y, TiledMapTileLayer tileLayer) {
        super(x, y, tileLayer);
        this.age = 0;
        this.price = 5;
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
     * Grows a tree according to a random chance.
     * @return wether the tree has grown or not.
     */
    public boolean randomGrow(){
        Random r = new Random();
        if (r.nextInt(0,5) == 0 && this.age < 10){
            this.age++;
            return true;
        }
        return false;

    }

    /*
    //Suggestion for aging trees
    private int plantingDay;

    public ForestCell() {
        super();
        this.plantingDay = 0;
    }

    public ForestCell(int x, int y) {
        super(x, y);
        this.plantingDay = 0;
    }

    public ForestCell(int x, int y, int plantingDay) {
        super(x, y);
        this.plantingDay = plantingDay;
    }

    public int getPlantingDay() {return plantingDay;}
    public void setPlantingDay(int plantingDay) {this.plantingDay = plantingDay;}
    public int getAge(int now){return (now - plantingDay)/365;}
    */
}
