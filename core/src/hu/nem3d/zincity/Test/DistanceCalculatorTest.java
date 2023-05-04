package hu.nem3d.zincity.Test;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Cell.CityCell;
import hu.nem3d.zincity.Cell.EmptyCell;
import hu.nem3d.zincity.Cell.RoadCell;
import hu.nem3d.zincity.Misc.DistanceCalculator;
import org.junit.Test;

import static org.junit.Assert.*;

public class DistanceCalculatorTest {
    @Test
    public void testDistance1(){
        TiledMapTileLayer map = new TiledMapTileLayer(3, 3, 1, 1);
        map.setCell(0, 0, new RoadCell(0, 0, map));
        CityCell cell1 = new EmptyCell(0,1, map);
        map.setCell(0, 1, cell1);
        CityCell cell2 = new EmptyCell(1,0, map);
        map.setCell(1, 0, cell2);
        assertEquals(2, DistanceCalculator.distance(cell1, cell2));
    }

    @Test
    public void testDistance2(){
        /*
        [r] [r] [r]
        [r] [_] [c]
        [r] [c] [r]
        */
        TiledMapTileLayer map = new TiledMapTileLayer(3, 3, 1, 1);
        map.setCell(0, 0, new RoadCell(0, 0, map));
        map.setCell(2, 2, new RoadCell(2, 2, map));
        for (int i = 1; i < 3; i++) {
            map.setCell(i, 0, new RoadCell(i, 0, map));
            map.setCell(0, i, new RoadCell(0, i, map));
        }
        CityCell cell1 = new EmptyCell(2,1, map);
        map.setCell(2, 1, cell1);
        CityCell cell2 = new EmptyCell(1,2, map);
        map.setCell(1, 2, cell2);
        assertEquals(2, DistanceCalculator.distance(cell1, cell2));
    }

    @Test
    public void testDistanceUnreachable(){
        TiledMapTileLayer map = new TiledMapTileLayer(3, 3, 1, 1);
        CityCell cell1 = new EmptyCell(0,1, map);
        map.setCell(0, 1, cell1);
        CityCell cell2 = new EmptyCell(1,0, map);
        map.setCell(1, 0, cell2);
        assertEquals(-1, DistanceCalculator.distance(cell1, cell2));
    }

    @Test
    public void testDistanceDifferentLayer() {
        TiledMapTileLayer map1 = new TiledMapTileLayer(3, 3, 1, 1);
        CityCell cell1 = new EmptyCell(0,0, map1);
        map1.setCell(0, 0, cell1);
        TiledMapTileLayer map2 = new TiledMapTileLayer(3, 3, 1, 1);
        CityCell cell2 = new EmptyCell(0,1, map2);
        map2.setCell(0, 1, cell2);
        assertEquals(-1, DistanceCalculator.distance(cell1, cell2));
    }

    @Test
    public void testIsConnectedTrue(){
        TiledMapTileLayer map = new TiledMapTileLayer(3, 3, 1, 1);
        map.setCell(0, 0, new RoadCell(0, 0, map));
        CityCell cell1 = new EmptyCell(0,1, map);
        map.setCell(0, 1, cell1);
        CityCell cell2 = new EmptyCell(1,0, map);
        map.setCell(1, 0, cell2);
        assertTrue(DistanceCalculator.isConnected(cell1, cell2));
    }

    @Test
    public void testIsConnectedFalse(){
        TiledMapTileLayer map = new TiledMapTileLayer(3, 3, 1, 1);
        CityCell cell1 = new EmptyCell(0,1, map);
        map.setCell(0, 1, cell1);
        CityCell cell2 = new EmptyCell(1,0, map);
        map.setCell(1, 0, cell2);
        assertFalse(DistanceCalculator.isConnected(cell1, cell2));
    }
}