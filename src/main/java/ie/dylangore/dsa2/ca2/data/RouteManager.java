package ie.dylangore.dsa2.ca2.data;

import ie.dylangore.dsa2.ca2.types.Marker;

import java.util.ArrayList;
import java.util.List;

public class RouteManager {

    private static Marker start, end;
    private static ArrayList<Marker> waypoints, avoidList;
    private static int safetyScore, difficultyScore;

    public static void init(){
        setStart(null);
        setEnd(null);
        setWaypoints(new ArrayList<>());
        setAvoidList(new ArrayList<>());
        setSafetyScore(0);
        setDifficultyScore(0);
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

    public static ArrayList<Marker> getAvoidList() {
        return avoidList;
    }

    public static void setAvoidList(ArrayList<Marker> avoidList) {
        RouteManager.avoidList = avoidList;
    }

    public static int getSafetyScore() {
        return safetyScore;
    }

    public static void setSafetyScore(int safetyScore) {
        RouteManager.safetyScore = safetyScore;
    }

    public static int getDifficultyScore() {
        return difficultyScore;
    }

    public static void setDifficultyScore(int difficultyScore) {
        RouteManager.difficultyScore = difficultyScore;
    }
}
