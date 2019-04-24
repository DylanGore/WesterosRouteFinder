package ie.dylangore.dsa2.ca2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            HBox hb =  FXMLLoader.load(getClass().getResource("/gui/main.fxml"));
            Scene scene = new Scene(hb);
            scene.getStylesheets().add(getClass().getResource("/gui/main.css").toExternalForm());
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/gui/icon.png")));
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Westeros Route Planner");
            System.out.println("Loading...");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
