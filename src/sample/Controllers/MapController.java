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
import sample.Delivery;
import sample.Main;
import sample.MapLogic.Graphic.PointType;

import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Function;

public class MapController implements Initializable {

    @FXML
    private TableView table;
    @FXML
    private BorderPane Map;

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




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pane root = new Pane();
        Map.setCenter(root);
        Main.map.setRoot( root );


        Main.map.addPoint("1",124,85, PointType.Triangle);
        Main.map.addPoint("2",45,55, PointType.Square);
        Main.map.addPoint("3",94,55, PointType.TwoCricle);
        Main.map.draw();
        System.out.println("Добавилось");

        id.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("id"));
        executor.setCellValueFactory(new PropertyValueFactory<Delivery, String>("executor"));
        status.setCellValueFactory(new PropertyValueFactory<Delivery, String>("status"));
        time_start.setCellValueFactory(new PropertyValueFactory<Delivery, String>("time_start"));
        time_end.setCellValueFactory(new PropertyValueFactory<Delivery, String>("time_end"));

        // заполняем таблицу данными
        ObservableList<Delivery> DeliveryData = FXCollections.observableArrayList();
        DeliveryData.add(new Delivery(5,"Иванов","В процессе", "22","22:10"));
        DeliveryData.add(new Delivery(75,"Иванов","В процессе", "22","22:10"));
        DeliveryData.add(new Delivery(25,"Иванов","В процессе", "22","22:10"));
        DeliveryData.add(new Delivery(3,"Ивhgasанов","В процессе", "22","22:10"));
        DeliveryData.add(new Delivery(574747,"Иванов","В процессе", "22","22:10"));
        DeliveryData.add(new Delivery(255,"fag","В процессе", "22","22:10"));
        DeliveryData.add(new Delivery(426,"Иванов","В прdоцессе", "22","22:10"));

        DeliveryData.sort(new Comparator<Delivery>() {
            @Override
            public int compare(Delivery o1, Delivery o2) {
                return o1.getId()-o2.getId();
            }
        });

        HashMap<Integer,Integer> Id_to_Delivery = new HashMap<>();

        Function<HashMap,HashMap> d = (map)->{
               map.clear();
               int index = 0;
               for( var a: DeliveryData){
                   map.put(a.getId(),index++);
               }
               return map;
        };
        Id_to_Delivery = d.apply( Id_to_Delivery );
        System.out.println( DeliveryData.get(Id_to_Delivery.get(426)) );
        table.setItems(DeliveryData);

      //  ObservableList<String> list2= FXCollections.observableArrayList("Тортик","Пироженое");

    }
}
