package hu.nem3d.zincity.Misc;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Cell.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Provides some useful methods for checking if the connection, distance between two CityCells
 */
public final class DistanceCalculator {

    /**
     * Private constructor, that prevents the class of having any instances, because this only consists of static methods
     */
    private DistanceCalculator(){}

    /**
     * Calculates the distance between 2 cells form the chosen map
     * @param start The cell, where the calculation (and search) starts
     * @param destination The cell, where the calculation (and search) ends
     * @return The number of steps, that needs to be taken to reach the destination cell from the starting cell,
     * if the two cells are not connected by RoadCell, this value is -1
     */
    public static int distance(CityCell start, CityCell destination) {
        if(start == null || destination == null){return -1;}
        if(start == destination){return 0;}

        TiledMapTileLayer map = start.getTileLayer();

        //Setting up the distances matrix with -1 values, this will represent the unreachable tiles
        int[][] distances = new int[map.getWidth()][map.getHeight()];
        for(int i = 0; i < map.getWidth(); ++i) {
            Arrays.fill(distances[i], -1);
        }

        distances[start.getX()][start.getY()] = 0;

        //Queue always has CityCells "sorted" by their distances in increasing order (It's standard FIFO queue.)
        LinkedList<CityCell> queue = new LinkedList<>(getGoodNeighbours(map, start, distances));

        while(!queue.isEmpty()) {
            CityCell current = queue.remove(0);
            if(current == destination){
                return distances[destination.getX()][destination.getY()];
            }
            if(current.getClass() == RoadCell.class) {
                queue.addAll(getGoodNeighbours(map, current, distances));
            }
        }

        return -1;
    }

    /**
     * Checks if the two given CityCells are connected by a road (technically: sequence of RoadCells)
     * @param start The cell, where the search starts
     * @param destination The cell, that this method is trying to reach by travelling only on road
     * @return True, if the two given cell are connected by road, otherwise false
     */
    public static boolean isConnected(CityCell start, CityCell destination) {
        if (start == null || destination == null){return false;}

        TiledMapTileLayer map = start.getTileLayer();

        boolean[][] visited = new boolean[map.getWidth()][map.getHeight()];

        //Queue always has CityCells "sorted" by their distances in increasing order (It's standard FIFO queue.)
        LinkedList<CityCell> queue = new LinkedList<>();
        queue.add(start);

        while(!queue.isEmpty()) {
            CityCell current = queue.remove(0);
            if(current == destination){
                return true;
            }
            if(current == start || current.getClass() == RoadCell.class) {
                for (Direction dir : Direction.values()) {
                    CityCell neighbor = current.getNeighbor(dir);
                    if (neighbor != null && !visited[neighbor.getX()][neighbor.getY()]) {
                        visited[neighbor.getX()][neighbor.getY()] = true;
                        queue.add(neighbor);
                    }
                }
            }
        }
        return false;
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

    //Stashed method
    /*
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
    private static Tuple<ZoneCell, Integer> closestOfWorkplaceWithDistance(TiledMapTileLayer map, LivingZoneCell home,
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
    }*/
}
