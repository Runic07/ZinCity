package hu.nem3d.zincity.Misc;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Cell.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DistanceCalculator {

    public DistanceCalculator() {}

    /**
     * Calculates the distance between 2 cells form the chosen map
     * @param map The 2D matrix, where this does the searching/calculations
     * @param start The cell, where the calculation (and search) starts
     * @param destination The cell, where the calculation (and search) ends
     * @return The number of steps, that needs to be taken to reach the destination cell from the starting cell,
     * if the two cells are not connected by RoadCell, this value is -1
     */
    public static int distance(TiledMapTileLayer map, CityCell start, CityCell destination) {
        if (start == null || destination == null){return -1;}

        //Setting up the distances matrix with -1 values, this will represent the unreachable tiles
        int[][] distances = new int[map.getWidth()][map.getHeight()];
        for(int i = 0; i < map.getWidth(); ++i) {
            Arrays.fill(distances[i], -1);
        }

        distances[start.getX()][start.getY()] = 0;

        //Queue always has CityCells "sorted" by their distances in increasing order (It's standard FIFO queue.)
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

    /**
     * Searches for the closest workplace location from a LivingCellZone (based on certain conditions), meanwhile this
     * counts the "steps" that this takes to reach that workplace CityCell <p>
     * (More efficient and practical, than filtering all possible destination,
     * and making a minimum-search with the distance method)
     * @param map The 2D matrix, where this does the searching/calculations
     * @param home the cell, where the search begins from
     * @param workplaceType the class of the CityCell that this searches for
     * @param mustBeAvailable If this true, this should check, if any ZoneCell, that this finds, is not full
     * @return A tuple of the reached CityCell, that can qualify as a proper workplace (based on the conditions set in parameters),
     * and the distance between the 2 CityCells, if there is no such workplace, this returns the tuple of (null, -1)
     */
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

    /**
     * Collects the adjacent CityCells and calculates the distances between these CityCells and the starting cell of distances matrix
     * @param map The 2D matrix, where this does the searching/calculations
     * @param me The CityCell, which serves as the origin of this search
     * @param distances The matrix, that contains which cell is how far from the starting point
     * @return A list of proper CityCells, that are adjacent to this
     */
    private static List<CityCell> getGoodNeighbours(TiledMapTileLayer map, CityCell me, int[][] distances){
        ArrayList<CityCell> result = new ArrayList<>();

        int distNow = distances[me.getX()][me.getY()];

        for (Direction dir : Direction.values()) {
            CityCell current = me.getNeighbor(dir);
            if (current != null && distances[current.getX()][current.getY()] == -1) {
                distances[current.getX()][current.getY()] = distNow + 1;
                result.add((CityCell) map.getCell(current.getX(), current.getY()));
            }
        }
        return result;
    }

    /**
     * Checks if the coordinates are on the chosen map
     * @param map The 2D matrix, where this does the searching/calculations
     * @param x The distance of this from the origin on the horizontal axis
     * @param y  The distance of this from the origin on the vertical axis
     * @return True, if the given coordinates are on the chosen map
     */
    public static boolean isReachable(TiledMapTileLayer map, int x, int y) { //currently unused, it may change later
        return (x >= 0 && x < map.getWidth() && y >= 0 && y < map.getHeight());
    }
}
