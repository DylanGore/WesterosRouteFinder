package ie.dylangore.dsa2.ca2.types;

import ie.dylangore.dsa2.ca2.data.ListManager;

import java.io.Serializable;

public class Marker implements Serializable {

    private int xCoordinate;
    private int yCoordinate;
    private String name;
    private String affiliation;
    private Region region;

    public Marker(int x, int y, String name, String affiliation, Region region){
        this.setXCoordinate(x);
        this.setYCoordinate(y);
        this.setName(name);
        this.setAffiliation(affiliation);
        this.setRegion(region);

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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String toString(){
        return this.getName() + " [" + this.getXCoordinate() + ", " + this.getYCoordinate() + "]";
    }
}
