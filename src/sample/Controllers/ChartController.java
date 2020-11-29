package sample.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
import java.util.TreeMap;

public class ChartController implements Initializable {
    private  XYChart.Series set=new XYChart.Series();
    private XYChart.Series set1=new XYChart.Series();
    private XYChart.Series set2=new XYChart.Series();
    private Map<String,Integer> time_for_histogramm=new TreeMap<>();
    @FXML
    private BarChart<?, ?> chart;
    @FXML
    private DatePicker datePicker;
    @FXML
    private void click(){
        chart.setTitle("Статистика заказов за "+datePicker.getValue());
        chart.getData().addAll(set,set1,set2);
    }

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       //chart.setTitle("Статистика заказов");
        addData(4);
       addData(5);
       addData(10);
       addData(8);
       addData(11);
        System.out.println("time_for_histogramm = " + time_for_histogramm);

       //chart.getData().addAll(set,set1,set2);

    }
    //Метод для добавления статистики
    public void addData(Integer time) {

            if (time_for_histogramm.get(time.toString()) != null) {
                time_for_histogramm.put(time.toString(), 1 + time_for_histogramm.get(time.toString()));
            } else time_for_histogramm.put(time.toString(), 1);
            int a=0,b=0,c=0;
            for (Map.Entry<String, Integer> entry : time_for_histogramm.entrySet()) {
                //XYChart.Data data = new XYChart.Data(entry.getKey(), entry.getValue());
                if(Integer.parseInt(entry.getKey())<=5) {
                    a++;
                    XYChart.Data data = new XYChart.Data("до 5 мин", a);
                    set2.getData().add(data);
                }
                else if(Integer.parseInt(entry.getKey())>5 && Integer.parseInt(entry.getKey())<=10) {
                    b++;
                    XYChart.Data data = new XYChart.Data("от 5 до 10 мин", b);
                    set1.getData().add(data);
                }
                else if(Integer.parseInt(entry.getKey())>10) {
                    c++;
                    XYChart.Data data = new XYChart.Data("от 10 мин", c);
                    set.getData().add(data);
                }
            }

        }
    }
