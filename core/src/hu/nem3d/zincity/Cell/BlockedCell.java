package hu.nem3d.zincity.Cell;

public class BlockedCell extends CityCell{

    private String info; //For making StatBoard's info more unique (this attribute may contain data like "lake", "river")

    public BlockedCell() {
        this.info = "";
    }

    public BlockedCell(String info) {
        this.info = info;
    }

    public BlockedCell(int x, int y, String info) {
        super(x, y);
        this.info = info;
    }

    public String getInfo() {return info;}
    public void setInfo(String info) {this.info = info;}
}
