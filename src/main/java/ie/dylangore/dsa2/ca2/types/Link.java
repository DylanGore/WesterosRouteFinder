package ie.dylangore.dsa2.ca2.types;

import ie.dylangore.dsa2.ca2.data.ListManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Link between two markers (graph edge)
 */
public class Link {

    private Marker start, end;
    private String type, climate;
    private int ease, safety, distance;

    /**
     * Constructor - Create new link
     * @param start start point
     * @param end end point
     * @param type terrain type (land, sea, mountain)
     * @param climate climate type (temperate, mild, desert)
     */
    public Link(Marker start, Marker end, String type, String climate){
        setStart(start);
        setEnd(end);
        setType(type);
        setClimate(climate);
        setDistance(calculateDistance());
        setEase(calculateEase());
        setSafety(calculateSafety());

        this.getStart().getLinks().add(this);
        this.getStart().getNearby().add(this.getEnd());
        System.out.println("New Link: " + this.getStart().getName() + " -> " + this.getEnd().getName());
    }

    /**
     * Get start point
     * @return start point
     */
    public Marker getStart() {
        return start;
    }

    /**
     * Set start point
     * @param start desired start point
     */
    private void setStart(Marker start) {
        if(start != null && start != this.getEnd()){
            this.start = start;
        }
    }

    /**
     * Get end point
     * @return end point
     */
    public Marker getEnd() {
        return end;
    }

    /**
     * Set end point
     * @param end desired end point
     */
    private void setEnd(Marker end) {
        if(end != null && end != this.getStart()){
            this.end = end;
        }
    }

    /**
     * Get terrain type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Set terrain type
     * @param type desired terrain type
     */
    private void setType(String type) {
        switch(type.toLowerCase()){
            case "sea":
                this.type = "sea";
                break;
            case "mountain":
                this.type = "mountain";
                break;
            default:
                this.type = "land";
                break;
        }
    }

    /**
     * Get climate
     * @return climate
     */
    public String getClimate() {
        return climate;
    }

    /**
     * Set climate
     * @param climate desired climate
     */
    private void setClimate(String climate) {
        switch(climate.toLowerCase()){
            case "mild":{
                this.climate = "mild";
                break;
            }
            case "desert":{
                this.climate = "desert";
                break;
            }
            default:{
                this.climate = "temperate";
                break;
            }
        }
    }

    /**
     * Calculate distance between start and end points using point geometry
     * @return distance
     */
    private int calculateDistance(){
        double p1 = Math.pow((this.getEnd().getXCoordinate() - this.getStart().getXCoordinate()), 2);
        double p2 = Math.pow((this.getEnd().getYCoordinate() - this.getStart().getYCoordinate()), 2);
        double sqrtIn = p1-p2;

        if(sqrtIn < 0)
            sqrtIn = sqrtIn * -1;

        return (int)Math.abs(Math.sqrt(sqrtIn));
    }

    /**
     * Get distance value
     * @return distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Set distance value
     * @param distance desired distance
     */
    private void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Calculate easiness score
     * @return easiness score
     */
    private int calculateEase(){
        int score = 0;

        // Factor in terrain type
        switch(getType()){
            case "sea":{
                score = score + 2;
            }
            case "mountain":{
                score = score + 3;
            }
            default:{
                score = score + 1;
            }
        }

        // Factor in climate
        switch(getType()){
            case "mild":{
                score = score + 2;
            }
            case "desert":{
                score = score + 3;
            }
            default:{
                score = score + 1;
            }
        }

        // Factor in Region
        List<String> difficultRegions = new ArrayList<>();
        difficultRegions.add("The North");
        difficultRegions.add("Dorne");
        difficultRegions.add("Islands");
        difficultRegions.add("The Vale");
        difficultRegions.add("Across the Narrow Sea");
        if(difficultRegions.contains(this.getStart().getRegion()) || difficultRegions.contains(this.getEnd().getRegion())){
            score = score + 2;
        }

        return score;
    }

    /**
     * Get easiness score
     * @return easiness score
     */
    public int getEase() {
        return ease;
    }

    /**
     * Set easiness score
     * @param ease easiness score
     */
    private void setEase(int ease) {
        this.ease = ease;
    }

    /**
     * Calculate safety score
     * @return safety score
     */
    private int calculateSafety(){
        int score = 0;

        // Factor in terrain type
        switch(getType()){
            case "sea":{
                score = score + 2;
            }
            case "mountain":{
                score = score + 3;
            }
        }

        // Factor in climate
        switch(getType()){
            case "mild":{
                score = score + 1;
            }
            case "desert":{
                score = score + 2;
            }
        }

        // Factor in Affiliation
        score = getAffiliationScore(score, start);

        score = getAffiliationScore(score, end);

        // Special cases
        List<Marker> specialCases = new ArrayList<>();
        specialCases.add(ListManager.getMarkerByName("Valyria"));
        specialCases.add(ListManager.getMarkerByName("Asshai"));
        if(specialCases.contains(getStart()) || specialCases.contains(getEnd()))
            score = score + 8;
        return score;
    }

    /**
     * Calculate change to score depending on affiliation
     * @param score current score
     * @param marker place to get affiliation of
     * @return new score
     */
    private int getAffiliationScore(int score, Marker marker) {
        switch(marker.getAffiliation()){
            case "Daenerys":
                score = score + 2;
            case "The Crown":
                score = score + 4;
            case "House Stark":
                score = score + 2;
            case "House Greyjoy":
                score = score + 2;
            case "House Arryn":
                score = score + 1;
            case "House Martell":
                score = score + 3;
            case "Dothraki":
                score = score + 4;
            case "The Night King":
                score = score + 5;
            case "Self":
                score = score + 1;
            case "Abandoned":
                score = score + 2;
            default:
                score = score + 1;
        }
        return score;
    }

    /**
     * Get safety score
     * @return safety score
     */
    public int getSafety() {
        return safety;
    }

    /**
     * Set safety score
     * @param safety safety score
     */
    private void setSafety(int safety) {
        this.safety = safety;
    }

    /**
     * toString method - used for displaying this object as a String
     * @return string
     */
    public String toString(){
        return this.getStart().getName() + " -> " + this.getEnd().getName() + " (D: " + this.getDistance() + ")";
    }
}
