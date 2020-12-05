package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import sample.Main;
import sample.Delivery.Delivery;
import sample.Delivery.DeliveryStatus;
import sample.Transport.BaseTransport;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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

    public static Timer timer = new Timer("TimerRefresh");



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("id"));
        executor.setCellValueFactory(new PropertyValueFactory<Delivery, BaseTransport>("executor"));
        status.setCellValueFactory(new PropertyValueFactory<Delivery, DeliveryStatus>("status"));
        time_start.setCellValueFactory(new PropertyValueFactory<Delivery, String>("time_start"));
        time_end.setCellValueFactory(new PropertyValueFactory<Delivery, String>("time_end"));
        //goods.setCellValueFactory(new PropertyValueFactory<Delivery, ArrayList<Product>>("goods"));
        // заполняем таблицу данными

        table.setItems(Main.deliveryLogic.getDeliveryData());

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
