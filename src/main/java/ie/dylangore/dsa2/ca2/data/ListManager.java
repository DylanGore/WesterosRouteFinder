package ie.dylangore.dsa2.ca2.data;

import ie.dylangore.dsa2.ca2.types.Link;
import ie.dylangore.dsa2.ca2.types.Marker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class ListManager {

    private static ObservableList<Marker> markerList;
    private static ObservableList<Link> linkList;
    private static ObservableList<String> affiliationList;
    private static ObservableList<String> regionList;


    public static void init(){

        markerList = FXCollections.observableArrayList();
        linkList = FXCollections.observableArrayList();
        affiliationList = FXCollections.observableArrayList();
        regionList = FXCollections.observableArrayList();
    }

    public static Marker getMarkerByName(String name){
        for(int i = 0; i < getMarkerList().size(); i++){
            Marker marker = getMarkerList().get(i);
            if(marker.getName().equals(name)){
                return marker;
            }
        }
        return null;
    }

    public static Marker getMarkerByXY(int x, int y){
        for(int i = 0; i < getMarkerList().size(); i++){
            Marker marker = getMarkerList().get(i);
            if(marker.getXCoordinate() == x && marker.getYCoordinate() == y){
                return marker;
            }
        }
        return null;
    }

    public static ObservableList<Marker> getMarkerList() {
        return markerList;
    }

    public static ObservableList<String> getAffiliationList(){
        return affiliationList;
    }

    public static ObservableList<String> getRegionList(){
        return regionList;
    }

    public static ObservableList<Link> getLinkList() {
        return linkList;
    }
}
