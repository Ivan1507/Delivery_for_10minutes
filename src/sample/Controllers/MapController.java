package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sample.MapLogic.Delivery.Delivery;
import sample.Main;
import sample.MapLogic.Delivery.DeliverySerializer;
import sample.MapLogic.PathWrapper;
import sample.MapLogic.Vertex;
import sample.Transport.BaseTransport;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.ResourceBundle;
// FXML контроллер для сцены map.fxml
public class MapController implements Initializable {

    @FXML
    private TableView table;
    @FXML
    private BorderPane Map1;

    @FXML
    private TableColumn id;

    @FXML
    private TableColumn executor;

    @FXML
    private TableColumn status;

    @FXML
    private TableColumn time_start;

    @FXML
    private TableColumn time_end;

    @FXML
    private TableColumn goods;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pane root = new Pane();
        Map1.setCenter(root);
        Main.map.setRoot( root );
        Main.map.DrawGraph();
        Main.map.draw();
//ArrayList<String> path
       PathWrapper pathWrapper= Main.map.find_min_path("1","12", new BaseTransport());
        for(String s:pathWrapper.getPath()){
            System.out.println(s);
        }
        double ans=Main.map.Count_time(pathWrapper);
        System.out.println("Доставка заняла "+ans+" min");

        Main.map.DrawPath( pathWrapper.getPath() );
        System.out.println("Добавилось");

        id.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("id"));
        executor.setCellValueFactory(new PropertyValueFactory<Delivery, String>("executor"));
        status.setCellValueFactory(new PropertyValueFactory<Delivery, String>("status"));
        time_start.setCellValueFactory(new PropertyValueFactory<Delivery, String>("time_start"));
        time_end.setCellValueFactory(new PropertyValueFactory<Delivery, String>("time_end"));
        goods.setCellValueFactory(new PropertyValueFactory<Delivery, String>("goods"));
        // заполняем таблицу данными

        table.setItems(Main.DeliveryData);


      //  ObservableList<String> list2= FXCollections.observableArrayList("Тортик","Пироженое");

    }
}
