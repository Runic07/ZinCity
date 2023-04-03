package hu.nem3d.zincity;

public class Citizen {
    private double satisfaction;
    private CityCell workplace;
    private CityCell home;
    private CityCell nearestIndustrial;
    public Citizen(){
        this.satisfaction = 0.0;

    }
    //needs current budget, negativeSince from city, and ratio of services/industrial - 1
    public double calculateSatisfaction(double budget, int negativeSince, double ratio){
        return 0.0;
        //need a function here that calculates distances between tiles
    }

}
