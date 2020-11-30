package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.DatePicker;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ChartController implements Initializable {
    private Integer a=0;
    private Integer b=0;
    private Integer c=0;
    private  XYChart.Series set=new XYChart.Series();
    private XYChart.Series set1=new XYChart.Series();
    private XYChart.Series set2=new XYChart.Series();
    static public Map<String,Integer> time_for_histogramm=new TreeMap<>();
    static public Map<String, HashMap<String,String>> barchart=new TreeMap<>();
    @FXML
    private BarChart<?, ?> chart;
    @FXML
    private DatePicker datePicker;
    @FXML
    private void click(){

        chart.setTitle("Статистика заказов за "+datePicker.getValue());


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
        chart.setStyle(".default-color0.chart-series-line { -fx-stroke: #e9967a; }"+
                ".default-color1.chart-series-line { -fx-stroke: #f0e68c; }"+
                ".default-color2.chart-series-line { -fx-stroke: #dda0dd; }");
        System.out.println("time_for_histogramm = " + time_for_histogramm);

        chart.getData().addAll(set,set1,set2);
        for(Node n:chart.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: red;");
        }
        for(Node n:chart.lookupAll(".default-color1.chart-bar")) {
            n.setStyle("-fx-bar-fill: orange;");
        }
        for(Node n:chart.lookupAll(".default-color2.chart-bar")) {
            n.setStyle("-fx-bar-fill: green;");
        }
    }
    static public void saveData(long time) throws IOException {
        HashMap<String,String> nm=new HashMap<>();
        if(time<5) nm.put("0","1");
        else if(time>5 && time<=10) nm.put("1","1");
        else if(time>10) nm.put("2","1");
        if(barchart.get(String.valueOf(LocalDate.now())).get(nm)==null)
        barchart.put(String.valueOf(LocalDate.now()),nm);
        else barchart.put(String.valueOf(LocalDate.now()),


    }
    //Метод для добавления статистики
    public void addData(Integer time) {

            if (time_for_histogramm.get(time.toString()) != null) {
                time_for_histogramm.put(time.toString(), 1 + time_for_histogramm.get(time.toString()));
            } else time_for_histogramm.put(time.toString(), 1);
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
