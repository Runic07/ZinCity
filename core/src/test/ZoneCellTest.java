import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Cell.EmptyCell;
import hu.nem3d.zincity.Cell.LivingZoneCell;
import hu.nem3d.zincity.Cell.RoadCell;
import hu.nem3d.zincity.Misc.CellException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZoneCellTest {

    @Test
    public void testZoneCell(){
        try {
            TiledMapTileLayer map = new TiledMapTileLayer(2,2,1,1);
            map.setCell(0, 1, new EmptyCell(0, 1, map));
            map.setCell(0, 0, new RoadCell(0, 0, map));
            map.setCell(0, 1, new LivingZoneCell(0, 1, map));
            assertEquals(LivingZoneCell.class, map.getCell(0, 1).getClass());
        } catch (CellException ce) {
            fail("Failed Initialization: " + ce.getMessage());
        }
    }

    @Test
    public void testOccupant(){
        try {
            TiledMapTileLayer map = new TiledMapTileLayer(2,2,1,1);
            map.setCell(0, 1, new EmptyCell(0, 1, map));
            map.setCell(0, 0, new RoadCell(0, 0, map));
            LivingZoneCell home = new LivingZoneCell(0, 1, map);
            map.setCell(0, 1, home);

            home.addOccupant();
            assertEquals(0, home.removeOccupant());
        } catch (CellException ce) {
            fail("Failed Initialization: " + ce.getMessage());
        }
    }

    @Test
    public void testZoneCellError(){
        assertThrows(CellException.class, this::constructingZone);
    }

    private void constructingZone() throws CellException{
        new LivingZoneCell(0, 0, new TiledMapTileLayer(2, 2, 2, 2));
    }

    @Test
    public void testLevelUp(){
        try {
            TiledMapTileLayer map = new TiledMapTileLayer(2,2,1,1);
            map.setCell(0, 1, new EmptyCell(0, 1, map));
            map.setCell(0, 0, new RoadCell(0, 0, map));
            LivingZoneCell home = new LivingZoneCell(0, 1, map);
            map.setCell(0, 1, home);

            home.levelUp(8);
            int lvl1 = home.getLevel();
            home.levelUp(16);
            int lvl2 = home.getLevel();

            assertTrue(!home.levelUp(32) && lvl1 < lvl2);
        } catch (CellException ce) {
            fail("Failed Initialization: " + ce.getMessage());
        }
    }

}
