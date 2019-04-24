package ie.dylangore.dsa2.ca2.gui;

import ie.dylangore.dsa2.ca2.data.DataManager;
import ie.dylangore.dsa2.ca2.data.GuiManager;
import ie.dylangore.dsa2.ca2.data.ListManager;
import ie.dylangore.dsa2.ca2.data.RouteManager;
import ie.dylangore.dsa2.ca2.types.Marker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ControllerMain {

    @FXML private ScrollPane mapContainer;
    @FXML private AnchorPane mapPane;

    @FXML private ChoiceBox<Marker> availablePlaces;
    @FXML private Button btnSetStart, btnSetEnd, btnAddWaypoint, btnRemoveWaypoint, btnClearRoute;
    @FXML private Label lblStart, lblEnd, lblWaypoints;

    @FXML private TextField addMarkerX, addMarkerY, addMarkerName;
    @FXML private ChoiceBox<String> addMarkerRegion, addMarkerAffiliation;
    @FXML private Button btnAddMarker;

    @FXML
    protected void initialize(){
        ListManager.init();
        RouteManager.init();
        GuiManager.setMapPane(mapPane);
        GuiManager.setAvailablePlaces(availablePlaces);
        loadAll();
        updateLists();
    }

    @FXML
    private void updateLists(){
        availablePlaces.setItems(ListManager.getMarkerList());
        addMarkerRegion.setItems(ListManager.getRegionList());
        addMarkerAffiliation.setItems(ListManager.getAffiliationList());
    }

    @FXML
    public void mapClicked(MouseEvent event){
        int mouseX = (int)event.getX();
        int mouseY = (int)event.getY();

        System.out.println("Mouse X: " + mouseX);
        System.out.println("Mouse Y: " + mouseY);

//        lblCoords.setText("Co-Ordinates: [" + mouseX + ", " + mouseY + "]");

        addMarkerX.setText(String.valueOf(mouseX));
        addMarkerY.setText(String.valueOf(mouseY));
    }

    @FXML
    public void addMarker(ActionEvent event){
        int newX = Integer.valueOf(addMarkerX.getText());
        int newY = Integer.valueOf(addMarkerY.getText());
        String newName = addMarkerName.getText();
        String newRegion = addMarkerRegion.getSelectionModel().getSelectedItem().toString();
        String newAffiliation = addMarkerAffiliation.getSelectionModel().getSelectedItem().toString();

        GuiManager.addMarkerButton(newName, newX, newY, newAffiliation, newRegion);
    }

    @FXML
    public void modifyRoute(ActionEvent event){
        Marker current = (Marker) availablePlaces.getSelectionModel().getSelectedItem();
        Button source = (Button) event.getSource();

        System.out.println("Current Marker: " + current.toString());
        System.out.println("Source: " + source.getText());

        if(current != null){
            if(source == btnAddWaypoint){
                if(!RouteManager.getWaypoints().contains(current)){
                    RouteManager.getWaypoints().add(current);
                }else{
                    System.out.println("WARNING: This waypoint is already included!");
                }
            }else if(source == btnRemoveWaypoint){
                if(RouteManager.getWaypoints().contains(current)){
                    RouteManager.getWaypoints().remove(current);
                }else{
                    System.out.println("WARNING: This waypoint is not in the list!");
                }
            }else if(source == btnSetStart){
                if(!RouteManager.getWaypoints().contains(current) && RouteManager.getEnd() != current){
                    RouteManager.setStart(current);
                }else{
                    System.out.println("WARNING: This point has already been used!");
                }
            }else if(source == btnSetEnd){
                if(!RouteManager.getWaypoints().contains(current) && RouteManager.getStart() != current){
                    RouteManager.setEnd(current);
                }else{
                    System.out.println("WARNING: This point has already been used!");
                }
            }else if(source == btnClearRoute){
                RouteManager.setStart(null);
                RouteManager.setEnd(null);
                RouteManager.getWaypoints().clear();
            }

            // Update labels
            StringBuilder waypoints = new StringBuilder();
            for(int i = 0; i < RouteManager.getWaypoints().size(); i++){
                Marker currWaypoint = RouteManager.getWaypoints().get(i);
                waypoints.append("\n - ");
                waypoints.append(currWaypoint.getName());
            }

            if(!RouteManager.getWaypoints().isEmpty()){
                lblWaypoints.setText("Waypoints:" + waypoints);
            }else{
                lblWaypoints.setText("Waypoints: \n - None");
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
    }


    /**
     * 'Find on Map' feature, will scroll the map so the desired location is in view
     * TODO needs adjusting so location is centered
     */
    @FXML
    public void centerMapOnMarker(){
        Marker selected = availablePlaces.getSelectionModel().getSelectedItem();
        double hVal = 1 * (selected.getXCoordinate() / 5000.00);
        double vVal = 1 * (selected.getYCoordinate() / 3334.00);

        System.out.println(hVal);
        System.out.println(vVal);

        mapContainer.setHvalue(hVal);
        mapContainer.setVvalue(vVal);
    }

    @FXML
    public void saveAll(){
        DataManager.saveAll();
    }

    @FXML
    public void loadAll(){
        GuiManager.clearAllMarkerButtons();
        DataManager.loadAll();
        updateLists();
    }

}
