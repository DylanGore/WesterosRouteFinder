package ie.dylangore.dsa2.ca2.gui;

import ie.dylangore.dsa2.ca2.data.ListManager;
import ie.dylangore.dsa2.ca2.types.Marker;
import ie.dylangore.dsa2.ca2.types.Region;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import javax.swing.text.html.ImageView;

public class ControllerMain {

    @FXML private Label lblCoords;
    @FXML private AnchorPane mapPane;

    @FXML private ChoiceBox planRouteStart, planRouteEnd;
    @FXML private ListView<Marker> planRouteWaypoints;

    @FXML private TextField addMarkerX, addMarkerY, addMarkerName;
    @FXML private ChoiceBox addMarkerRegion, addMarkerAffiliation;
    @FXML private Button btnAddMarker;

    @FXML
    protected void initialize(){
        ListManager.init();
        loadAll();

        addMarkerRegion.setItems(ListManager.getRegionList());
        addMarkerAffiliation.setItems(ListManager.getAffiliationList());
        planRouteWaypoints.setItems(ListManager.getMarkerList());

    }

    public void updateLists(){
        planRouteStart.setItems(ListManager.getMarkerList());
        planRouteEnd.setItems(ListManager.getMarkerList());
        planRouteWaypoints.setItems(ListManager.getMarkerList());
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

        new Marker(newX, newY, newName, newAffiliation, newRegion);

        // Create a new button on the map corresponding to the new marker
        int btnSize = 20;

    @FXML
    public void saveAll(){
        DataManager.saveAll();
    }
        markerButton.setMinSize(btnSize, btnSize);
        markerButton.setMaxSize(btnSize, btnSize);
        markerButton.setPrefSize(btnSize, btnSize);
        markerButton.setLayoutY(newY - btnSize / 2);
        markerButton.setLayoutX(newX - btnSize / 2);
        mapPane.getChildren().add(markerButton);

    @FXML
    public void loadAll(){
        GuiManager.clearAllMarkerButtons();
        DataManager.loadAll();
        updateLists();
    }

}
