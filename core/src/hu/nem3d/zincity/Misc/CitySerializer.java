package hu.nem3d.zincity.Misc;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import hu.nem3d.zincity.Cell.*;
import hu.nem3d.zincity.Logic.Citizen;
import hu.nem3d.zincity.Logic.City;
import hu.nem3d.zincity.Logic.CityMap;

import java.util.concurrent.CopyOnWriteArrayList;

public class CitySerializer implements Json.Serializer<City> {
    @Override
    public void write(Json json, City city, Class knownType) {
        json.writeObjectStart();

        json.writeValue("budget", city.budget);
        json.writeValue("taxCoefficient", city.taxCoefficient);
        json.writeValue("baseTaxAmount", city.baseTaxAmount);
        json.writeValue("satisfaction", city.satisfaction);

        // Serialize citizens

        json.writeArrayStart("citizens");
        for (Citizen citizen : city.citizens) {
            json.writeObjectStart();

            json.writeValue("homeX", citizen.getHome().getX());
            json.writeValue("homeY", citizen.getHome().getY());
            json.writeValue("workplaceX", citizen.getHome().getX());
            json.writeValue("workplaceY", citizen.getHome().getY());
            json.writeValue("satisfaction", citizen.getSatisfaction());


            json.writeObjectEnd();
        }
        json.writeArrayEnd();

        json.writeValue("satisfactionUpperThreshold", city.satisfactionUpperThreshold);
        json.writeValue("satisfactionLowerThreshold", city.satisfactionLowerThreshold);

        // Serialize cityMap

        json.writeArrayStart("cityMap");
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 20; j++) {
                json.writeObjectStart();  // Start a new object for each cell

                CityCell cell = (CityCell) city.cityMap.getBuildingLayer().getCell(i, j);

                json.writeValue("class", cell.getClass().getName());  // Serialize class name
                json.writeValue("x", cell.getX());
                json.writeValue("y", cell.getY());



                if (cell instanceof ForestCell){
                    json.writeValue("age", ((ForestCell) cell).getAge());
                }
                if (cell instanceof RoadCell){
                    json.writeValue("rotation", cell.getRotation());
                }
                if (cell instanceof ArenaCell || cell instanceof GeneratorCell){
                    json.writeValue("part", ((BuildingCell) cell).getPart());
                }
                json.writeObjectEnd();  // End the object for each cell

            }
        }
        json.writeArrayEnd();

        json.writeObjectEnd();
    }

    @Override
    public City read(Json json, JsonValue jsonData, Class type) {
        City city = new City();

        city.budget = json.readValue("budget", double.class, jsonData);
        city.taxCoefficient = json.readValue("taxCoefficient", double.class, jsonData);
        city.baseTaxAmount = json.readValue("baseTaxAmount", double.class, jsonData);
        city.satisfaction = json.readValue("satisfaction", double.class, jsonData);

        // Deserialize cityMap
        JsonValue cityMapArray = jsonData.get("cityMap");
        for (JsonValue cellData : cityMapArray) {

            String className = json.readValue("class", String.class, jsonData);

            Class<?> objectClass = null;
            CityCell cell = null;
            try {
                objectClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                System.err.println("Could not initialize a class with classname " + className);
            }
            try {
                cell = (CityCell) objectClass.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            int x = json.readValue("x", int.class, cellData);
            int y = json.readValue("y", int.class, cellData);
            cell.setX(x);
            cell.setY(y);
            // Create and add the appropriate CityCell subclass based on the class name

            switch (className) {
                case "ForestCell":

                    ((ForestCell) cell).setAge(json.readValue("age", int.class, cellData));
                    break;
                case "RoadCell":

                    ((RoadCell) cell).setRotation(json.readValue("rotation", int.class, cellData));
                    break;
                case "ArenaCell":

                    ((ArenaCell) cell).setPart(json.readValue("part", BuildingCell.BuildingPart.class, cellData));
                    break;
                case "GeneratorCell":

                    ((GeneratorCell) cell).setPart(json.readValue("part", BuildingCell.BuildingPart.class, cellData));
                    break;
                default:
                    //do nothing
                    break;
            }

            // Set the common properties of the CityCell

            city.cityMap.getBuildingLayer().setCell( x, y, cell);


        // Deserialize citizens
        city.citizens = new CopyOnWriteArrayList<>();
        JsonValue citizensArray = jsonData.get("citizens");
        for (JsonValue citizenData : citizensArray) {
            Citizen citizen = new Citizen();
            citizen.getHome().setX(json.readValue("homeX", int.class, citizenData));
            citizen.getHome().setY(json.readValue("homeY", int.class, citizenData));
            citizen.getWorkplace().setX(json.readValue("workplaceX", int.class, citizenData));
            citizen.getWorkplace().setY(json.readValue("workplaceY", int.class, citizenData));
            citizen.setSatisfaction(json.readValue("satisfaction", double.class, citizenData));
            city.citizens.add(citizen);
        }

        city.satisfactionUpperThreshold = json.readValue("satisfactionUpperThreshold", double.class, jsonData);
        city.satisfactionLowerThreshold = json.readValue("satisfactionLowerThreshold", double.class, jsonData);


        }


        return city;
    }
}
