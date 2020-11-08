package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.MapLogic.Delivery.Delivery;
import sample.MapLogic.Delivery.DeliveryLogic;
import sample.MapLogic.Delivery.DeliveryStatus;
import sample.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DeliveryController implements Initializable {
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




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//ArrayList<String> path
//       PathWrapper pathWrapper= Main.map.find_min_path_with_vert("1","12", new BaseTransport());
//        for(String s:pathWrapper.getPath()){
//            System.out.println(s);
//        }
//        double ans=Main.map.Count_time(pathWrapper);
//        System.out.println("Доставка заняла "+ans+" min");
//
//        Main.map.DrawPath( pathWrapper.getPath() );
//        System.out.println("Добавилось");

        id.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("id"));
        executor.setCellValueFactory(new PropertyValueFactory<Delivery, String>("executor"));
        status.setCellValueFactory(new PropertyValueFactory<Delivery, DeliveryStatus>("status"));
        time_start.setCellValueFactory(new PropertyValueFactory<Delivery, String>("time_start"));
        time_end.setCellValueFactory(new PropertyValueFactory<Delivery, String>("time_end"));
        //goods.setCellValueFactory(new PropertyValueFactory<Delivery, ArrayList<Product>>("goods"));
        // заполняем таблицу данными
        DeliveryLogic dl=new DeliveryLogic();
        dl.DeliveryData=Main.DeliveryData;
        dl.change_status(0,DeliveryStatus.OK);
        table.setItems(dl.DeliveryData);
    }
}
