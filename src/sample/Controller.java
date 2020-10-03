package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private BorderPane borderPaneMap;
    // @FXML
    private Button SafeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private Graph borderPaneMapGraph = new Graph(borderPaneMap);
    //{
    //Graph bMapgraph = new Graph(borderPaneMap);

    // }
    private ButtonUserData GlobalUserData = new ButtonUserData();

    @FXML
    public void OnClicked(Event E) throws IOException {
        System.out.println("123");
        Parent root = FXMLLoader.load(getClass().getResource("map.fxml"));
        borderPane.setCenter(root);
       // if(SafeButton!=null){
         //   SafeButton.setStyle("-fx-background-color: #999");
        //}


    }

    @FXML
    public void buttonClicked(ActionEvent E) {
        GlobalUserData.setType(btnTypes.valueOf((String) ((Button) E.getSource()).getUserData()));

        clearStylesheet();
        SafeButton = ((Button) E.getSource());
        SafeButton.setStyle("-fx-background-color: #1c8494");


           // SafeButton = ((Button) E.getSource());


        //Point.setUserData(new ButtonUserData(btnTypes.valueOf("Production")));
        //  System.out.println("Успешно!");
        // System.out.println(((ButtonUserData)Point.getUserData()).getType());
    }

    @FXML
    public void clearData(MouseEvent E) {
        if (SafeButton != null && E.getButton()== MouseButton.SECONDARY) {
            GlobalUserData.clear();
            clearStylesheet();
           // return;
        }

    }

    public void insertObject(MouseEvent E) {
        if (SafeButton != null && E.getButton()== MouseButton.SECONDARY) {
            GlobalUserData.clear();
            clearStylesheet();
            return;
        }
        System.out.println("Был вызван " + GlobalUserData.getType());
        GlobalUserData.clear();


    }

    @FXML
    public void clearStylesheet(){
        if (SafeButton != null) {
            SafeButton.setStyle("");
        }
    }
}

class ButtonUserData{
    private btnTypes type;
    public ButtonUserData(  btnTypes btnTypes){
        type = btnTypes;
    }
    public ButtonUserData( ){
    }
    public btnTypes getType() {
        return type;
    }
    public void setType(btnTypes type){
        this.type=type;
    }
    public void clear(){
        type = null;
        System.gc();
    }
}

enum btnTypes {
    Point("Point"),Production("Production") ;

    String Type;
    btnTypes(String type){
        Type=type;
    }
    btnTypes(){

    }
    public String getType() {
        return Type;
    }

    @Override
    public String toString() {
        return Type;
    }
}