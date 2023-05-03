package hu.nem3d.zincity.Test;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Cell.IndustrialZoneCell;
import hu.nem3d.zincity.Cell.LivingZoneCell;
import hu.nem3d.zincity.Cell.RoadCell;
import hu.nem3d.zincity.Cell.ServiceZoneCell;
import hu.nem3d.zincity.Logic.Citizen;
import hu.nem3d.zincity.Logic.City;
import hu.nem3d.zincity.Misc.CellException;
import org.junit.Test;

import static org.junit.Assert.*;

public class CityTest {
    @Test
    public void testAddingCitizen(){
        try {
            City city = new City(true);
            TiledMapTileLayer map = city.getCityMap().getBuildingLayer();

            map.setCell(0, 1, new RoadCell(0, 1, map));
            map.setCell(0, 0, new LivingZoneCell(0, 0, map));
            map.setCell(0, 2, new IndustrialZoneCell(0, 2, map));

            assertTrue(city.addCitizen());
        } catch(CellException ce) {
            fail("Failed Initialization: " + ce.getMessage());
        }
    }

    @Test
    public void testSatisfactionDecrease(){
        try {
            City city = new City(true);
            TiledMapTileLayer map = city.getCityMap().getBuildingLayer();

            map.setCell(0, 1, new RoadCell(0, 1, map));
            map.setCell(0, 0, new LivingZoneCell(0, 0, map));
            map.setCell(0, 2, new IndustrialZoneCell(0, 2, map));

            if(city.addCitizen()){
                Citizen firstCitizen = city.citizens.get(0);
                double lastSatisfaction = firstCitizen.getSatisfaction();

                city.step();

                assertTrue(firstCitizen.getSatisfaction() < lastSatisfaction);
            }else{
                fail("Error occurred while adding citizen!");
            }
        } catch(CellException ce) {
            fail("Failed Initialization: " + ce.getMessage());
        }
    }

    @Test
    public void testSatisfactionIncrease(){
        try {
            City city = new City(true);
            TiledMapTileLayer map = city.getCityMap().getBuildingLayer();

            map.setCell(0, 1, new RoadCell(0, 1, map));
            map.setCell(0, 0, new LivingZoneCell(0, 0, map));
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
        } catch(CellException ce) {
            fail("Failed Initialization: " + ce.getMessage());
        }
    }
}
