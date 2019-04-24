package ie.dylangore.dsa2.ca2.types;

import ie.dylangore.dsa2.ca2.data.ListManager;

import java.io.Serializable;

public class Marker implements Serializable {

    private int xCoordinate, yCoordinate, temperature, terrain;
    private String name, affiliation, region;

    public Marker(int x, int y, String name, String affiliation, String region, int temperature, int terain){
        this.setXCoordinate(x);
        this.setYCoordinate(y);
        this.setName(name);
        this.setAffiliation(affiliation);
        this.setRegion(region);
        this.setTemperature(temperature);
        this.setTerrain(terain);

        ListManager.getMarkerList().add(this);
        System.out.println("New Marker @ [" + this.getXCoordinate() + ", " + this.getYCoordinate() +"] - " + this.getName() + " (" + this.getRegion() + " | " + this.getAffiliation() + ")");
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String toString(){
        return this.getName() + " [" + this.getXCoordinate() + ", " + this.getYCoordinate() + "]";
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getTerrain() {
        return terrain;
    }

    public void setTerrain(int terrain) {
        this.terrain = terrain;
    }
}
