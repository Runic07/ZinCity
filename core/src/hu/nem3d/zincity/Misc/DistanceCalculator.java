package hu.nem3d.zincity.Misc;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Cell.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DistanceCalculator {

    public DistanceCalculator() {}


    public static int distance(TiledMapTileLayer map, CityCell start, CityCell destination) {
        if (start == null || destination == null){return -1;}

        //Setting up the distances matrix with -1 values, this will represent the unreachable tiles
        int[][] distances = new int[map.getWidth()][map.getHeight()];
        for(int i = 0; i < map.getWidth(); ++i) {
            Arrays.fill(distances[i], -1);
        }

        distances[start.getX()][start.getY()] = 0;

        //Queue always has Citycells "sorted" by their distances in increasing order (It's standard FIFO queue.)
        LinkedList<CityCell> queue = new LinkedList<>();
        queue.addAll(getGoodNeighbours(map, start, distances));

        while(!queue.isEmpty()) {
            CityCell current = queue.remove(0);
            if(current.getClass() == RoadCell.class) {
                queue.addAll(getGoodNeighbours(map, current, distances));
            }
        }

        return distances[destination.getX()][destination.getY()];
    }


    public static Tuple<ZoneCell, Integer> closestOfWorkplaceWithDistance(TiledMapTileLayer map, LivingZoneCell home,
                                                                   Class<?> workplaceType, boolean mustBeAvailable) {

        //Setting up the distances matrix with -1 values, this will represent the unreachable tiles
        int[][] distances = new int[map.getWidth()][map.getHeight()];
        for(int i = 0; i < map.getWidth(); ++i) {
            Arrays.fill(distances[i], -1);
        }

        distances[home.getX()][home.getY()] = 0;

        ZoneCell destination = null;

        //Queue always has CityCells "sorted" by their distances in increasing order (It's standard FIFO queue.)
        LinkedList<CityCell> queue = new LinkedList<>();
        queue.addAll(getGoodNeighbours(map, home, distances));

        while(!queue.isEmpty()) {
            CityCell current = queue.remove(0);
            if(current.getClass() == workplaceType) {
                destination = (ZoneCell) current;
                if(mustBeAvailable) {
                    if(!destination.isFull()){break;}
                    else{destination = null;}
                }else{break;}
            }
            if(current.getClass() == RoadCell.class){
                queue.addAll(getGoodNeighbours(map, current, distances));
            }
        }

        Integer distance = ((destination != null ) ? distances[destination.getX()][destination.getY()] : -1);
        return (new Tuple<>(destination, distance));
    }


    private static List<CityCell> getGoodNeighbours(TiledMapTileLayer map, CityCell me, int[][] distances){
        ArrayList<CityCell> result = new ArrayList<>();

        int distNow = distances[me.getX()][me.getY()];

        CityCell current;

        if(isReachable(map, me.getX()+1, me.getY())) {
            if(distances[me.getX()+1][me.getY()] == -1) {
                distances[me.getX()+1][me.getY()] = distNow + 1;
                result.add((CityCell) map.getCell(me.getX()+1, me.getY()));
            }
        }

        if(isReachable(map, me.getX()-1, me.getY())) {
            if(distances[me.getX()-1][me.getY()] == -1) {
                distances[me.getX()-1][me.getY()] = distNow + 1;
                result.add((CityCell) map.getCell(me.getX()-1, me.getY()));
            }
        }

        if(isReachable(map, me.getX(), me.getY()+1)) {
            if(distances[me.getX()][me.getY()+1] == -1) {
                distances[me.getX()][me.getY()+1] = distNow + 1;
                result.add((CityCell) map.getCell(me.getX(), me.getY()+1));
            }
        }

        if(isReachable(map, me.getX(), me.getY()-1)) {
            if(distances[me.getX()][me.getY()-1] == -1) {
                distances[me.getX()][me.getY()-1] = distNow + 1;
                result.add((CityCell) map.getCell(me.getX(), me.getY()-1));

            }
        }

        return result;
    }

   
    public static boolean isReachable(TiledMapTileLayer map, int x, int y) {
        return (x >= 0 && x < map.getWidth() && y >= 0 && y < map.getHeight());
    }
}
