package ie.dylangore.dsa2.ca2.types;

import ie.dylangore.dsa2.ca2.data.ListManager;

import java.util.ArrayList;

/**
 * Location/Marker on map (graph node)
 */
public class Marker {

    private int xCoordinate, yCoordinate;
    private String name, affiliation, region;
    private ArrayList<Link> links;
    private ArrayList<Marker> nearby;

    public int nodeValue = Integer.MAX_VALUE;

    /**
     * Constructor - create new marker
     * @param x x coordinate
     * @param y y coordinate
     * @param name marker name
     * @param affiliation marker affiliation
     * @param region marker region
     */
    public Marker(int x, int y, String name, String affiliation, String region){
        this.setXCoordinate(x);
        this.setYCoordinate(y);
        this.setName(name);
        this.setAffiliation(affiliation);
        this.setRegion(region);
        this.links = new ArrayList<>();
        this.nearby = new ArrayList<>();

        ListManager.getMarkerList().add(this);
        System.out.println("New Marker @ [" + this.getXCoordinate() + ", " + this.getYCoordinate() +"] - " + this.getName() + " (" + this.getRegion() + " | " + this.getAffiliation() + ")");
    }

    /**
     * Get X coordinate
     * @return x coordinate
     */
    public int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * Set X coordinate
     * @param xCoordinate x coordinate
     */
    private void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * Get Y coordinate
     * @return y coordinate
     */
    public int getYCoordinate() {
        return yCoordinate;
    }

    /**
     * Set Y coordinate
     * @param yCoordinate y coordinate
     */
    private void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    /**
     * Get marker name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set marker name
     * @param name desired name
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * Get marker affiliation
     * @return affiliation
     */
    public String getAffiliation() {
        return affiliation;
    }

    /**
     * Set marker affiliation
     * @param affiliation desired affiliation
     */
    private void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    /**
     * Get marker region
     * @return region
     */
    public String getRegion() {
        return this.region;
    }

    /**
     * Set marker region
     * @param region desired region
     */
    private void setRegion(String region) {
        this.region = region;
    }

    /**
     * Get list of links
     * @return links
     */
    public ArrayList<Link> getLinks() {
        return links;
    }

    /**
     * Get list of nearby markers
     * @return nearby
     */
    public ArrayList<Marker> getNearby() {
        return nearby;
    }

    /**
     * toString method - used for displaying this object as a String
     * @return string
     */
    public String toString(){
        return this.getName() + " [" + this.getXCoordinate() + ", " + this.getYCoordinate() + "]";
    }
}
