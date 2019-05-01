package ie.dylangore.dsa2.ca2.data;

import ie.dylangore.dsa2.ca2.types.Link;
import ie.dylangore.dsa2.ca2.types.Marker;

/**
 * Temporary storage and error checking for creating new links
 */
public class LinkManager {

    private static Marker start = null, end = null;
    private static String type = null, climate = null;

    /**
     * Get start point
     * @return start point
     */
    public static Marker getStart() {
        return start;
    }

    /**
     * Set start point
     * @param start start point
     */
    public static void setStart(Marker start) {
        LinkManager.start = start;
    }

    /**
     * Get end point
     * @return end point
     */
    private static Marker getEnd() {
        return end;
    }

    /**
     * Set end point
     * @param end end point
     */
    public static void setEnd(Marker end) {
        LinkManager.end = end;
    }

    /**
     * Get climate
     * @return climate
     */
    private static String getClimate() {
        return climate;
    }

    /**
     * Set climate
     * @param climate climate
     */
    public static void setClimate(String climate) {
        LinkManager.climate = climate;
    }

    /**
     * Get terrain type
     * @return terrain type
     */
    private static String getType() {
        return type;
    }

    /**
     * Set terrain type
     * @param type terrain type
     */
    public static void setType(String type) {
        LinkManager.type = type;
    }

    /**
     * Check if each variable is valid
     * @return isValid
     */
    private static boolean isValid(){
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

    /**
     * Create new link and reverse-link
     */
    public static void addLink(){
        if(isValid()){
            new Link(getStart(), getEnd(), getType().toLowerCase(), getClimate().toLowerCase());
            // Add reverse link
            new Link(getEnd(), getStart(), getType().toLowerCase(), getClimate().toLowerCase());
        }
    }
}
