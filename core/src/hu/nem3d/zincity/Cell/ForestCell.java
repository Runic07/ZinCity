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
}
