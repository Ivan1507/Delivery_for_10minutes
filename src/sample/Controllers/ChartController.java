package sample.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ChartController implements Initializable {
    private  XYChart.Series<String,Number> set=new XYChart.Series();
    private XYChart.Series<String,Number> set1=new XYChart.Series();
    private XYChart.Series<String,Number> set2=new XYChart.Series();
    private Map<String,Integer> time_for_histogramm=new TreeMap<>();
    @FXML
    private BarChart<String,Number> chart;



    @FXML
    private DatePicker datePicker;
    @FXML
    private void click() throws IOException, ClassNotFoundException {
        clear();

        chart.setTitle("Статистика заказов за "+datePicker.getValue());
        File  file = new File("./date.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        HashMap<String, Map<String, Integer>> map =(HashMap<String,Map<String,Integer>>) objectInputStream.readObject();
        System.out.println("datePicker = " + datePicker.getValue().toString());
        XYChart.Data<String,Number> data = new XYChart.Data<String,Number>("<5 минут", map.get(datePicker.getValue().toString()).get("<"));
        set2.getData().add(data);

        XYChart.Data<String,Number> data1 = new XYChart.Data<String,Number>(">5 & <10 минут", map.get(datePicker.getValue().toString()).get("="));
        set1.getData().add(data1);


        XYChart.Data<String,Number> data2 = new XYChart.Data<String,Number>(">10 минут", map.get(datePicker.getValue().toString()).get(">"));

        set.getData().add(data2);




        Node nl = chart.lookup(".default-color0.chart-bar");
        Node ns = chart.lookup(".default-color1.chart-bar");
        Node nsl = chart.lookup(".default-color2.chart-bar");
        nl.setStyle("-fx-bar-fill:green");
        ns.setStyle("-fx-bar-fill:orange;");
        nsl.setStyle("-fx-bar-fill:red;");

    }


    @FXML
    private void clear(){
        set.getData().clear();
        set1.getData().clear();
        set2.getData().clear();
        //chart.getData().removeAll(set2,set1,set);
        //chart.getData().clear();
    }

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //chart.setTitle("Статистика заказов");
        addData(4);
        addData(7);
        addData(8);
        addData(11);
        chart.getData().addAll(set2,set1,set);
        chart.setLegendVisible(false);
        set1.setName("От 5 до 10 минут");
        set2.setName("До 5 минут");
        set.setName("от 10 минут");
        //chart.getData().addAll(set,set1,set2);

    }
    //Метод для добавления статистики
    public static void addData(Integer time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formatDateTime = LocalDateTime.now().format(formatter);

        String category = "";
        if (time < 5) category = "<";
        else if (time > 5 && time <= 10) category = "=";
        else if (time > 10) category = ">";

        try {
            File file = new File("./date.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            HashMap<String, Map<String, Integer>> map = (HashMap<String, Map<String, Integer>>) objectInputStream.readObject();
            if(map.get(formatDateTime)==null){
                map.put(formatDateTime,new HashMap<>());
            }
            if (map.get(formatDateTime).get(category) != null) {
                map.get(formatDateTime).put(category, map.get(formatDateTime).get(category) + 1);
            } else {
                map.get(formatDateTime).put(category, 1);
            }


            FileOutputStream fileInputStream2 = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileInputStream2);
            objectOutputStream.writeObject(map);
            objectOutputStream.flush();


            //System.out.println("Map = " + map);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            try {


                HashMap<String, Map<String, Integer>> map = new HashMap<>();
                if (map.get(formatDateTime) == null) map.put(formatDateTime, new HashMap<>());

                if (map.get(formatDateTime).get(category) != null) {
                    map.get(formatDateTime).put(category, map.get(formatDateTime).get(category) + 1);
                } else {
                    map.get(formatDateTime).put(category, 1);
                }


                File file = new File("./date.txt");
                FileOutputStream fileInputStream = new FileOutputStream(file);
                ObjectOutputStream objectInputStream = new ObjectOutputStream(fileInputStream);
                objectInputStream.writeObject(map);
                objectInputStream.flush();

            } catch (IOException ee) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}