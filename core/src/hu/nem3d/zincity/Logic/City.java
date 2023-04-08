package hu.nem3d.zincity.Logic;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import hu.nem3d.zincity.Cell.*;

import java.util.ArrayList;

public class City {
    //still a skeleton
    public ArrayList<Citizen> citizens;


    public double budget;
    public double taxCoefficient; //double between 0.8-1.2, can be changed by player
    public final double baseTaxAmount; //tax per citizen

    public double satisfaction; //sum of satisfactions
    public final double satisfactionUpperThreshold = 0.2; //above this number, it's possible to receive new inhabitants
    public final double satisfactionLowerThreshold = -0.2; //below this, a citizen may flee.


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
        citizens = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (addCitizen()) {
                System.out.println("Added starter citizen");
            }
        }



    }

    public boolean addCitizen(){
        Citizen citizen = new Citizen();
        boolean foundHome = false;
        boolean foundWorkplace = false;

        for (int i=0; i < 30; i++){
            for (int j=0; j < 20; j++){
                TiledMapTileLayer tl = (TiledMapTileLayer) cityMap.getMap().getLayers().get(1);
                TiledMapTileLayer.Cell cell = tl.getCell(i,j);

                //find home
                if (cell.getClass() == LivingZoneCell.class && !((ZoneCell) cell).isFull() && !foundHome){
                    //bro how does this even work?
                    //cast is only needed in theory, to get the associated methods. should not actually change the class.
                    //but this collection of multiple types of objects kinda makes me want to puke
                    //also going to mess this up probably

                    citizen.setHome((LivingZoneCell) cell);
                    ((LivingZoneCell) cell).addOccupant();
                    foundHome=true;


                }
                //find workplace
                if ((cell.getClass() == IndustrialZoneCell.class || cell.getClass() == ServiceZoneCell.class) && !((ZoneCell) cell).isFull() && !foundWorkplace){

                    citizen.setWorkplace((ZoneCell) cell);
                    ((ZoneCell) cell).addOccupant();
                    foundWorkplace = true;

                }

            }
        }
        if (foundHome && foundWorkplace){
            citizens.add(citizen);
            return true;
        }
        else{
            return false;
        }

    }
    public void step(){ //a unit of time passes
        System.out.println("Current citizen satisfactions: ");
        for (Citizen citizen : citizens) {
            budget += baseTaxAmount;

            citizen.setSatisfaction(
                    citizen.getSatisfaction() + citizen.getSatisfaction() * 0.05 + //previous satisfaction added with small weight
                            (1 / taxCoefficient - 1) * 0.05 //tax coeff added, scaled down
                    //TODO +distance from workplace coeff
                    //TODO +distance from the nearest industry coeff



            );
            System.out.print(citizen.getSatisfaction() + "\t");
            satisfaction += citizen.getSatisfaction();


        }
        TiledMapTileLayer buildingLayer = (TiledMapTileLayer) cityMap.getMap().getLayers().get(1);
        for (int i = 0; i < 30; i++){ //no forall here unfortunately
            for (int j = 0; j < 20; j++){
                CityCell cell = (CityCell) buildingLayer.getCell(i,j);
                if (cell.getClass() == LivingZoneCell.class){
                    budget -=20;

                }
                if (cell.getClass() == IndustrialZoneCell.class){
                    budget -=40;

                }
                if (cell.getClass() == ServiceZoneCell.class){
                    budget -=30;

                }
            }

        }

        satisfaction = satisfaction / ((double) citizens.size());

        //spew info
        System.out.println("\nCurrent city satisfaction: " + satisfaction + "\nCurrent budget: " + budget + "\nCurrent tax coeff: " + taxCoefficient);
        System.out.println("---------------------------------");
    }

}
