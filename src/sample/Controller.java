package sample;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    @FXML
    private ComboBox pizzas;
    @FXML
    private ComboBox sugar;
    @FXML
    private ComboBox drinks;
    @FXML
    private AnchorPane anch;
    @FXML
    private Button bt;
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
    private Graph mapGraph;
    {

    }
    //{
    //Graph bMapgraph = new Graph(borderPaneMap);

    // }
    private ButtonUserData ActionUserData = new ButtonUserData();

    @FXML
    public void OnClicked(Event E) throws IOException {
        // Инициализация фрейма
        //System.out.println("123");
        Parent root = FXMLLoader.load(getClass().getResource("map.fxml"));
        borderPane.setCenter(root);
        //mapGraph.setRoot( pane12 );
        System.out.println(pane12);

    }
    @FXML
    public void button_stat_clicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("table_stat.fxml"));
        borderPane.setCenter(root);
    }
    @FXML
    public void button_delivery_clicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("delivery.fxml"));
        borderPane.setCenter(root);
    }
    @FXML
    public void button_Point_Clicked(ActionEvent E) {
        try {
            ActionType Action = ActionType.valueOf((String) ((Button) E.getSource()).getUserData());

            ActionUserData.setType(Action);

            clearStylesheet();
            SafeButton = ((Button) E.getSource());
            SafeButton.setStyle("-fx-background-color: #1c8494");
        } catch (IllegalArgumentException s){
            System.out.println("Не существует такого типа ActionType");
            ActionUserData.setType(ActionType.NULL);
        }

    }

    @FXML
    public void clearData(MouseEvent E) {
        if (SafeButton != null && E.getButton()== MouseButton.SECONDARY) {
            ActionUserData.clear();
            clearStylesheet();

        }

    }

    public void insertObject(MouseEvent E) {
        if (SafeButton == null ){
            return;
        }

        System.out.println("Был вызван " + ActionUserData.getType());
        ActionUserData.clear();
        clearStylesheet();

        mapGraph = Graph.getInstance(pane12);
        mapGraph.setRoot(pane12);
        mapGraph.addPoint("undefined",E.getX(),E.getY());
        SafeButton = null;
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
    private ActionType type;
    public ButtonUserData(  ActionType actionType){
        type = actionType;
    }
    public ButtonUserData( ){
    }
    public ActionType getType() {
        return type;
    }
    public void setType(ActionType type){
        this.type=type;
    }
    public void clear(){
        type = null;
        System.gc();
    }
}

enum PointType {
    Point("Point"),Production("Production") ;

    String Type;
    PointType(String type){
        Type=type;
    }
    PointType(){

    }
    public String getType() {
        return Type;
    }

    @Override
    public String toString() {
        return Type;
    }
}

enum ActionType{
    Point("Point"),Road("Road"), Empty("Empty"),NULL("null") ;

    String Type;
    ActionType(String type){
        Type=type;
    }
    ActionType(){

    }
    public String getType() {
        return Type;
    }

    @Override
    public String toString() {
        return Type;
    }
}