import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Cell.*;
import hu.nem3d.zincity.Misc.CellException;
import hu.nem3d.zincity.Misc.Direction;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class CityCellTest {
    @Ignore
    @Test
    //Use this to test the job runner
    public void testFail(){

        fail("Test fail");
    }

    @Test
    public void testIsWired(){
        try {
            TiledMapTileLayer map = new TiledMapTileLayer(2,2,1,1);
            map.setCell(0, 1, new EmptyCell(0, 1, map));
            map.setCell(0, 0, new RoadCell(0, 0, map));
            FireStationCell cell = new FireStationCell(0, 1, map, false);
            map.setCell(0, 1, cell);
            assertTrue(cell.isWired());
        } catch (CellException ce) {
            fail("Failed Initialization: " + ce.getMessage());
        }
    }

    @Test
    public void testNeighbor(){
        TiledMapTileLayer map = new TiledMapTileLayer(2,2,1,1);
        CityCell cell = new EmptyCell(0, 1, map);
        map.setCell(0, 1, cell);
        map.setCell(0, 0, new RoadCell(0, 0, map));
        assertEquals(RoadCell.class, cell.getNeighbor(Direction.SOUTH).getClass());
    }

    @Test
    public void testRadius(){
        TiledMapTileLayer map = new TiledMapTileLayer(2,2,1,1);
        CityCell cell1 = new EmptyCell(0, 1, map);
        map.setCell(0, 1, cell1);
        CityCell cell2 = new RoadCell(0, 0, map);
        map.setCell(0, 0, cell2);
        assertEquals(1.0, cell1.auraRadiusSize(cell2), 0);
    }
}
