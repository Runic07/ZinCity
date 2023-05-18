package hu.nem3d.zincity.Misc;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import hu.nem3d.zincity.Cell.CityCell;
import hu.nem3d.zincity.Logic.Citizen;
import hu.nem3d.zincity.Logic.City;
import hu.nem3d.zincity.Logic.CityMap;

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
        json.writeObjectStart();
        json.writeArrayStart("cityMap");
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 20; j++) {
                json.writeObjectStart(String.valueOf(i+j));
                CityCell cell = (CityCell) city.cityMap.getBuildingLayer().getCell(i,j);

                json.addClassTag("class", cell.getClass());
                json.toJson(cell.getX());
                json.toJson(cell.getY());
                json.writeObjectEnd();



            }
        }
        json.writeArrayEnd();

        json.writeObjectEnd();
    }

    @Override
    public City read(Json json, JsonValue jsonData, Class type) {
        City city = new City();

        city.budget = jsonData.getDouble("budget");
        city.taxCoefficient = jsonData.getDouble("taxCoefficient");
        city.baseTaxAmount = jsonData.getDouble("baseTaxAmount");
        city.satisfaction = jsonData.getDouble("satisfaction");

        // Deserialize citizens
        JsonValue citizensArray = jsonData.get("citizens");
        for (JsonValue citizenData : citizensArray) {
            Citizen citizen = new Citizen();
            // Read citizen properties here
            // Example: citizen.setName(citizenData.getString("name"));
            city.citizens.add(citizen);
        }

        city.satisfactionUpperThreshold = jsonData.getDouble("satisfactionUpperThreshold");
        city.satisfactionLowerThreshold = jsonData.getDouble("satisfactionLowerThreshold");

        // Deserialize cityMap
        JsonValue cityMapData = jsonData.get("cityMap");
        // Read cityMap properties here
        // Example: city.cityMap.setWidth(cityMapData.getInt("width"));

        return city;
    }
}
