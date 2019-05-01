package ie.dylangore.dsa2.ca2.gui.components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Custom implementation of JavaFX Line to allow for custom properties
 * and easier group removal from mapPane
 */
public class LineEasiestRoute extends Line {
    /**
     * Constructor
     */
    public LineEasiestRoute(){
        super.setStartX(0);
        super.setStartY(0);
        super.setEndX(0);
        super.setEndY(0);

        super.setStroke(Color.RED);
        super.setStrokeWidth(3);
    }
}
