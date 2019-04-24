package ie.dylangore.dsa2.ca2.data;

import ie.dylangore.dsa2.ca2.types.Marker;

import java.util.ArrayList;
import java.util.List;

public class RouteManager {

    private static Marker start;
    private static Marker end;
    private static ArrayList<Marker> waypoints;

    public static void init(){
        setStart(null);
        setEnd(null);
        setWaypoints(new ArrayList<>());
    }

    public static Marker getStart() {
        return start;
    }

    public static void setStart(Marker start) {
        RouteManager.start = start;
    }

    public static Marker getEnd() {
        return end;
    }

    public static void setEnd(Marker end) {
        RouteManager.end = end;
    }

    public static List<Marker> getWaypoints() {
        return waypoints;
    }

    public static void setWaypoints(ArrayList<Marker> waypoints) {
        RouteManager.waypoints = waypoints;
    }
}
