import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Cell.*;
import hu.nem3d.zincity.Logic.Citizen;
import hu.nem3d.zincity.Logic.City;
import hu.nem3d.zincity.Logic.CityMap;
import org.junit.Test;

import static org.junit.Assert.*;

public class CityTest {
    @Test
    public void testAddingCitizen(){
        try {
            City city = new City();
            TiledMapTileLayer map = city.getCityMap().getBuildingLayer();

            map.setCell(0, 1, new RoadCell(0, 1, map));
            map.setCell(0, 0, new LivingZoneCell(0, 0, map));
            ((CityCell)map.getCell(0,0)).electrify(true);
            map.setCell(0, 2, new IndustrialZoneCell(0, 2, map));

            assertTrue(city.addCitizen());
        } catch(Exception e) {
            fail("Failed Initialization: " + e.getMessage());
        }
    }

    @Test
    public void testAddingCitizenFail() {
        City city = new City();
        assertFalse(city.addCitizen());
    }

    @Test
    public void testSatisfactionDecrease(){
        try {
            City city = new City();
            TiledMapTileLayer map = city.getCityMap().getBuildingLayer();

            map.setCell(0, 1, new RoadCell(0, 1, map));
            map.setCell(0, 0, new LivingZoneCell(0, 0, map));
            ((CityCell)map.getCell(0,0)).electrify(true);
            map.setCell(0, 2, new IndustrialZoneCell(0, 2, map));

            if(city.addCitizen()){
                Citizen firstCitizen = city.citizens.get(0);
                double lastSatisfaction = firstCitizen.getSatisfaction();

                city.step();

                assertTrue(firstCitizen.getSatisfaction() < lastSatisfaction);
            }else{
                fail("Error occurred while adding citizen!");
            }
        } catch(Exception e) {
            fail("Failed Initialization: " + e.getMessage());
        }
    }

    @Test
    public void testSatisfactionIncrease(){
        try {
            City city = new City();
            TiledMapTileLayer map = city.getCityMap().getBuildingLayer();

            map.setCell(0, 1, new RoadCell(0, 1, map));
            map.setCell(0, 0, new LivingZoneCell(0, 0, map));
            ((CityCell)map.getCell(0,0)).electrify(true);
            map.setCell(0, 2, new IndustrialZoneCell(0, 2, map));
            map.setCell(1, 1, new ServiceZoneCell(1, 1, map));

            if(city.addCitizen()){
                Citizen firstCitizen = city.citizens.get(0);
                double lastSatisfaction = firstCitizen.getSatisfaction();

                city.taxCoefficient = 0.2;
                city.step();

                assertTrue(firstCitizen.getSatisfaction() > lastSatisfaction);
            }else{
                fail("Error occurred while adding citizen!");
            }
        } catch(Exception e) {
            fail("Failed Initialization: " + e.getMessage());
        }
    }

    @Test
    public void testUpkeepCost(){
        try {
            City city = new City();
            double lastBudget = city.budget;

            TiledMapTileLayer map = city.getCityMap().getBuildingLayer();
            map.setCell(0, 0, new RoadCell(0, 0, map));
            map.setCell(0, 1, new PoliceCell(0, 1, map));

            city.step();

            assertTrue(lastBudget > city.budget);
        } catch(Exception e) {
            fail("Failed Initialization: " + e.getMessage());
        }
    }

    @Test
    public void testTax(){
        try {
            City city = new City();
            double lastBudget = city.budget;

            TiledMapTileLayer map = city.getCityMap().getBuildingLayer();
            map.setCell(0, 0, new RoadCell(0, 0, map));
            map.setCell(1, 0, new LivingZoneCell(1, 0, map));
            map.setCell(0, 1, new IndustrialZoneCell(0, 1, map));
            city.addCitizen();

            city.taxCoefficient = 2.0;

            city.step();
            city.step();

            assertTrue(lastBudget > city.budget);
        } catch(Exception e) {
            fail("Failed Initialization: " + e.getMessage());
        }
    }

    @Test
    public void testCityMapCounts(){

        try {
            CityMap cm = new CityMap();
            TiledMapTileLayer layer = cm.getBuildingLayer();

            layer.setCell(0, 0, new RoadCell(0, 0, layer));
            layer.setCell(1, 0, new ServiceZoneCell(1, 0, layer));
            layer.setCell(0, 1, new IndustrialZoneCell(0, 1, layer));

            layer.setCell(2, 2, new RoadCell(2, 2, layer));
            layer.setCell(3, 2, new ServiceZoneCell(3, 2, layer));
            layer.setCell(2, 3, new IndustrialZoneCell(2, 3, layer));

            assertEquals(0, cm.ServiceZoneCount() - cm.IndustrialZoneCount());
        } catch(Exception e) {
            fail("Failed Initialization: " + e.getMessage());
        }
    }
}
