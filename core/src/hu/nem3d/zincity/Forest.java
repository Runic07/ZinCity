package hu.nem3d.zincity;

public class Forest extends CityCell{
    private int age;

    public Forest() {
        this.age = 0;
    }

    public Forest(int x, int y) {
        super(x, y);
        this.age = 0;
    }

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}
    public void incAge(){age = (age == 10) ? 10 : age+1;}
}
