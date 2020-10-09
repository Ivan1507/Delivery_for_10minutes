package sample;
import javafx.event.ActionEvent;
import javafx.event.Event;
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




