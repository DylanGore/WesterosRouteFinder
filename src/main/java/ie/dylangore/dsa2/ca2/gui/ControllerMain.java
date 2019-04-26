package ie.dylangore.dsa2.ca2.gui;

import ie.dylangore.dsa2.ca2.data.DataManager;
import ie.dylangore.dsa2.ca2.data.GuiManager;
import ie.dylangore.dsa2.ca2.data.ListManager;
import ie.dylangore.dsa2.ca2.data.RouteManager;
import ie.dylangore.dsa2.ca2.types.Link;
import ie.dylangore.dsa2.ca2.types.Marker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class ControllerMain {

    @FXML private ScrollPane mapContainer;
    @FXML private AnchorPane mapPane;

    @FXML private ChoiceBox<Marker> availablePlaces;
    @FXML private Button btnSetStart, btnSetEnd, btnAddWaypoint, btnRemoveWaypoint, btnAvoid, btnRemoveAvoid, btnClearRoute, btnCalculateRoute;
    @FXML private Label lblStart, lblEnd, lblWaypoints,  lblAvoid;

    @FXML private TextField addMarkerX, addMarkerY, addMarkerName;
    @FXML private ChoiceBox<String> addMarkerRegion, addMarkerAffiliation;
    @FXML private Button btnAddMarker;

    @FXML private ChoiceBox<Marker> addLinkPlaces;
    @FXML private Button btnAddLinkStart, btnAddLinkEnd, btnAddLink;
    @FXML private ListView<Link> listLinks;
    @FXML private Label lblLinkStart, lblLinkEnd, lblLinkInfo;

    @FXML
    protected void initialize(){
        ListManager.init();
        RouteManager.init();
        GuiManager.setMapPane(mapPane);
        GuiManager.setAvailablePlaces(availablePlaces);
        GuiManager.setBtnAddMarker(btnAddMarker);
        loadAll();
        updateLists();
        availablePlaces.getSelectionModel().select(0);
    }

    @FXML
    private void updateLists(){
        availablePlaces.setItems(ListManager.getMarkerList());
        addMarkerRegion.setItems(ListManager.getRegionList());
        addMarkerAffiliation.setItems(ListManager.getAffiliationList());
        addLinkPlaces.setItems(ListManager.getMarkerList());
    }

    @FXML
    public void mapClicked(MouseEvent event){
        addMarkerX.setText(String.valueOf((int)event.getX()));
        addMarkerY.setText(String.valueOf((int)event.getY()));
    }

    @FXML
    public void addMarker(ActionEvent event){
        int newX = Integer.valueOf(addMarkerX.getText());
        int newY = Integer.valueOf(addMarkerY.getText());
        String newName = addMarkerName.getText();
        String newRegion = addMarkerRegion.getSelectionModel().getSelectedItem();
        String newAffiliation = addMarkerAffiliation.getSelectionModel().getSelectedItem();

        // TODO better error checking
        if(newRegion != null && newAffiliation != null && !newName.equals("") && newX >=0 && newX <=5000 && newY>=0 && newY <=3334){
            try{
                if(ListManager.getMarkerByName(newName) != null){
                    throw new Exception("A marker with this name already exists!");
                }else if(ListManager.getMarkerByXY(newX, newY) != null){
                    throw new Exception("A marker at the given coordinates already exists!");
                }
                else{
                    GuiManager.addMarkerButton(newName, newX, newY, newAffiliation, newRegion);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("ERROR: Unable to add marker, some/all of the data is out of bounds or missing!");
        }


    }

    @FXML
    public void modifyRoute(ActionEvent event){
        Marker current = (Marker) availablePlaces.getSelectionModel().getSelectedItem();
        Button source = (Button) event.getSource();

        System.out.println("Current Marker: " + current.toString());
        System.out.println("Source: " + source.getText());

        if(current != null){
            if(source == btnAddWaypoint){
                if(!RouteManager.getWaypoints().contains(current) && !RouteManager.getAvoidList().contains(current) && RouteManager.getEnd() != current && RouteManager.getStart() != current){
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
            }else if(source == btnAvoid){
                if(!RouteManager.getWaypoints().contains(current) && !RouteManager.getAvoidList().contains(current) && RouteManager.getEnd() != current && RouteManager.getStart() != current){
                    RouteManager.getAvoidList().add(current);
                }else{
                    System.out.println("WARNING: This waypoint is already used!");
                }
            }else if(source == btnRemoveAvoid){
                if(RouteManager.getAvoidList().contains(current)){
                    RouteManager.getAvoidList().remove(current);
                }else{
                    System.out.println("WARNING: This waypoint is not in the avoid list!");
                }
            }else if(source == btnSetStart){
                if(!RouteManager.getWaypoints().contains(current) && !RouteManager.getAvoidList().contains(current) && RouteManager.getEnd() != current){
                    RouteManager.setStart(current);
                }else{
                    System.out.println("WARNING: This point has already been used!");
                }
            }else if(source == btnSetEnd){
                if(!RouteManager.getWaypoints().contains(current) && !RouteManager.getAvoidList().contains(current) &&RouteManager.getStart() != current){
                    RouteManager.setEnd(current);
                }else{
                    System.out.println("WARNING: This point has already been used!");
                }
            }else if(source == btnClearRoute){
                RouteManager.setStart(null);
                RouteManager.setEnd(null);
                RouteManager.getWaypoints().clear();
                RouteManager.getAvoidList().clear();
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
    }

    public void addLink(ActionEvent event){
        StringBuilder linkInfo = new StringBuilder();
        Marker selected = addLinkPlaces.getSelectionModel().getSelectedItem();

        if(selected != null){
            if(event.getSource() == btnAddLinkStart){
                lblLinkStart.setText("Start Point: " + selected.getName());
                ObservableList<Link> localLinks = FXCollections.observableArrayList();
                for(Link l: ListManager.getLinkList()){
                    if(l.getStart() == selected){
                        localLinks.add(l);
                    }
                }
                listLinks.setItems(localLinks);
            }else if(event.getSource() == btnAddLinkEnd){
                lblLinkEnd.setText("End Point: " + selected.getName());
            }else{
                System.out.println("Add link");
            }
        }
    }

    @FXML
    public void calculateRoute(){
        System.out.println("Calculate Link");
    }


    /**
     * 'Find on Map' feature, will scroll the map so the desired location is in view
     * TODO needs adjusting so location is centered
     */
    @FXML
    public void centerMapOnMarker(){
        Marker selected = availablePlaces.getSelectionModel().getSelectedItem();
        if(selected != null){
            double hVal = 1 * (selected.getXCoordinate() / 5000.00);
            double vVal = 1 * (selected.getYCoordinate() / 3334.00);
            System.out.println(hVal);
            System.out.println(vVal);
            mapContainer.setHvalue(hVal);
            mapContainer.setVvalue(vVal);
        }
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
