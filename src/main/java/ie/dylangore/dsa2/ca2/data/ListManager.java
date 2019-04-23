package ie.dylangore.dsa2.ca2.data;

import ie.dylangore.dsa2.ca2.types.Marker;
import ie.dylangore.dsa2.ca2.types.Region;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class ListManager {

    public static ObservableList<Marker> markerList;
    public static ObservableList<String> affiliationList;
    public static ObservableList<Region> regionList;


    public static void init(){

        markerList = FXCollections.observableArrayList();
        affiliationList = FXCollections.observableArrayList();
        regionList = FXCollections.observableArrayList();

        System.out.println("LIST INIT");

        addTestData();
    }

    private static void addTestData(){
        affiliationList.addAll("Daenerys", "The Crown", "Other");
        regionList.addAll(new Region("The North"), new Region("The Vale"), new Region("The Westerlands"), new Region("The Reach"), new Region("The Stormlands"), new Region("Dorne"));
    }

    public static ObservableList<Marker> getMarkerList() {
        return markerList;
    }

    public static ObservableList<String> getAffiliationList(){
        return affiliationList;
    }

    public static ObservableList<Region> getRegionList(){
        return regionList;
    }
}
