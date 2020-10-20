package sample.Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sample.MapLogic.Graph;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private BorderPane borderPane;
    // @FXML
    @FXML
    private ComboBox pizzas;
    @FXML
    private ComboBox sugar;
    @FXML
    private ComboBox drinks;
    @FXML
    private AnchorPane anch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list= FXCollections.observableArrayList("Маргарита","Мясная","Мексиканская");
        if (pizzas!=null)
        pizzas.setItems(list);
        ObservableList<String> list1= FXCollections.observableArrayList("Pepsi","Cola");
        if (drinks!=null)
        drinks.setItems(list1);
        ObservableList<String> list2= FXCollections.observableArrayList("Тортик","Пироженое");
        if(sugar!=null)
        sugar.setItems(list2);
    }
    @FXML
    public void bt_click(){
        anch.setVisible(true);
    }


    @FXML
    public void OnClicked(Event E) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/Frames/map.fxml"));
        borderPane.setCenter(root);

    }
    @FXML
    public void button_stat_clicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Frames/table_stat.fxml"));
        borderPane.setCenter(root);
    }
    @FXML
    public void button_delivery_clicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Frames/delivery.fxml"));
        borderPane.setCenter(root);
    }


    @FXML
    public void clearData(MouseEvent E) {

    }

    public void insertObject(MouseEvent E) {
    }

}




