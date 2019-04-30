package ie.dylangore.dsa2.ca2.types;

import ie.dylangore.dsa2.ca2.data.ListManager;

public class Link {

    private Marker start, end;
    private String type, climate;
    private int score, distance;

    public Link(Marker start, Marker end, String type, String climate){
        setStart(start);
        setEnd(end);
        setType(type);
        setClimate(climate);
        setDistance(calculateDistance());

        this.getStart().getLinks().add(this);
        System.out.println("New Link: " + this.getStart().getName() + " -> " + this.getEnd().getName());
    }

    public Marker getStart() {
        return start;
    }

    private void setStart(Marker start) {
        if(start != null && start != this.getEnd()){
            this.start = start;
        }
    }

    public Marker getEnd() {
        return end;
    }

    private void setEnd(Marker end) {
        if(end != null && end != this.getStart()){
            this.end = end;
        }
    }

    public String getType() {
        return type;
    }

    private void setType(String type) {
        switch(type.toLowerCase()){
            case "sea":
                this.type = "sea";
                break;
            default:
                this.type = "land";
                break;
        }
    }

    public int getScore() {
        return score;
    }

    private void setScore(int score) {
        if(score > 0){
            this.score = score;
        }
    }

    public String getClimate() {
        return climate;
    }

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

    private int calculateDistance(){
        double p1 = Math.pow((this.getEnd().getXCoordinate() - this.getStart().getXCoordinate()), 2);
        double p2 = Math.pow((this.getEnd().getYCoordinate() - this.getStart().getYCoordinate()), 2);
        double sqrtIn = p1-p2;

        if(sqrtIn < 0)
            sqrtIn = sqrtIn * -1;

        return (int)Math.abs(Math.sqrt(sqrtIn));
    }

    public int getDistance() {
        return distance;
    }

    private void setDistance(int distance) {
        this.distance = distance;
    }

    public String toString(){
        return this.getStart().getName() + " -> " + this.getEnd().getName() + " (D: " + this.getDistance() + ")";
    }
}
