package ie.dylangore.dsa2.ca2.gui;

import ie.dylangore.dsa2.ca2.data.*;
import ie.dylangore.dsa2.ca2.graph.RouteCalculater;
import ie.dylangore.dsa2.ca2.gui.components.LineEasiestRoute;
import ie.dylangore.dsa2.ca2.gui.components.LineSafestRoute;
import ie.dylangore.dsa2.ca2.gui.components.LineShortestRoute;
import ie.dylangore.dsa2.ca2.types.Link;
import ie.dylangore.dsa2.ca2.types.Marker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * JavaFX main controller
 */
public class ControllerMain {

    @FXML private ScrollPane mapContainer;
    @FXML private AnchorPane mapPane;

    @FXML private ChoiceBox<Marker> availablePlaces;
    @FXML private Button btnSetStart, btnSetEnd, btnAddWaypoint, btnRemoveWaypoint, btnAvoid, btnRemoveAvoid, btnClearRoute;
    @FXML private Label lblStart, lblEnd, lblWaypoints, lblAvoid;

    @FXML private TextField addMarkerX, addMarkerY, addMarkerName;
    @FXML private ChoiceBox<String> addMarkerRegion, addMarkerAffiliation;

    @FXML private ChoiceBox<Marker> addLinkPlaces;
    @FXML private ChoiceBox<String> addLinkType, addLinkClimate;
    @FXML private Button btnAddLinkStart, btnAddLinkEnd;
    @FXML private ListView<Link> listLinks;
    @FXML private Label lblLinkStart, lblLinkEnd;
    @FXML private Button btnAddLinkCoords;

    @FXML private VBox showRouteContainer;
    @FXML private CheckBox showRouteShortest, showRouteEasiest, showRouteSafest;

    private boolean linksVisible = false;

    /**
     * Initialise controller and other classes and set initial values
     */
    @FXML
    protected void initialize(){
        ListManager.init();
        RouteManager.init();
        GuiManager.setRealGui(true);
        GuiManager.setMapPane(mapPane);
        GuiManager.setAvailablePlaces(availablePlaces);
        GuiManager.setAddLinkPlaces(addLinkPlaces);
        loadAll();
        updateLists();
        availablePlaces.getSelectionModel().select(0);

        addMarkerRegion.getSelectionModel().select(0);
        addMarkerAffiliation.getSelectionModel().select(1);

        addLinkPlaces.getSelectionModel().select(0);
        addLinkType.getItems().addAll("Land", "Mountain", "Sea");
        addLinkType.getSelectionModel().select(0);

        addLinkClimate.getItems().addAll("Desert", "Mild", "Temperate");
        addLinkClimate.getSelectionModel().select(2);
        mapContainer.setPannable(true);

        showRouteContainer.setVisible(false);
    }

    /**
     * Update combo box contents
     */
    @FXML
    private void updateLists(){
        availablePlaces.setItems(ListManager.getMarkerList());
        addMarkerRegion.setItems(ListManager.getRegionList());
        addMarkerAffiliation.setItems(ListManager.getAffiliationList());
        addLinkPlaces.setItems(ListManager.getMarkerList());
    }

    /**
     * Get mouse X and Y coordinates when map is clicked on
     * @param event mouse event
     */
    @FXML
    public void mapClicked(MouseEvent event){
        addMarkerX.setText(String.valueOf((int)event.getX()));
        addMarkerY.setText(String.valueOf((int)event.getY()));
    }

    /**
     * Add a new marker to the map
     */
    @FXML
    public void addMarker(){
        int newX = -1;
        int newY = -1;
        String newName = "";
        String newRegion = addMarkerRegion.getSelectionModel().getSelectedItem();
        String newAffiliation = addMarkerAffiliation.getSelectionModel().getSelectedItem();
        if(!addMarkerX.getText().equals("") && !addMarkerY.getText().equals("") && !addMarkerName.getText().equals("")){
            newX = Integer.valueOf(addMarkerX.getText());
            newY = Integer.valueOf(addMarkerY.getText());
            newName = addMarkerName.getText();
        }

        if(newRegion != null && newAffiliation != null && !newName.equals("") && newX >=0 && newX <=5000 && newY>=0 && newY <=3334){
            try{
                if(ListManager.getMarkerByName(newName) != null){
                    GuiManager.displayErrorAlert("Marker Already Exists", "A marker with this name already exists!");
                }else if(ListManager.getMarkerByXY(newX, newY) != null){
                    GuiManager.displayErrorAlert("Marker Already Exists", "A marker at the given coordinates already exists!");
                }
                else{
                    GuiManager.addMarkerButton(newName, newX, newY, newAffiliation, newRegion);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            GuiManager.displayErrorAlert("GUI Error", "Unable to add marker, some/all of the data is out of bounds or missing!");
        }


    }

    /**
     * Build a new route
     * @param event action event
     */
    @FXML
    public void modifyRoute(ActionEvent event){
        Marker current = availablePlaces.getSelectionModel().getSelectedItem();
        Button source = (Button) event.getSource();

        if(source == btnAddWaypoint){
            if(!RouteManager.getWaypoints().contains(current) && !RouteManager.getAvoidList().contains(current) && RouteManager.getEnd() != current && RouteManager.getStart() != current){
                RouteManager.getWaypoints().add(current);
            }else{
                GuiManager.displayErrorAlert("Warning!", "This point has already been included in this list!");
            }
        }else if(source == btnRemoveWaypoint){
            if(RouteManager.getWaypoints().contains(current)){
                RouteManager.getWaypoints().remove(current);
            }else{
                GuiManager.displayErrorAlert("Warning!", "This point is not in the waypoint list!");
            }
        }else if(source == btnAvoid){
            if(!RouteManager.getWaypoints().contains(current) && !RouteManager.getAvoidList().contains(current) && RouteManager.getEnd() != current && RouteManager.getStart() != current){
                RouteManager.getAvoidList().add(current);
            }else{
                GuiManager.displayErrorAlert("Warning!", "This point has already been used elsewhere!");
            }
        }else if(source == btnRemoveAvoid){
            if(RouteManager.getAvoidList().contains(current)){
                RouteManager.getAvoidList().remove(current);
            }else{
                GuiManager.displayErrorAlert("Warning!", "This point is not in the avoid list!");
            }
        }else if(source == btnSetStart){
            if(!RouteManager.getWaypoints().contains(current) && !RouteManager.getAvoidList().contains(current) && RouteManager.getEnd() != current){
                RouteManager.setStart(current);
            }else{
                GuiManager.displayErrorAlert("Warning!", "This point has already been used elsewhere!");
            }
        }else if(source == btnSetEnd){
            if(!RouteManager.getWaypoints().contains(current) && !RouteManager.getAvoidList().contains(current) &&RouteManager.getStart() != current){
                RouteManager.setEnd(current);
            }else{
                GuiManager.displayErrorAlert("Warning!", "This point has already been used elsewhere!");
            }
        }else if(source == btnClearRoute){
            RouteManager.setStart(null);
            RouteManager.setEnd(null);
            RouteManager.getWaypoints().clear();
            RouteManager.getAvoidList().clear();
            mapPane.getChildren().removeIf(node -> node instanceof Line);
            showRouteShortest.setSelected(false);
            showRouteEasiest.setSelected(false);
            showRouteSafest.setSelected(false);
            showRouteContainer.setVisible(false);
        }

        // Update labels
        StringBuilder waypoints = new StringBuilder();
        for(int i = 0; i < RouteManager.getWaypoints().size(); i++){
            Marker currWaypoint = RouteManager.getWaypoints().get(i);
            waypoints.append("\n - ");
            waypoints.append(currWaypoint.getName());
        }

        StringBuilder avoiding = new StringBuilder();
        for(int i = 0; i < RouteManager.getAvoidList().size(); i++){
            Marker currWaypoint = RouteManager.getAvoidList().get(i);
            avoiding.append("\n - ");
            avoiding.append(currWaypoint.getName());
        }

        if(!RouteManager.getWaypoints().isEmpty()){
            lblWaypoints.setText("Waypoints:" + waypoints);
        }else{
            lblWaypoints.setText("Waypoints: \n - None");
        }
        if(!RouteManager.getAvoidList().isEmpty()){
            lblAvoid.setText("Avoiding:" + avoiding);
        }else{
            lblAvoid.setText("Avoiding: \n - None");
        }
        if(RouteManager.getStart() != null){
            lblStart.setText("Start Point: " + RouteManager.getStart().getName());
        }else{
            lblStart.setText("Start Point: Not Set");
        }
        if(RouteManager.getEnd() != null){
            lblEnd.setText("End Point: " + RouteManager.getEnd().getName());
        }else{
            lblEnd.setText("End Point: Not Set");
        }

    }

    /**
     * Add a new link between two markers
     * @param event action event
     */
    public void addLink(ActionEvent event){
        Marker selected = addLinkPlaces.getSelectionModel().getSelectedItem();
        ObservableList<Link> localLinks = FXCollections.observableArrayList();
        localLinks.addAll(selected.getLinks());

        if(event.getSource() == btnAddLinkStart){
            if(LinkManager.getStart() != selected && LinkManager.getEnd() != selected){
                lblLinkStart.setText("Start Point: " + selected.getName());
                LinkManager.setStart(selected);
                listLinks.setItems(localLinks);
            }else{
                GuiManager.displayErrorAlert("Marker In Use", "This marker has already been used elsewhere!");
            }
        }else if(event.getSource() == btnAddLinkEnd){
            if(LinkManager.getStart() != selected && LinkManager.getEnd() != selected){
                lblLinkEnd.setText("End Point: " + selected.getName());
                LinkManager.setEnd(selected);
            }else{
                GuiManager.displayErrorAlert("Marker In Use", "This marker has already been used elsewhere!");
            }
        }else{
            if(addLinkType.getSelectionModel().getSelectedItem() != null && addLinkClimate != null){
                LinkManager.setType(addLinkType.getSelectionModel().getSelectedItem().toLowerCase());
                LinkManager.setClimate(addLinkClimate.getSelectionModel().getSelectedItem().toLowerCase());
                if(LinkManager.isValid()){
                    LinkManager.addLink();
                    ListManager.getLinksFromMarkers();
                    localLinks.clear();
                    localLinks.addAll(LinkManager.getStart().getLinks());
                    listLinks.setItems(localLinks);
                }else{
                    GuiManager.displayErrorAlert("GUI Error", "Unable to add link, some/all of the data is out of bounds or missing!");
                }
            }else{
                GuiManager.displayErrorAlert("GUI Error", "Unable to add link, some/all of the data is out of bounds or missing!");
            }
        }
    }

    /**
     * Toggle link visibility on map
     */
    @FXML
    public void toggleLinks(){
        if(linksVisible){
            linksVisible = false;
            mapPane.getChildren().removeIf(node -> node instanceof Line);
        }else{
            linksVisible = true;
            for(Link link: ListManager.getLinkList()){
                int startX = link.getStart().getXCoordinate();
                int startY = link.getStart().getYCoordinate();
                int endX = link.getEnd().getXCoordinate();
                int endY = link.getEnd().getYCoordinate();

                Line line = new Line(startX, startY, endX, endY);
                line.setStrokeWidth(2);
                if(link.getType().equals("land")){
                    line.setStroke(Color.LAWNGREEN);
                }else if(link.getType().equals("sea")){
                    line.setStroke(Color.AQUA);
                }else{
                    line.setStroke(Color.GOLD);
                }
                mapPane.getChildren().add(line);
            }
        }

        System.out.println("Link Visibility: " + linksVisible);
    }

    /**
     * Calculate routes based on values set above
     */
    @FXML
    public void calculateRoute(){
        // Clear any existing route lines from the map
        mapPane.getChildren().removeIf(node -> node instanceof LineShortestRoute);
        mapPane.getChildren().removeIf(node -> node instanceof LineEasiestRoute);
        mapPane.getChildren().removeIf(node -> node instanceof LineSafestRoute);

        List<Marker> waypointQueue = new ArrayList<>(RouteManager.getWaypoints());
        System.out.println("\n" + waypointQueue + "\n");
        Marker startPoint = RouteManager.getStart();

        RouteCalculater.CostedPath shortestRoute;
        RouteCalculater.CostedPath easiestRoute;
        RouteCalculater.CostedPath safestRoute;

        if(waypointQueue.isEmpty()){
            shortestRoute = RouteCalculater.findCheapestPathDijkstra(startPoint, RouteManager.getEnd(), "shortest");
            easiestRoute = RouteCalculater.findCheapestPathDijkstra(startPoint, RouteManager.getEnd(), "easiest");
            safestRoute = RouteCalculater.findCheapestPathDijkstra(startPoint, RouteManager.getEnd(), "safest");
        }else{
            RouteCalculater.CostedPath wpShortestRoute = new RouteCalculater.CostedPath();
            RouteCalculater.CostedPath wpEasiestRoute = new RouteCalculater.CostedPath();
            RouteCalculater.CostedPath wpSafestRoute = new RouteCalculater.CostedPath();
            RouteCalculater.CostedPath shortestRouteAWP;
            RouteCalculater.CostedPath easiestRouteAWP;
            RouteCalculater.CostedPath safestRouteAWP;

            Iterator queueItr = waypointQueue.iterator();
            while(queueItr.hasNext()){
                if(waypointQueue.size() > 0){
                    Marker current = waypointQueue.get(0);
                    System.out.println("Processing Waypoint Route: " + startPoint.getName() + " -> " + current.getName());
                    RouteCalculater.CostedPath tmpShortestRoute = RouteCalculater.findCheapestPathDijkstra(startPoint, current, "shortest");
                    RouteCalculater.CostedPath tmpEasiestRoute = RouteCalculater.findCheapestPathDijkstra(startPoint, current, "easiest");
                    RouteCalculater.CostedPath tmpSafestRoute = RouteCalculater.findCheapestPathDijkstra(startPoint, current, "safest");
                    // Shortest
                    if(tmpShortestRoute != null){
                        wpShortestRoute.getPathList().addAll(tmpShortestRoute.getPathList());
                        wpShortestRoute.setPathCost(tmpShortestRoute.getPathCost());
                    }
                    // Easiest
                    if(tmpEasiestRoute != null){
                        wpEasiestRoute.getPathList().addAll(tmpEasiestRoute.getPathList());
                        wpEasiestRoute.setPathCost(tmpEasiestRoute.getPathCost());
                    }
                    // Safest
                    if(tmpSafestRoute != null){
                        wpSafestRoute.getPathList().addAll(tmpSafestRoute.getPathList());
                        wpSafestRoute.setPathCost(tmpSafestRoute.getPathCost());
                    }
                    startPoint = current;
                    waypointQueue.remove(current);
                }else{
                    break;
                }
            }

            // Shortest
            shortestRouteAWP = RouteCalculater.findCheapestPathDijkstra(startPoint, RouteManager.getEnd(), "shortest");
            if(shortestRouteAWP != null){
                wpShortestRoute.getPathList().addAll(shortestRouteAWP.getPathList());
                wpShortestRoute.setPathCost(wpShortestRoute.getPathCost() + shortestRouteAWP.getPathCost());
            }
            shortestRoute = wpShortestRoute;

            // Easiest
            easiestRouteAWP = RouteCalculater.findCheapestPathDijkstra(startPoint, RouteManager.getEnd(), "easiest");
            if(easiestRouteAWP != null){
                wpEasiestRoute.getPathList().addAll(easiestRouteAWP.getPathList());
                wpEasiestRoute.setPathCost(wpEasiestRoute.getPathCost() + easiestRouteAWP.getPathCost());
            }
            easiestRoute = wpEasiestRoute;

            // Safest
            safestRouteAWP = RouteCalculater.findCheapestPathDijkstra(startPoint, RouteManager.getEnd(), "safest");
            if(safestRouteAWP != null){
                wpSafestRoute.getPathList().addAll(safestRouteAWP.getPathList());
                wpSafestRoute.setPathCost(safestRouteAWP.getPathCost() + safestRouteAWP.getPathCost());
            }
            safestRoute = wpSafestRoute;
        }

        // Pass routes to GuiManager to allow line creation
        GuiManager.setRoutes(shortestRoute, easiestRoute, safestRoute);

        // Show route view controls
        showRouteContainer.setVisible(true);

        // Hide check box if the respective route doesn't exist
        if(shortestRoute != null){
            System.out.println("Shortest Route: " + shortestRoute.getPathCost());
            // Default route is set as selected and drawn on the map
            showRouteShortest.setVisible(true);
            showRouteShortest.setSelected(true);
            GuiManager.drawRoute("shortest");
        }else{
            showRouteShortest.setVisible(false);
            GuiManager.displayErrorAlert("No route found!", "A valid shortest route could not be found");
        }

        if(easiestRoute != null){
            System.out.println("Easiest Route: " + easiestRoute.getPathCost());
            showRouteEasiest.setVisible(true);
        }else{
            GuiManager.displayErrorAlert("No route found!", "A valid easiest route could not be found");
            showRouteEasiest.setVisible(false);
        }

        if(safestRoute != null){
            System.out.println("Safest Route: " + safestRoute.getPathCost());
            showRouteSafest.setVisible(true);
        }else{
            GuiManager.displayErrorAlert("No route found!", "A valid safest route could not be found");
            showRouteSafest.setVisible(false);
        }
    }

    /**
     * Show/Hide routes on map
     * If a routes corresponding checkbox is checked, show that route, if it is unchecked, hide id
     * @param event action event
     */
    @FXML
    public void showRoutes(ActionEvent event){
        if(event.getSource() == showRouteShortest){
            if(showRouteShortest.isSelected()){
                GuiManager.drawRoute("shortest");
            }else{
                mapPane.getChildren().removeIf(node -> node instanceof LineShortestRoute);
            }
        }else if(event.getSource() == showRouteEasiest){
            if(showRouteEasiest.isSelected()){
                GuiManager.drawRoute("easiest");
            }else{
                mapPane.getChildren().removeIf(node -> node instanceof LineEasiestRoute);
            }
        }else{
            if(showRouteSafest.isSelected()){
                GuiManager.drawRoute("safest");
            }else{
                mapPane.getChildren().removeIf(node -> node instanceof LineSafestRoute);
            }
        }
    }


    /**
     * 'Find on Map' feature, will scroll the map so the desired location is in view
     * TODO needs adjusting so location is centered
     */
    @FXML
    public void centerMapOnMarker(ActionEvent event){
        Marker selected;
        // Center map on selected marker when adding links
        if(event.getSource() == btnAddLinkCoords){
            selected = addLinkPlaces.getSelectionModel().getSelectedItem();
        }else{
            selected = availablePlaces.getSelectionModel().getSelectedItem();
        }
        if(selected != null){
            double hVal = 1 * (selected.getXCoordinate() / 5000.00);
            double vVal = 1 * (selected.getYCoordinate() / 3334.00);
            System.out.println(hVal);
            System.out.println(vVal);
            mapContainer.setHvalue(hVal);
            mapContainer.setVvalue(vVal);
        }
    }

    /**
     * Display about dialog window
     */
    @FXML
    private void displayAboutAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Citadel Maps");
        alert.setContentText("Data Structures & Algorithms Assignment 2\nBy Dylan Gore (20081224)\n");
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
    }

    /**
     * Save all lists to files on menu click
     */
    @FXML
    public void saveAll(){
        DataManager.saveAll();
    }

    /**
     * Load all lists from files on menu click
     */
    @FXML
    public void loadAll(){
        GuiManager.clearAllMarkerButtons();
        DataManager.loadAll();
        updateLists();
    }

}
