package hu.nem3d.zincity.ATempClasses;

import hu.nem3d.zincity.Cell.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TestMap{

    CityCell[][] citycells;

    public TestMap(int x, int y) {
        citycells = new CityCell[x][y];

    }

    public boolean addCell(int x, int y, CityCell t) {
        if(citycells[x][y] == null) {
            citycells[x][y] = t;
            return true;
        }else {
            return false;
        }
    }

    public void fillRest() {
        for (int i = 0; i < citycells.length; i++) {
            for (int j = 0; j < citycells[i].length; j++) {
                if(citycells[i][j] == null) {
                    citycells[i][j] = new EmptyCell(i, j);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tiles: [ ] = empty, [r] - road, [h] - living, [x] - industrial, [s] - service\n");
        for (int i = 0; i < citycells.length; i++) {
            for (int j = 0; j < citycells[i].length; j++) {
                if(citycells[i][j] instanceof LivingZoneCell) {
                    sb.append("[h] ");
                }else if(citycells[i][j] instanceof IndustrialZoneCell) {
                    sb.append("[x] ");
                } else if(citycells[i][j] instanceof ServiceZoneCell) {
                    sb.append("[s] ");
                } else if(citycells[i][j] instanceof RoadCell) {
                    sb.append("[r] ");
                } else {
                    sb.append("[ ] ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public CityCell getTile(int x, int y) {
        if(x < 0 || y < 0 || x >= citycells.length || y >= citycells[0].length) {
            return null;
        }else {
            return citycells[x][y];
        }
    }


    public IndustrialZoneCell closestIndustrial(LivingZoneCell h) {
        HashMap<IndustrialZoneCell, Integer> indDist = new HashMap<>();
        HashSet<CityCell> visited = new HashSet<>();
        seekingDistanceInd(h, 0, indDist, visited);
        //seekingDistanceInd(h, 0, null, indDist);

        System.out.println("\nClosest Industrials:");
        for(Map.Entry<IndustrialZoneCell, Integer> e : indDist.entrySet()) {
            System.out.println(e.getKey().getX() + " " + e.getKey().getY() + " " + e.getValue());
        }
        return null;
    }

    public ServiceZoneCell closestService(LivingZoneCell l) {
        return null;
    }

	/*
	//StackOverflow
	private  void seekingDistanceInd(CityCell t, int distance, Direction from, HashMap<IndustrialZoneCell, Integer> indDist){
		CityCell nextTile = null;
		System.out.println(t.x + " " + t.y + " " + distance);
		if(from != Direction.North) {
			nextTile = getTile(t.x, t.y-1);
			if(nextTile != null) {
				if(nextTile.getClass() == IndustrialZoneCell.class) {indDist.put((IndustrialZoneCell) nextTile, distance+1);}
				else if(nextTile.getClass() == RoadCell.class) {seekingDistanceInd(nextTile, distance+1, Direction.South, indDist);}
			}
		}
		if(from != Direction.West) {
			nextTile = getTile(t.x-1, t.y);
			if(nextTile != null) {
				if(nextTile.getClass() == IndustrialZoneCell.class) {indDist.put((IndustrialZoneCell) nextTile, distance+1);}
				else if(nextTile.getClass() == RoadCell.class) {seekingDistanceInd(nextTile, distance+1, Direction.East, indDist);}
			}
		}
		if(from != Direction.South) {
			nextTile = getTile(t.x, t.y+1);
			if(nextTile != null) {
				if(nextTile.getClass() == IndustrialZoneCell.class) {indDist.put((IndustrialZoneCell) nextTile, distance+1);}
				else if(nextTile.getClass() == RoadCell.class) {seekingDistanceInd(nextTile, distance+1, Direction.North, indDist);}
			}
		}
		if(from != Direction.East) {
			nextTile = getTile(t.x+1, t.y);
			if(nextTile != null) {
				if(nextTile.getClass() == IndustrialZoneCell.class) {indDist.put((IndustrialZoneCell) nextTile, distance+1);}
				else if(nextTile.getClass() == RoadCell.class) {seekingDistanceInd(nextTile, distance+1, Direction.West, indDist);}
			}
		}
	}*/

    private  void seekingDistanceInd(CityCell t, int distance, HashMap<IndustrialZoneCell, Integer> indDist, HashSet<CityCell> visited){
        System.out.println(t.getX() + " " + t.getY() + " " + distance);

        ArrayList<CityCell> neighbours = new ArrayList<CityCell>();
        if(t.getX()+1 < citycells.length && t.getX()+1 >= 0) {
            neighbours.add(citycells[t.getX()+1][t.getY()]);
        }
        if(t.getY()+1 < citycells[0].length && t.getY()+1 >= 0) {
            neighbours.add(citycells[t.getX()][t.getY()+1]);
        }
        if(t.getX()-1 < citycells.length && t.getX()-1 >= 0) {
            neighbours.add(citycells[t.getX()-1][t.getY()]);
        }
        if(t.getY()-1 < citycells[0].length && t.getY()-1 >= 0) {
            neighbours.add(citycells[t.getX()][t.getY()-1]);
        }

        visited.add(t);
        int newDist = distance+1;

        for (CityCell nextTile : neighbours) {
            System.out.print(" -> " + nextTile.getX() + " " + nextTile.getY());
            if(!visited.contains(nextTile)) {
                if(nextTile.getClass() == IndustrialZoneCell.class) {indDist.put((IndustrialZoneCell) nextTile, newDist);}
                else if(nextTile.getClass() == RoadCell.class) {
                    System.out.print("\n");
                    seekingDistanceInd(nextTile, newDist, indDist, visited);
                }
            }
        }
    }

    private  void seekingDistanceSer(CityCell t, int distance, HashMap<ServiceZoneCell, Integer> indDist, HashSet<CityCell> visited){
        System.out.println(t.getX() + " " + t.getY() + " " + distance);

        ArrayList<CityCell> neighbours = new ArrayList<CityCell>();
        if(t.getX()+1 < citycells.length && t.getX()+1 >= 0) {
            neighbours.add(citycells[t.getX()+1][t.getY()]);
        }
        if(t.getY()+1 < citycells[0].length && t.getY()+1 >= 0) {
            neighbours.add(citycells[t.getX()][t.getY()+1]);
        }
        if(t.getX()-1 < citycells.length && t.getX()-1 >= 0) {
            neighbours.add(citycells[t.getX()-1][t.getY()]);
        }
        if(t.getY()-1 < citycells[0].length && t.getY()-1 >= 0) {
            neighbours.add(citycells[t.getX()][t.getY()-1]);
        }

        visited.add(t);
        int newDist = distance+1;

        for (CityCell nextTile : neighbours) {
            if(nextTile != null) {
                if(!visited.contains(nextTile)) {
                    if(nextTile.getClass() == ServiceZoneCell.class) {indDist.put((ServiceZoneCell) nextTile, newDist);}
                    else if(nextTile.getClass() == RoadCell.class) {
                        //seekingDistanceInd(nextTile, newDist, indDist, visited);
                    }
                }
            }
        }
    }
}
