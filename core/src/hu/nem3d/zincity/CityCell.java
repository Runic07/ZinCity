package hu.nem3d.zincity;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class CityCell extends TiledMapTileLayer.Cell {
    //CLASS FOR EACH TILE ON THE MAP
    //should contain logic data.
    //equvivalent with "tile" class in UML diagram. Renamed to avoid confusion.

    //For saving coordinates, may be removed later
    private int x, y;

    //It will may need a rework, but now for efficiency purposes, those are 2 booleans
    //Obviously, each tile starts with no wire, electricity
    private boolean isElectrified = false;
    private boolean isWired = false;

    //placeholder constructor, may be removed later
    public CityCell() {
        super();
        this.x = 0;
        this.y = 0;
    }

    public CityCell(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    //Basic setters, getters
    public int getX() {return x;}
    public void setX(int x) {this.x = x;}

    public int getY() {return y;}
    public void setY(int y) {this.y = y;}

    public boolean isWired() {return isWired;}
    public void setWired(boolean wired) {isWired = wired;}

    public boolean isElectrified() {return isElectrified;}
    public void setElectrified(boolean elect) {
        if (elect) {
            if (isWired) {  //Electricity requires wires to flow through
                isElectrified = true;
            } else {
                throw new IllegalArgumentException("Can't have electricity without wires!");
            }
        } else {
            isElectrified = false;
        }
    }

    //Result of merging Position and Tile class from UML
    public double distance(CityCell c){
        return Math.sqrt(Math.pow((c.getX()-this.x), 2) + Math.pow((c.getY()-this.y), 2));
    }
}

