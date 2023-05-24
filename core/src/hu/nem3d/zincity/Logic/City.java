package hu.nem3d.zincity.Logic;


import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import hu.nem3d.zincity.Cell.*;
import hu.nem3d.zincity.Misc.BuildingEffect;
import hu.nem3d.zincity.Misc.Direction;
import hu.nem3d.zincity.Misc.DistanceCalculator;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Engine class for an instance of a city. Responsible for storing the map, and logic
 * related to managing budget, satisfaction and citizens
 *
 */
public class City{

    public CopyOnWriteArrayList<Citizen> citizens; //using this because ArrayList iterator is a clown
    //ain't it fun kids, fixing concurrency bugs?


    public double budget;
    public double taxCoefficient; //double between 0.8-1.2, can be changed by player
    public double baseTaxAmount; //tax per citizen

    public double satisfaction; //sum of satisfactions
    public double satisfactionUpperThreshold = 0.2; //above this number, it's possible to receive new inhabitants
    public double satisfactionLowerThreshold = -0.8; //below this, a citizen may flee.

    Random r = new Random();
    public CityMap cityMap; //generates and stores the map

    public CityMap getCityMap() {
        return cityMap;
    }

    public void setCityMap(CityMap cityMap) {
        this.cityMap = cityMap;
    }

    public City() {
        budget = 500;
        satisfaction = 0.0;
        taxCoefficient = 1.0;
        baseTaxAmount = 50;
        cityMap = new CityMap();
        citizens = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (addCitizen()) {
                System.out.println("Added starter citizen");
            }
        }
    }

    /**
     * Method to add a citizen to the city.
     * If a certain satisfaction threshold is reached city-wide,
     * citizens will move in at a random rate
     * if any vacant LivingZoneTile is present, and they have either a
     * Service or Industrial zone to work in.
     *
     * @return true if adding a citizen succeeded, false otherwise
     */
    public boolean addCitizen(){
        Citizen citizen = new Citizen();
        boolean foundHome = false;
        boolean foundWorkplace = false;
        TiledMapTileLayer tl = (TiledMapTileLayer) cityMap.getMap().getLayers().get(1);
        TiledMapTileLayer.Cell homeCell = null;
        TiledMapTileLayer.Cell workCell = null;

        for (int i=0; i < 30; i++){
            for (int j=0; j < 20; j++){

                TiledMapTileLayer.Cell cell = tl.getCell(i,j);

                //find home
                //TODO extra feature: choose randomly from available homes
                if (cell.getClass() == LivingZoneCell.class && !(((ZoneCell) cell).isFull()) && !foundHome && ((CityCell) cell).isElectrified()){

                    //cast is only needed in theory, to get the associated methods. should not actually change the class.

                    homeCell = cell;


                    foundHome=true;


                }
                //find workplace
                if (((cell.getClass() == IndustrialZoneCell.class) || (cell.getClass() == ServiceZoneCell.class)) && !(((ZoneCell) cell).isFull()) && !foundWorkplace){

                    workCell = cell;
                    foundWorkplace = true;

                }

            }
        }
        if (foundHome && foundWorkplace){
            citizen.setHome((LivingZoneCell) homeCell);
            ((LivingZoneCell) homeCell).addOccupant();

            citizen.setWorkplace((ZoneCell) workCell);
            ((ZoneCell) workCell).addOccupant();

            citizens.add(citizen);

            return true;
        }
        else{
            return false;
        }

    }

    /**
     * Main game loop method. Ideally gets called every n-th frame in the screen implementation.
     * Responsible for moving the game forward a discrete time amount.
     * Updates every tile, and every citizen.
     *
     */
    public void step(){ //a unit of time passes
        System.out.println("Current citizen satisfactions: ");
        System.out.println(citizens.toString());
        satisfaction = 0;

        for (Citizen citizen : citizens) {
            budget += baseTaxAmount * taxCoefficient;
            if(citizen.getWorkplace().isElectrified()){budget -= (baseTaxAmount/4);}

            //TODO rework bonuses caused by effects
            double effectBonus = (citizen.getHome().getEffects().contains(BuildingEffect.Arena) ? 0.05 : 0.0)
                    + (citizen.getWorkplace().getEffects().contains(BuildingEffect.Arena) ? 0.05 : 0.0)
                    - (citizen.getHome().isElectrified() ? 0.0 : 0.1)
                    + (citizen.getHome().getEffects().contains(BuildingEffect.Police) ? 0.05 : 0.0);
            for (Direction dir : Direction.values()){
                CityCell neighbor = citizen.getHome().getNeighbor(dir);
                if(neighbor != null && neighbor.getEffects().contains(BuildingEffect.Police)){
                    effectBonus += 0.01;
                }
            }

            double forestSatisfactionBonus = 0.0;
            for (CityCell c: citizen.getHome().getImmediateNeighbors()
                 ) {
                if (c instanceof ForestCell){
                    forestSatisfactionBonus+=((ForestCell) c).getAge() * 0.005;
                }
            }
            citizen.setSatisfaction(
                    citizen.getSatisfaction() + effectBonus +
                            citizen.getSatisfaction() * 0.05 + //previous satisfaction added with small weight
                            (1 / taxCoefficient - 1) * 0.05 -//tax coeff added, scaled down
                            ((double) DistanceCalculator.distance(citizen.getHome(), citizen.getWorkplace()) - 10.0) * 0.01 - //distance from workplace
                            //bonus if <10 tile (linear)
                            1.0 / (DistanceCalculator.nearestIndustrialDistance(citizen.getHome()) * 3) + //distance from nearest industrial (hyperbolic)
                            forestSatisfactionBonus

            );
            System.out.print(citizen.getSatisfaction() + "\t");

            satisfaction += citizen.getSatisfaction();

            satisfaction -= Math.abs(cityMap.IndustrialZoneCount() - cityMap.ServiceZoneCount()) * 0.01; //ratio of different workplaces penalty (linear)

            if (citizen.getSatisfaction() < satisfactionLowerThreshold){
                if (r.nextInt() % 20 == 0){
                    citizen.getHome().removeOccupant();
                    citizen.getWorkplace().removeOccupant();
                    citizens.remove(citizen);

                }
            }

        }
        if (citizens.size() > 0){
            satisfaction = satisfaction / ((double) citizens.size());
        }
        else{
            satisfaction = 0; //TODO Game over!
        }



        TiledMapTileLayer buildingLayer = (TiledMapTileLayer) cityMap.getMap().getLayers().get(1);
        for (int i = 0; i < 30; i++){ //no forall here unfortunately
            for (int j = 0; j < 20; j++){
                CityCell cell = (CityCell) buildingLayer.getCell(i,j);
                budget -= cell.getUpkeepCost();

                if(buildingLayer.getCell(i,j) instanceof ZoneCell
                   && ((ZoneCell) buildingLayer.getCell(i,j)).getLevel() == 1
                   && ((ZoneCell) buildingLayer.getCell(i,j)).getOccupants() >= ((ZoneCell) buildingLayer.getCell(i,j)).getCapacity() / 2) {

                        Object zoneType = buildingLayer.getCell(i,j).getClass();
                        TiledMapTileSet tileSet = cityMap.getTileSet();

                        if(LivingZoneCell.class == zoneType){
                                cell.setTile(tileSet.getTile(5 ));
                        }
                        else if(ServiceZoneCell.class == zoneType){
                                cell.setTile(tileSet.getTile(11 ));
                        }
                        else if(IndustrialZoneCell.class == zoneType){
                                cell.setTile(tileSet.getTile(8));
                        }
                }
                if (cell instanceof ForestCell){
                    boolean isUpdated = ((ForestCell) cell).randomGrow();
                    if (isUpdated && ((ForestCell) cell).getAge() == 10){
                       cell.setTile(cityMap.tileSet.getTile(3));
                    }
                }

            }

        }

        if (satisfaction > satisfactionUpperThreshold){
            if (r.nextInt() % 5 == 0){
                addCitizen();
            }
        }

        //spew info
        System.out.println("\nCurrent city satisfaction: " + satisfaction + "\nCurrent budget: " + budget + "\nCurrent tax coeff: " + taxCoefficient);
        System.out.println("---------------------------------");
    }


}
