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
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Pane pane12;
    // @FXML
    private Button SafeButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private Graph mapGraph;
    {

    }
    //{
    //Graph bMapgraph = new Graph(borderPaneMap);

    // }
    private ButtonUserData GlobalUserData = new ButtonUserData();

    @FXML
    public void OnClicked(Event E) throws IOException {
        // Инициализация фрейма
        System.out.println("123");
        Parent root = FXMLLoader.load(getClass().getResource("map.fxml"));
        borderPane.setCenter(root);
        //mapGraph.setRoot( pane12 );

        System.out.println(pane12);



    }

    @FXML
    public void buttonClicked(ActionEvent E) {
        GlobalUserData.setType(btnTypes.valueOf((String) ((Button) E.getSource()).getUserData()));

        clearStylesheet();
        SafeButton = ((Button) E.getSource());
        SafeButton.setStyle("-fx-background-color: #1c8494");


    }

    @FXML
    public void clearData(MouseEvent E) {
        if (SafeButton != null && E.getButton()== MouseButton.SECONDARY) {
            GlobalUserData.clear();
            clearStylesheet();

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

        mapGraph = Graph.getInstance(pane12);

        mapGraph.addPoint("undefined",E.getX(),E.getY());
        System.out.println(mapGraph);
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