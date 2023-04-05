package hu.nem3d.zincity;

public class Citizen {
    //private City city; //It's necessary, if the Citizen calculates its own workplace-home combo
    private double satisfaction;
    private Zone workplace;
    private LivingZone home;
    private IndustrialZone nearestIndustrial;

    //TODO decide whether this or the City calculates the perfect workplace-home combination

    public Citizen(){
        this.satisfaction = 0.0;
    }

    public Citizen(Zone workplace, LivingZone home){
        this.workplace = workplace;
        this.home = home;
        this.satisfaction = 0.0;
    }

    public Zone getWorkplace() {return workplace;}
    public void setWorkplace(Zone workplace) {this.workplace = workplace;}
    public LivingZone getHome() {return home;}
    public void setHome(LivingZone home) {this.home = home;}
    public double getSatisfaction() {return satisfaction;}


    //needs current budget, negativeSince from city, and ratio of services/industrial - 1
    public double calculateSatisfaction(double budget, int negativeSince, double ratio){
        double result = 0.0;
        //TODO examine all condition based on the calculated math behind the game
        //need a function here that calculates distances between tiles
        satisfaction = result;
        return satisfaction;
    }

}

//Temp classes
class Zone{}
class LivingZone{}
class IndustrialZone{}
