package hu.nem3d.zincity.Logic;

import hu.nem3d.zincity.Cell.IndustrialZoneCell;
import hu.nem3d.zincity.Cell.LivingZoneCell;
import hu.nem3d.zincity.Cell.ZoneCell;

public class Citizen {
    //private City city; //It's necessary, if the Citizen calculates its own workplace-home combo
    private double satisfaction;
    private ZoneCell workplace;
    private LivingZoneCell home;
    private IndustrialZoneCell nearestIndustrial;

    public Citizen(){
        this.satisfaction = 0.0;
    }

    public Citizen(ZoneCell workplace, LivingZoneCell home){
        this.workplace = workplace;
        this.home = home;
        this.satisfaction = 0.0;
    }

    public ZoneCell getWorkplace() {return workplace;}
    public void setWorkplace(ZoneCell workplace) {this.workplace = workplace;}
    public LivingZoneCell getHome() {return home;}
    public void setHome(LivingZoneCell home) {this.home = home;}
    public double getSatisfaction() {return satisfaction;}


    //needs current budget, negativeSince from city, and ratio of services/industrial - 1
    public double calculateSatisfaction(double budget, int negativeSince, double ratio){
        double result = 0.0;
        //TODO examine all condition based on the calculated math behind the game
        //need a function here that calculates distances between tiles

        return result;
    }

}
