package hu.nem3d.zincity.Logic;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import hu.nem3d.zincity.Cell.*;
import hu.nem3d.zincity.Misc.Builder;
import hu.nem3d.zincity.Misc.DistanceCalculator;
import hu.nem3d.zincity.Screen.Effects;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Engine class for an instance of a city. Responsible for storing the map, and logic
 * related to managing budget, satisfaction and citizens
 *
 */
public class City {

    public CopyOnWriteArrayList<Citizen> citizens; //using this because ArrayList iterator is a clown
    //ain't it fun kids, fixing concurrency bugs?
    public double budget;
    public double taxCoefficient; //double between 0.8-1.2, can be changed by player
    public final double baseTaxAmount; //tax per citizen

    public double satisfaction; //sum of satisfactions
    public final double satisfactionUpperThreshold = 0.2; //above this number, it's possible to receive new inhabitants
    public final double satisfactionLowerThreshold = -0.8; //below this, a citizen may flee.

    private Effects effects;

    public int fireFighters;  //how many fires can you put out in one step (it should be based on the fireDep count and how near it is but whatever for now)

    private LinkedList<CityCell> fireOrder;  //in which order to put out the fire

    Random r = new Random();
    CityMap cityMap; //generates and stores the map

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
        effects = new Effects(cityMap);
        fireOrder = new LinkedList<>();
        fireFighters = 0;
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
                if (cell.getClass() == LivingZoneCell.class && !(((ZoneCell) cell).isFull()) && !foundHome){

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
     */
    public void step(){ //a unit of time passes
        System.out.println("Current citizen satisfactions: ");
        System.out.println(citizens.toString());
        satisfaction = 0;


        for (Citizen citizen : citizens) {
            budget += baseTaxAmount * taxCoefficient;


            citizen.setSatisfaction(
                    citizen.getSatisfaction() +
                            citizen.getSatisfaction() * 0.05 + //previous satisfaction added with small weight
                            (1 / taxCoefficient - 1) * 0.05 -//tax coeff added, scaled down
                            ((double) DistanceCalculator.distance(citizen.getHome(), citizen.getWorkplace()) - 10.0) * 0.01 - //distance from workplace
                            //bonus if <10 tile (linear)
                            1.0 / (DistanceCalculator.nearestIndustrialDistance(citizen.getHome()) * 3) //distance from nearest industrial (hyperbolic)

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
                if(cell.getOnFire()){ //this is for the neighbours that are not rendered when its neighbour sets them on fire
                    effects.setOnFire(i,j);
                }

                if(buildingLayer.getCell(i,j).getClass() != EmptyCell.class
                   && buildingLayer.getCell(i,j).getClass() != BlockedCell.class
                   && buildingLayer.getCell(i,j).getClass() != RoadCell.class
                   && buildingLayer.getCell(i,j).getClass() != PowerLineCell.class
                   && buildingLayer.getCell(i,j).getClass() != FireStationCell.class){
                    if(r.nextInt(100) < 2){
                        effects.setOnFire(i,j);
                    }
                    if(cell.getOnFireFor() >=1){
                        if(!fireOrder.contains(cell)){
                            fireOrder.add(((CityCell) buildingLayer.getCell(i,j)));
                        }
                    }
                    if(cell.getOnFireFor() >=10){
                        Builder builder = new Builder(0,0,this);
                        builder.buildWhat("delete");
                        builder.build(i,j,buildingLayer);
                        effects.putOutFire(i,j);
                    }
                    ((CityCell) buildingLayer.getCell(i,j)).burning();
                }


            }

        }


        for(int i = 0; i < fireFighters; i++){
            if(fireOrder.size() > 0){
                CityCell cell = fireOrder.getFirst();
                fireOrder.removeFirst();
                int x = cell.getX();
                int y = cell.getY();
                effects.putOutFire(x,y);
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
