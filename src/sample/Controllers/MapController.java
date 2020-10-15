package sample.Controllers;

import javafx.beans.Observable;
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
import sample.MapLogic.Graphic.PointType;
import sample.Transport.BaseTransport;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
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

    @FXML
    private TableColumn goods;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pane root = new Pane();
        Map.setCenter(root);
        Main.map.setRoot( root );
        Main.map.DrawGraph();
        Main.map.draw();

        ArrayList<String> path= Main.map.find_min_path("1","10", new BaseTransport());
        for(String s:path){
            System.out.println(s);
        }


            Main.map.DrawPath( path );




        System.out.println("Добавилось");

        id.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("id"));
        executor.setCellValueFactory(new PropertyValueFactory<Delivery, String>("executor"));
        status.setCellValueFactory(new PropertyValueFactory<Delivery, String>("status"));
        time_start.setCellValueFactory(new PropertyValueFactory<Delivery, String>("time_start"));
        time_end.setCellValueFactory(new PropertyValueFactory<Delivery, String>("time_end"));
        goods.setCellValueFactory(new PropertyValueFactory<Delivery, String>("goods"));
        // заполняем таблицу данными
        ObservableList<Delivery> DeliveryData = FXCollections.observableArrayList();
        DeliveryData.add(new Delivery(5,"Иванов","В процессе", "22","22:10","Картошка\n123"));


        DeliverySerializer serializer2 = new DeliverySerializer(DeliveryData);
        try {
            serializer2.SaveObject("Active");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObservableList<Delivery> DeliveryData1;
        DeliverySerializer serializer = new DeliverySerializer();
        try {
        DeliveryData =  serializer.LoadObject("Active");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ;

        DeliveryData.sort(new Comparator<Delivery>() {
            @Override
            public int compare(Delivery o1, Delivery o2) {
                return o1.getId()-o2.getId();
            }
        });

//        HashMap<Integer,Integer> Id_to_Delivery = new HashMap<>();
//
//        ObservableList<Delivery> finalDeliveryData = DeliveryData;
//        Function<HashMap,HashMap> d = (map)->{
//               map.clear();
//               int index = 0;
//               for( var a: finalDeliveryData){
//                   map.put(a.getId(),index++);
//               }
//               return map;
//        };
//        Id_to_Delivery = d.apply( Id_to_Delivery );
//        System.out.println( DeliveryData.get(Id_to_Delivery.get(426)) );
        table.setItems(DeliveryData);


      //  ObservableList<String> list2= FXCollections.observableArrayList("Тортик","Пироженое");

    }
}
