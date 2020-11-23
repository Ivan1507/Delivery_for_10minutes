package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sample.LocalDateFormatted2;
import sample.Delivery.Delivery;
import sample.Main;
import sample.Delivery.DeliveryStatus;
import sample.Graphic.PointType;
import sample.Product;
import sample.Transport.BaseTransport;
import sample.Transport.Quadrocopter;

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
        Main.map.setRoot(root);
        Main.map.DrawGraph();
        Main.map.Draw();

        id.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("id"));
        executor.setCellValueFactory(new PropertyValueFactory<Delivery, String>("executor"));
        status.setCellValueFactory(new PropertyValueFactory<Delivery, DeliveryStatus>("status"));
        time_start.setCellValueFactory(new PropertyValueFactory<Delivery, LocalDateFormatted2>("time_start"));
        time_end.setCellValueFactory(new PropertyValueFactory<Delivery, LocalDateFormatted2>("time_end"));
        goods.setCellValueFactory(new PropertyValueFactory<Delivery, ArrayList<Product>>("goods"));
        // заполняем таблицу данными

        table.setItems(Main.deliveryLogic.getDeliveryData());
        for (BaseTransport vehicle : Main.deliveryLogic.getDepartment().getVehicles()) {
            vehicle.placeTo(root);
        }

        for (Delivery delivery : Main.deliveryLogic.getDeliveryData()) {
            delivery.getAddress().placeTo(root);
        }


        BaseTransport t = new BaseTransport(470 + 60, 25 + 25);
        t.setName("Квадрокотер");
        t.setPointType(PointType.Circle);
        t.placeTo(root);


        for (Delivery e : Main.deliveryLogic.getDeliveryData()) {
            System.out.println("e = " + e);
            if (!e.getAddress().getName().equals("Заказ 47")) continue;
            try {
                System.out.println("Start");
                t = (Main.deliveryLogic.getBestExecutor(e));
                System.out.println("t = " + t);
                System.out.println(t + " берет заказ " + e);
                Main.map.DrawPath(t.getPathToDelivery(e).getPath());
                System.out.println("End");
            } catch (NullPointerException | CloneNotSupportedException tt) {
                tt.printStackTrace();
            }
        }
    }
}
