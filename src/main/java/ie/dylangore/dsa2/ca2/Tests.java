package ie.dylangore.dsa2.ca2;

import ie.dylangore.dsa2.ca2.data.DataManager;
import ie.dylangore.dsa2.ca2.data.GuiManager;
import ie.dylangore.dsa2.ca2.data.ListManager;
import ie.dylangore.dsa2.ca2.data.RouteManager;
import ie.dylangore.dsa2.ca2.graph.RouteCalculater;
import ie.dylangore.dsa2.ca2.types.Link;
import ie.dylangore.dsa2.ca2.types.Marker;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Objects;

public class Tests extends ApplicationTest {
    /**
     * Setup dummy Java FX application to allow testing of Java FX components
     *
     * @param stage primary stage
     */
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new VBox());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Setup tests
     */
    @BeforeEach
    void setUp() {
        ListManager.init();
        RouteManager.init();
        DataManager.loadAll();
        AnchorPane testPane = new AnchorPane();
        testPane.setPrefSize(5000, 3334);
        GuiManager.setRealGui(false);
        GuiManager.setMapPane(testPane);
    }

    /**
     * Check that lists have loaded from their respective files correctly
     */
    @Test
    void persistence(){
        Assertions.assertNotNull(ListManager.getMarkerList());
        Assertions.assertTrue(ListManager.getMarkerList().contains(ListManager.getMarkerByName("Winterfell")));
        Assertions.assertNotNull(ListManager.getLinkList());
        Assertions.assertNotNull(ListManager.getRegionList());
        Assertions.assertNotNull(ListManager.getAffiliationList());
    }

    /**
     * Add test Marker and Link and ensure they are added to the required lists
     */
    @Test
    void addData(){
        Marker testMarker = new Marker(50, 50, "Test", "self", "Across the Narrow Sea");
        Link testLink = new Link(testMarker, ListManager.getMarkerList().get(0), "land", "temperate");

        Assertions.assertTrue(ListManager.getMarkerList().contains(testMarker));
        Assertions.assertTrue(testMarker.getLinks().contains(testLink));
    }

    /**
     * Calculate three routes (shortest, easiest, safest) between known good points and ensure that they are valid
     */
    @Test
    void routeCalculationStandard(){
        RouteCalculater.CostedPath route1 = RouteCalculater.findCheapestPathDijkstra(Objects.requireNonNull(ListManager.getMarkerByName("Winterfell")), ListManager.getMarkerByName("King's Landing"), "shortest");
        RouteCalculater.CostedPath route2 = RouteCalculater.findCheapestPathDijkstra(Objects.requireNonNull(ListManager.getMarkerByName("King's Landing")), ListManager.getMarkerByName("Castle Black"), "easiest");
        RouteCalculater.CostedPath route3 = RouteCalculater.findCheapestPathDijkstra(Objects.requireNonNull(ListManager.getMarkerByName("Casterly Rock")), ListManager.getMarkerByName("Dragonstone"), "safest");

        Assertions.assertNotNull(route1);
        Assertions.assertNotNull(route2);
        Assertions.assertNotNull(route3);
    }

    /**
     * Calculate a route given a marker to avoid and ensure that it is actually avoided
     */
    @Test
    void routeCalculationAvoid(){
        Marker pyke = ListManager.getMarkerByName("Pyke");
        Marker twins = ListManager.getMarkerByName("The Twins");

        RouteManager.getAvoidList().add(twins);
        RouteCalculater.CostedPath route4 = RouteCalculater.findCheapestPathDijkstra(Objects.requireNonNull(ListManager.getMarkerByName("Winterfell")), ListManager.getMarkerByName("King's Landing"), "shortest");

        Assertions.assertNotNull(route4);
        Assertions.assertTrue(route4.getPathList().contains(pyke));
        Assertions.assertFalse(route4.getPathList().contains(twins));
    }

}
