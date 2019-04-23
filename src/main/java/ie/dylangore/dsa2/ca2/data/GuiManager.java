package ie.dylangore.dsa2.ca2.data;

import ie.dylangore.dsa2.ca2.types.Marker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GuiManager {

    private static AnchorPane mapPane;
    private static Label textLocation;

    // Create a new button on the map corresponding to the new marker
    public static void addMarkerButton(String name, int x, int y, String affiliation, String region){
        int btnSize = 32;

        new Marker(x, y, name, affiliation, region);

        Button markerButton = new Button();
        markerButton.getStyleClass().clear();
        markerButton.getStyleClass().add("mapBtn");
        markerButton.getStyleClass().add(getAffiliationClass(affiliation));
        markerButton.setText(name);
        markerButton.setMinSize(btnSize, btnSize);
        markerButton.setMaxSize(btnSize, btnSize);
        markerButton.setPrefSize(btnSize, btnSize);
        markerButton.setLayoutX(x - btnSize / 2);
        markerButton.setLayoutY(y - btnSize / 2);
        markerButton.setOnAction(e -> {
            textLocation.setText(markerButton.getText() + " [" + x + ", " + y + "]");
        });
        mapPane.getChildren().add(markerButton);
    }

    private static String getAffiliationClass(String aff){
        System.out.println("AFF: " + aff);
        switch(aff){
            case "Daenerys":
                return "targaryenMarker";
            case "The Crown":
                return "lannisterMarker";
            case "House Stark":
                return "starkMarker";
            case "House Greyjoy":
                return "greyjoyMarker";
            case "House Arryn":
                return "arrynMarker";
            case "House Martell":
                return "martellMarker";
            case "Dothraki":
                return "dothrakiMarker";
            case "The Night King":
                return "nightKingMarker";
            case "Self":
                return "selfMarker";
            case "Abandoned":
                return "abandonedMarker";
            default:
                return "otherMarker";
        }
    }

    public static void clearAllMarkerButtons(){
        mapPane.getChildren().clear();
    }

    public static void setMapPane(AnchorPane mapPane) {
        GuiManager.mapPane = mapPane;
    }

    public static void setTextLocation(Label textLocation) {
        GuiManager.textLocation = textLocation;
    }
}
