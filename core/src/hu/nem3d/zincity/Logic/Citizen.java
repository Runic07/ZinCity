package hu.nem3d.zincity.Logic;

import hu.nem3d.zincity.Cell.IndustrialZoneCell;
import hu.nem3d.zincity.Cell.LivingZoneCell;
import hu.nem3d.zincity.Cell.ZoneCell;

/**
 * Represents an occupant living in the city.
 * Knows its workplace and home.
 */
public class Citizen {
    //private City city; //It's necessary, if the Citizen calculates its own workplace-home combo
    private double satisfaction;
    private ZoneCell workplace;
    private LivingZoneCell home;
    private IndustrialZoneCell nearestIndustrial;

    /**
     * Constructs a citizen that has no home, workplace
     */
    public Citizen(){
        this.satisfaction = 0.0;
    }

    /**
     * Constructs a working citizen, who lives in a LivingZoneCell
     * @param workplace The IndustrialZoneCell or ServiceZoneCell, where this works
     * @param home The LivingZoneCell, which is the home of this
     */
    public Citizen(ZoneCell workplace, LivingZoneCell home){
        this.workplace = workplace;
        this.home = home;
        this.satisfaction = 0.0;
    }

    /**
     * Gets the workplace of this
     * @return The ZoneCell, where this works
     */
    public ZoneCell getWorkplace() {return workplace;}

    /**
     * Changes workplace of this to the parameter's value
     * @param workplace The ZoneCell, where this will work
     */
    public void setWorkplace(ZoneCell workplace) {this.workplace = workplace;}

    /**
     * Gets the home of this
     * @return The LivingZoneCell, where this lives
     */
    public LivingZoneCell getHome() {return home;}

    /**
     * Changes workplace of this to the parameter's value
     * @param home The LivingZoneCell, where this will live
     */
    public void setHome(LivingZoneCell home) {this.home = home;}

    /**
     * Gets satisfaction of this
     * @return A double value, which represents the current satisfaction of this
     */
    public double getSatisfaction() {return satisfaction;}

    /**
     * Changes the satisfaction of this to the parameter's value
     * @param satisfaction The double value, that will represent the satisfaction of this
     */
    public void setSatisfaction(double satisfaction) {
        if (satisfaction > 1.0) {
            this.satisfaction = 1.0;
        } else if (satisfaction < -1.0) {
            this.satisfaction = -1.0;
        } else {
            this.satisfaction = satisfaction;
        }
    }
    /*moved to city class


    //needs current budget, negativeSince from city, and ratio of services/industrial - 1
    public double calculateSatisfaction(double budget, int negativeSince, double ratio){
        double result = 0.0;
        //TODO examine all condition based on the calculated math behind the game
        //need a function here that calculates distances between tiles

        return result;
    }
    */


}
