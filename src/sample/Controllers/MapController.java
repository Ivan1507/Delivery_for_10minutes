package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.MapLogic.Graphic.PointType;

import java.net.URL;
import java.util.ResourceBundle;

public class MapController implements Initializable {

    @FXML
    private BorderPane Map;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pane root = new Pane();
        Map.setCenter(root);
        Main.map.setRoot( root );


        Main.map.addPoint("1",124,85, PointType.Triangle);
        Main.map.addPoint("2",45,55, PointType.Square);
        Main.map.addPoint("3",94,55, PointType.TwoCricle);
        Main.map.draw();
    }
}
