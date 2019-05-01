package ie.dylangore.dsa2.ca2.data;

import ie.dylangore.dsa2.ca2.types.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Temporary storage and error checking for new routes
 */
public class RouteManager {

    private static Marker start, end;
    private static ArrayList<Marker> waypoints, avoidList;

    /**
     * Initialise empty variables
     */
    public static void init(){
        setStart(null);
        setEnd(null);
        setWaypoints(new ArrayList<>());
        setAvoidList(new ArrayList<>());
    }

    /**
     * Get start point
     * @return start marker
     */
    public static Marker getStart() {
        return start;
    }

    /**
     * Set start point
     * @param start start marker
     */
    public static void setStart(Marker start) {
        RouteManager.start = start;
    }

    /**
     * Get end point
     * @return end marker
     */
    public static Marker getEnd() {
        return end;
    }

    /**
     * Set end point
     * @param end end marker
     */
    public static void setEnd(Marker end) {
        RouteManager.end = end;
    }

    /**
     * Get list of waypoints
     * @return waypoints list
     */
    public static List<Marker> getWaypoints() {
        return waypoints;
    }

    /**
     * Set waypoints list
     * @param waypoints waypoints list
     */
    private static void setWaypoints(ArrayList<Marker> waypoints) {
        RouteManager.waypoints = waypoints;
    }

    /**
     * Get list of markers to avoid
     * @return avoid list
     */
    public static ArrayList<Marker> getAvoidList() {
        return avoidList;
    }

    /**
     * Set list of markers to avoid
     * @param avoidList avoid list
     */
    private static void setAvoidList(ArrayList<Marker> avoidList) {
        RouteManager.avoidList = avoidList;
    }
}
