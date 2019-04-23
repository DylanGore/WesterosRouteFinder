package ie.dylangore.dsa2.ca2.data;

import ie.dylangore.dsa2.ca2.types.Marker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class ListManager {

    public static ObservableList<Marker> markerList;
    public static ObservableList<String> affiliationList;
    public static ObservableList<String> regionList;


    public static void init(){

        markerList = FXCollections.observableArrayList();
        affiliationList = FXCollections.observableArrayList();
        regionList = FXCollections.observableArrayList();

//        addTestData();
    }

    private static void addTestData(){
        affiliationList.addAll("Daenerys", "The Crown", "Other");
        regionList.addAll("The North", "The Vale","The Westerlands","The Reach","The Stormlands","Dorne");
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
}
