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
    private int plantingYear;

    public ForestCell() {
        this.plantingYear = 2023;
    }

    public ForestCell(int x, int y, int plantingYear) {
        super(x, y);
        this.plantingYear = plantingYear;
    }

    public int getPlantingYear() {return plantingYear;}
    public void setPlantingYear(int plantingYear) {this.plantingYear = plantingYear;}
    public int getAge(int yearNow){return (yearNow - plantingYear);}
    */
}
