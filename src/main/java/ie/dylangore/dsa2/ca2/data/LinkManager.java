package ie.dylangore.dsa2.ca2.data;

import ie.dylangore.dsa2.ca2.types.Link;
import ie.dylangore.dsa2.ca2.types.Marker;

public class LinkManager {

    private static Marker start = null, end = null;
    private static String type = null, climate = null;

    public static Marker getStart() {
        return start;
    }

    public static void setStart(Marker start) {
        LinkManager.start = start;
    }

    public static Marker getEnd() {
        return end;
    }

    public static void setEnd(Marker end) {
        LinkManager.end = end;
    }

    public static String getClimate() {
        return climate;
    }

    public static void setClimate(String climate) {
        LinkManager.climate = climate;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        LinkManager.type = type;
    }

    public static boolean isValid(){
        // Check if each value is set
        if(getEnd() != null && getStart() != null && getType() != null && getClimate() != null){
            // Check that start and end point are not the same
            if(getStart() == getEnd()){
                System.out.println("Start and end points cannot be the same!");
                return false;
            }

            // Check if an existing link exists between the two points
            for(Link link: start.getLinks()){
                if(link.getEnd() == getEnd()){
                    System.out.println("A link between these two points already exists!");
                    return false;
                }
            }

            return true;
        }else{
            return false;
        }
    }

    public static void addLink(){
        if(isValid()){
            new Link(getStart(), getEnd(), getType().toLowerCase(), getClimate().toLowerCase());
            // Add reverse link
            new Link(getEnd(), getStart(), getType().toLowerCase(), getClimate().toLowerCase());
        }
    }

    public static void clear(){
        setStart(null);
        setEnd(null);
        setType(null);
        setClimate(null);
    }
}
