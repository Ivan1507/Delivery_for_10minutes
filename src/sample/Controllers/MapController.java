package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sample.DeliveryEdgeInfo;
import sample.MapLogic.Delivery.Delivery;
import sample.Main;
import sample.MapLogic.Delivery.DeliveryStatus;
import sample.MapLogic.Graphic.PointType;
import sample.MapLogic.PathWrapper;
import sample.MapLogic.Vertex;
import sample.Product;
import sample.Transport.BaseTransport;

import java.net.URL;
import java.util.*;

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
        goods.setCellValueFactory(new PropertyValueFactory<Delivery, ArrayList<Product>>("goods"));
        // заполняем таблицу данными

        table.setItems(Main.DeliveryData);

        Vertex A1 = new Vertex(125,66);
        A1.setName("Заказ");
        A1.setPointType(PointType.Triangle);
        A1.placeTo(root);

        Vertex A2 = new Vertex(225,96);
        A2.setName("Заказ 2");
        A2.setPointType(PointType.Triangle);
        A2.placeTo(root);



        Vertex A3 = new Vertex(325,86);
        A3.setName("Заказ 3");
        A3.setPointType(PointType.Triangle);
        A3.placeTo(root);
        A3.setSpecial(true);

        BaseTransport Car = new BaseTransport(470+40,80+3);
        Car.setName("Машина");
        Car.setPointType(PointType.Circle);
        Car.placeTo(root);



        DeliveryEdgeInfo deliveryEdgeInfo   =  Main.map.parseAllEdges(A1,false);
        DeliveryEdgeInfo deliveryEdgeInfo2 = Main.map.parseAllEdges(A2,false);
        //DeliveryEdgeInfo deliveryEdgeInfo4 = Main.map.parseAllEdges(A3,false);
        DeliveryEdgeInfo deliveryEdgeInfo3 = Main.map.parseAllEdges(Car,true);
       PathWrapper path=Main.map.findPath(Car,A3);

       path.getPath().forEach(System.out::println);
        double time= Main.map.Count_time( path );
        System.out.println("Доставка займет " + time + " мин ");
        Main.map.DrawPath(path.getPath());
       // System.out.println(deliveryEdgeInfo2.getAdjacentVertexes());
       // deliveryEdgeInfo.print();

      //  deliveryEdgeInfo2.print();
      //  deliveryEdgeInfo3.print();

       // Points.put(name,A1);
      //  ObservableList<String> list2= FXCollections.observableArrayList("Тортик","Пироженое");

    }
}
