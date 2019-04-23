package ie.dylangore.dsa2.ca2.gui;

import ie.dylangore.dsa2.ca2.data.DataManager;
import ie.dylangore.dsa2.ca2.data.GuiManager;
import ie.dylangore.dsa2.ca2.data.ListManager;
import ie.dylangore.dsa2.ca2.types.Marker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ControllerMain {

    @FXML private Label textLocation;
    @FXML private AnchorPane mapPane;

    @FXML private ChoiceBox planRouteStart, planRouteEnd;
    @FXML private ListView<Marker> planRouteAvailableWaypoints, planRouteIncludedWaypoints;

    @FXML private TextField addMarkerX, addMarkerY, addMarkerName;
    @FXML private ChoiceBox addMarkerRegion, addMarkerAffiliation;
    @FXML private Button btnAddMarker;

    @FXML
    protected void initialize(){
        ListManager.init();
        GuiManager.setMapPane(mapPane);
        GuiManager.setTextLocation(textLocation);
        loadAll();

        addMarkerRegion.setItems(ListManager.getRegionList());
        addMarkerAffiliation.setItems(ListManager.getAffiliationList());
        planRouteAvailableWaypoints.setItems(ListManager.getMarkerList());

    }

    @FXML
    public void updateLists(){
        planRouteStart.setItems(ListManager.getMarkerList());
        planRouteEnd.setItems(ListManager.getMarkerList());
        addMarkerRegion.setItems(ListManager.getRegionList());
        addMarkerAffiliation.setItems(ListManager.getAffiliationList());
        planRouteAvailableWaypoints.setItems(ListManager.getMarkerList());
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
    public void addWaypoint(){
        Marker selected = planRouteAvailableWaypoints.getSelectionModel().getSelectedItem();
        planRouteAvailableWaypoints.getItems().remove(selected);
        planRouteIncludedWaypoints.getItems().add(selected);
    }

    @FXML
    public void removeWaypoint(){
        Marker selected = planRouteAvailableWaypoints.getSelectionModel().getSelectedItem();
        planRouteIncludedWaypoints.getItems().remove(selected);
        planRouteAvailableWaypoints.getItems().add(selected);
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
