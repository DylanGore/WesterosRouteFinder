package ie.dylangore.dsa2.ca2.data;

import ie.dylangore.dsa2.ca2.graph.RouteCalculater;
import ie.dylangore.dsa2.ca2.gui.components.LineEasiestRoute;
import ie.dylangore.dsa2.ca2.gui.components.LineSafestRoute;
import ie.dylangore.dsa2.ca2.gui.components.LineShortestRoute;
import ie.dylangore.dsa2.ca2.types.Marker;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.StageStyle;


/**
 * Class to handle additional GUI functionality that cannot be done in the controller
 */
public class GuiManager {

    private static AnchorPane mapPane;
    private static Marker lastClickedMarker;
    private static ChoiceBox<Marker> availablePlaces, addLinkPlaces;
    private static RouteCalculater.CostedPath shortestRoute, easiestRoute, safestRoute;
    private static boolean realGui;

    /**
     * Create a new button on the map corresponding to the new marker
     * @param name marker name
     * @param x x coordinate
     * @param y y coordinate
     * @param affiliation marker affiliation
     * @param region marker region
     */
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
            setLastClickedMarker(ListManager.getMarkerByName(name));
            Marker selected = getLastClickedMarker();
            availablePlaces.getSelectionModel().select(selected);
            addLinkPlaces.getSelectionModel().select(selected);
        });
        if(realGui){
            mapPane.getChildren().add(markerButton);
        }
    }

    /**
     * Add CSS class to marker depending on affiliation
     * @param aff marker affiliation
     * @return CSS class name
     */
    private static String getAffiliationClass(String aff){
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

    /**
     * Remove all markers from the screen
     */
    public static void clearAllMarkerButtons(){
        mapPane.getChildren().removeIf(node -> node instanceof Button);
    }

    /**
     * Draw routes between markers on the map
     * @param type type of route (shortest, easiest, safest)
     */
    public static void drawRoute(String type){
        if(type.equals("shortest")){
            if(shortestRoute != null){
                for(int i = 0; i < shortestRoute.getPathList().size(); i++){
                    if((i + 1) <= shortestRoute.getPathList().size() - 1){
                        Marker startPoint = shortestRoute.getPathList().get(i);
                        Marker endPoint = shortestRoute.getPathList().get(i+1);

                        Line line = new LineShortestRoute();
                        line.setStartX(startPoint.getXCoordinate());
                        line.setStartY(startPoint.getYCoordinate());
                        line.setEndX(endPoint.getXCoordinate());
                        line.setEndY(endPoint.getYCoordinate());
                        mapPane.getChildren().add(line);
                    }
                }
            }
        } else if (type.equals("easiest")) {
            if(easiestRoute != null){
                for(int i = 0; i < easiestRoute.getPathList().size(); i++){
                    if((i + 1) <= easiestRoute.getPathList().size() - 1){
                        Marker startPoint = easiestRoute.getPathList().get(i);
                        Marker endPoint = easiestRoute.getPathList().get(i+1);

                        Line line = new LineEasiestRoute();
                        line.setStartX(startPoint.getXCoordinate());
                        line.setStartY(startPoint.getYCoordinate());
                        line.setEndX(endPoint.getXCoordinate());
                        line.setEndY(endPoint.getYCoordinate());
                        mapPane.getChildren().add(line);
                    }
                }
            }
        } else{
            if(safestRoute != null){
                for(int i = 0; i < safestRoute.getPathList().size(); i++){
                    if((i + 1) <= safestRoute.getPathList().size() - 1){
                        Marker startPoint = safestRoute.getPathList().get(i);
                        Marker endPoint = safestRoute.getPathList().get(i+1);

                        Line line = new LineSafestRoute();
                        line.setStartX(startPoint.getXCoordinate());
                        line.setStartY(startPoint.getYCoordinate());
                        line.setEndX(endPoint.getXCoordinate());
                        line.setEndY(endPoint.getYCoordinate());
                        mapPane.getChildren().add(line);
                    }
                }
            }
        }


    }

    /**
     * Used to get the map pane from the controller for use in this class
     * @param mapPane map pane
     */
    public static void setMapPane(AnchorPane mapPane) {
        GuiManager.mapPane = mapPane;
    }

    /**
     * Return the last marker to be clicked on by the user
     * @return last clicked marker
     */
    private static Marker getLastClickedMarker() {
        return lastClickedMarker;
    }

    /**
     * Set the last clicked marker variable
     * @param lastClickedMarker marker to set as
     */
    private static void setLastClickedMarker(Marker lastClickedMarker) {
        GuiManager.lastClickedMarker = lastClickedMarker;
    }

    /**
     * Used to get the availablePlaces combo box from the controller
     * @param availablePlaces ComboBox
     */
    public static void setAvailablePlaces(ChoiceBox<Marker> availablePlaces) {
        GuiManager.availablePlaces = availablePlaces;
    }

    /**
     * Used to get the addLinkPlaces combo box from the controller
     * @param addLinkPlaces ComboBox
     */
    public static void setAddLinkPlaces(ChoiceBox<Marker> addLinkPlaces) {
        GuiManager.addLinkPlaces = addLinkPlaces;
    }

    /**
     * Used to get routes from the controller and store them in local variables
     * @param shortest shortest route
     * @param easiest easiest route
     * @param safest safest route
     */
    public static void setRoutes(RouteCalculater.CostedPath shortest, RouteCalculater.CostedPath easiest, RouteCalculater.CostedPath safest){
        shortestRoute = shortest;
        easiestRoute = easiest;
        safestRoute = safest;
    }

    /**
     * Display an alert box for errors
     */
    public static void displayErrorAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
        System.out.println(header + ": " + content);
    }

    /**
     * Used to set value for real GUI to differentiate between Application and Test runs
     * @param value desired value
     */
    public static void setRealGui(Boolean value){
        realGui = value;
    }
}
