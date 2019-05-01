package ie.dylangore.dsa2.ca2.data;

import ie.dylangore.dsa2.ca2.types.Link;
import ie.dylangore.dsa2.ca2.types.Marker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Manages and gives access to lists
 */
public class ListManager {

    private static ObservableList<Marker> markerList;
    private static ObservableList<Link> linkList;
    private static ObservableList<String> affiliationList;
    private static ObservableList<String> regionList;


    /**
     * Initialise empty lists
     */
    public static void init(){

        markerList = FXCollections.observableArrayList();
        linkList = FXCollections.observableArrayList();
        affiliationList = FXCollections.observableArrayList();
        regionList = FXCollections.observableArrayList();
    }

    /**
     * Search through markerList to get a marker with a given name
     * @param name name to search with
     * @return marker
     */
    public static Marker getMarkerByName(String name){
        for(int i = 0; i < getMarkerList().size(); i++){
            Marker marker = getMarkerList().get(i);
            if(marker.getName().equals(name)){
                return marker;
            }
        }
        return null;
    }

    /**
     * Search through markerList to get a marker with a given x and y value
     * @param x x coordinate
     * @param y y coordinate
     * @return marker
     */
    public static Marker getMarkerByXY(int x, int y){
        for(int i = 0; i < getMarkerList().size(); i++){
            Marker marker = getMarkerList().get(i);
            if(marker.getXCoordinate() == x && marker.getYCoordinate() == y){
                return marker;
            }
        }
        return null;
    }

    /**
     * Get links stored in each marker and add them to the master linkList
     */
    public static void getLinksFromMarkers(){
        getLinkList().clear();
        for(Marker marker: getMarkerList()){
            getLinkList().addAll(marker.getLinks());
        }
    }

    /**
     * Get markerList
     * @return markerList
     */
    public static ObservableList<Marker> getMarkerList() {
        return markerList;
    }

    /**
     * Get affiliationList
     * @return affiliationList
     */
    public static ObservableList<String> getAffiliationList(){
        return affiliationList;
    }

    /**
     * Get regionList
     * @return regionList
     */
    public static ObservableList<String> getRegionList(){
        return regionList;
    }

    /**
     * Get linkList
     * @return linkList
     */
    public static ObservableList<Link> getLinkList() {
        return linkList;
    }
}
