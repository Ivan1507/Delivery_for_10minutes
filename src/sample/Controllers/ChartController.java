package sample.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import sample.Main;
import sample.Delivery.Delivery;
import sample.Delivery.DeliveryStatus;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ChartController implements Initializable {
    private  XYChart.Series set=new XYChart.Series();
    private XYChart.Series set1=new XYChart.Series();
    private XYChart.Series set2=new XYChart.Series();
    private HashMap<String,Integer> time_for_histogramm=new HashMap<>();
    @FXML
    private BarChart<?, ?> chart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       chart.setTitle("Статистика заказов");
        addData(4);
       addData(5);
       addData(10);
       addData(11);
       addData(5);
        addData(7);
        System.out.println("time_for_histogramm = " + time_for_histogramm);

       set1.getData().add(new XYChart.Data("Jan", 23));
       set2.getData().add(new XYChart.Data("Feb", 14));
       chart.getData().addAll(set,set1,set2);

    }
    //Метод для добавления статистики
    public void addData(Integer time) {
        if (time <= 5) {
            if (time_for_histogramm.get(time.toString()) != null) {
                time_for_histogramm.put(time.toString(), 1 + time_for_histogramm.get(time.toString()));
            } else time_for_histogramm.put(time.toString(), 1);

            set2.getData().clear();

            for (Map.Entry<String, Integer> entry : time_for_histogramm.entrySet()) {
                XYChart.Data data = new XYChart.Data(entry.getKey(), entry.getValue());
                set2.getData().add(data);
            }

        }
        else if (time > 5 && time<=10) {
            if (time_for_histogramm.get(time.toString()) != null) {
                time_for_histogramm.put(time.toString(), 1 + time_for_histogramm.get(time.toString()));
            } else time_for_histogramm.put(time.toString(), 1);

            set1.getData().clear();

            for (Map.Entry<String, Integer> entry : time_for_histogramm.entrySet()) {
                XYChart.Data data = new XYChart.Data(entry.getKey(), entry.getValue());
                set1.getData().add(data);
            }

        }
        else if (time > 10) {
            if (time_for_histogramm.get(time.toString()) != null) {
                time_for_histogramm.put(time.toString(), 1 + time_for_histogramm.get(time.toString()));
            } else time_for_histogramm.put(time.toString(), 1);

            set.getData().clear();

            for (Map.Entry<String, Integer> entry : time_for_histogramm.entrySet()) {
                XYChart.Data data = new XYChart.Data(entry.getKey(), entry.getValue());
                set.getData().add(data);
            }

        }
    }
}