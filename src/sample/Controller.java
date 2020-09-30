package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private BorderPane borderPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void OnClicked(Event d) throws IOException {
        System.out.println("123");
        Parent root = FXMLLoader.load(getClass().getResource("map.fxml"));
        borderPane.setCenter( root );

    }
}
