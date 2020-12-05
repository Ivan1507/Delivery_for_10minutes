package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import sample.LocalDateFormatted2;
import sample.Delivery.Delivery;
import sample.Main;
import sample.Delivery.DeliveryStatus;
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

    public static Timer timer = new Timer("TimerRefreshMap");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pane root = new Pane();
        Map1.setCenter(root);
        Main.map.setRoot( root );
        Main.map.DrawGraph();
        Main.map.Draw();

        id.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("id"));
        executor.setCellValueFactory(new PropertyValueFactory<Delivery, BaseTransport>("executor"));
        status.setCellValueFactory(new PropertyValueFactory<Delivery, DeliveryStatus>("status"));
        time_start.setCellValueFactory(new PropertyValueFactory<Delivery, LocalDateFormatted2>("time_start"));
        time_end.setCellValueFactory(new PropertyValueFactory<Delivery, LocalDateFormatted2>("time_end"));
        goods.setCellValueFactory(new PropertyValueFactory<Delivery, ArrayList<Product>>("goods"));
        // заполняем таблицу данными
        status.setCellFactory(tableColumn -> {
            return new TableCell<Delivery,DeliveryStatus>(){
                @Override
                protected void updateItem(DeliveryStatus deliveryStatus, boolean b) {
                    super.updateItem(deliveryStatus, b);
                    if (deliveryStatus == null) return;
                    String text = switch (deliveryStatus){
                        case OK -> "Готов";
                        case NOT_OK -> "Не готов";
                        case WAITING -> "Ожидает";
                        case DELAYED -> "С задержкой";
                        default -> "unknown";
                    };
                    setText(text);
                    if (text.equals("Готов")) {
                        //setText(deliveryStatus.toString());
                        setTextFill(Color.GREEN); //The text in red
                        //("-fx-background-color: #5bf55b"); //The background of the cell in yellow
                    } else {
                            //setText(deliveryStatus.toString());
                            //setTextFill(Color.BLACK);

                    }
                }
            };
        });

//        executor.setCellFactory(tableColumn -> {
//            return new TableCell<Delivery,BaseTransport>(){
//                @Override
//                protected void updateItem(BaseTransport baseTransport, boolean b) {
//                    super.updateItem(baseTransport, b);
//                   // if (baseTransport == null) return;
//
//
//                    if (baseTransport == null) {
//                        //setText("=====");
//                        setTextFill(Color.PURPLE); //The text in red
//                        //("-fx-background-color: #5bf55b"); //The background of the cell in yellow
//                    } else {
//                        //setText(deliveryStatus.toString());
//                        //setTextFill(Color.BLACK);
//                        setTextFill(Color.GREEN);
//                        setText(baseTransport.toString());
//                    }
//                }
//            };
//        });

        table.setItems(Main.deliveryLogic.getDeliveryData());
        for (BaseTransport vehicle : Main.deliveryLogic.getDepartment().getVehicles()) {
            vehicle.placeTo(root);
        }

        for (Delivery delivery : Main.deliveryLogic.getDeliveryData()) {
            if (delivery.getAddress().isFinished()) continue;
            delivery.getAddress().placeTo(root);
        }


        UpdateLc();
    }

    public void UpdateLc(){
        TimerTask task = new TimerTask() {
            public void run() {
                table.refresh();
                UpdateLc();
            }
        };


        long delay = 100L;
        timer.schedule(task, delay);
    }
}
