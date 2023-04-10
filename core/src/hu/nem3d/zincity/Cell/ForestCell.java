package hu.nem3d.zincity.Cell;

public class ForestCell extends CityCell{
    private int age;

    public ForestCell() {
        this.age = 0;
    }

    public ForestCell(int x, int y) {
        super(x, y);
        this.age = 0;
    }

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}
    public void increaseAge(){age = (age == 10) ? 10 : age+1;}

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
