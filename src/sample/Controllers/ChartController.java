package sample.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import sample.Main;
import sample.Delivery.Delivery;
import sample.Delivery.DeliveryStatus;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private DatePicker datePicker1;
    @FXML
    private void click() throws IOException, ClassNotFoundException {
        boolean f=false;
        boolean del=false;
        clear();
        File  file = new File("./date.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        HashMap<String, Map<String, Integer>> map =(HashMap<String,Map<String,Integer>>) objectInputStream.readObject();
        boolean go=false;
        int ld = 0,ly=0,lm=0,rd = 0,ry = 0,rm=0;

        if(datePicker.getValue()!=null&&datePicker1.getValue()!=null) {
            go=true;
            ld = datePicker.getValue().getDayOfMonth();
            ly = datePicker.getValue().getYear();
            lm = datePicker.getValue().getMonthValue();

            rd = datePicker1.getValue().getDayOfMonth();
            ry = datePicker1.getValue().getYear();
            rm = datePicker1.getValue().getMonthValue();
            if (ly > ry) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Инфорация от администратора");
                alert.setHeaderText("Введены некорректные данные");
                alert.show();
                chart.setTitle("Введены некорректные данные");
                f = true;
            } else if (lm > rm&&ry==ly) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Инфорация от администратора");
                alert.setHeaderText("Введены некорректные данные");
                alert.show();
                chart.setTitle("Введены некорректные данные");
                f = true;
            } else if (ld > rd && lm==rm) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Инфорация от администратора");
                alert.setHeaderText("Введены некорректные данные");
                alert.show();
                chart.setTitle("Введены некорректные данные");
                f = true;
            }
        }
        if(!f&&go) {
            chart.setTitle("Статистика заказов с "+datePicker.getValue()+" по "+datePicker1.getValue());
            //Получить инфу о доставках
                Integer a=0;
                Integer b=0;
                Integer c=0;
                if (map.get(datePicker.getValue().toString()) != null) {
                    del=true;
                    if (map.get(datePicker.getValue().toString()).get("<") != null) {
                        a = map.get(datePicker.getValue().toString()).get("<");
                    }
                    if (map.get(datePicker.getValue().toString()).get("=") != null) {
                        b = map.get(datePicker.getValue().toString()).get("=");
                    }
                    if (map.get(datePicker.getValue().toString()).get(">") != null) {
                        c = map.get(datePicker.getValue().toString()).get(">");
                    }
                }
            System.out.println("values" + a + "/" + b + "/" + c);

                ZoneId defaultZoneId = ZoneId.systemDefault();
                Calendar cal = Calendar.getInstance();
                cal.setTime(Date.from(datePicker.getValue().atStartOfDay(defaultZoneId).toInstant()));
                while(ld<rd||lm<rm||ly<ry) {
                    cal.add(Calendar.DATE,1);
                    LocalDate next = LocalDate.ofInstant(cal.toInstant(), defaultZoneId);
                    System.out.println("next = " + next);
                    ld=next.getDayOfMonth();
                    lm=next.getMonthValue();
                    ly=next.getYear();
                    System.out.println("ld = " + ld);
                    System.out.println("lm = " + lm);
                    System.out.println("ly = " + ly);
                    if(map.get(next.toString())!=null) {
                        del=true;
                        if (map.get(next.toString()).get("<") != null)
                            a += map.get(next.toString()).get("<");
                        if (map.get(next.toString()).get("=") != null)
                            b += map.get(next.toString()).get("=");
                        if (map.get(next.toString()).get(">") != null)
                            c += map.get(next.toString()).get(">");
                    }
                    System.out.println("values" + a+"/"+b+"/"+c);
                }
                if(del){
                
                XYChart.Data data = new XYChart.Data("до 5", a);
                if (a != null)
                    set2.getData().add(data);
                
                XYChart.Data data1 = new XYChart.Data("от 5 до 10 мин", b);
                if (b != null)
                    set1.getData().add(data1);
                XYChart.Data data2 = new XYChart.Data("от 10 мин", c);
                if (c != null)
                    set.getData().add(data2);

                if (a != null) {
                    chart.getData().add(set2);
                    Node nl = chart.lookup(".default-color0.chart-bar");
                    nl.setStyle("-fx-bar-fill:green");
                }
                if (b != null) {
                    chart.getData().add(set1);
                    Node ns = chart.lookup(".default-color1.chart-bar");
                    ns.setStyle("-fx-bar-fill:orange;");
                }
                if (c != null) {
                    chart.getData().add(set);
                    Node nsl = chart.lookup(".default-color2.chart-bar");
                    nsl.setStyle("-fx-bar-fill:red;");
                }

                //Получить рекомендации
                if (a != null && b != null && c != null) {
                    if (c >= (a + b) * 0.4) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Инфорация от администратора");
                        alert.setHeaderText("Необходимо добавить количество транспортных связей!");
                        alert.show();
                    } else if (a >= (b + c) * 0.4) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Инфорация от администратора");
                        alert.setHeaderText("Можно уменшить количество транспортных связей!");
                        alert.show();
                    } else if (b >= (c + a) * 0.4) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Инфорация от администратора");
                        alert.setHeaderText("Ваша Доставка нормально справляется с заказами!");
                        alert.show();
                    }
                } else if (a == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Инфорация от администратора");
                    alert.setHeaderText("Необходимо добавить количество транспортных связей!");
                    alert.show();
                } else if (c == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Инфорация от администратора");
                    alert.setHeaderText("Можно уменшить количество транспортных связей!");
                    alert.show();
                }


            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Информация от администратора");
                alert.setHeaderText("За этот период не было доставок!");
                alert.show();
            }
        }
    }



    @FXML
    private void clear(){
        set.getData().clear();
        set1.getData().clear();
        set2.getData().clear();
        chart.getData().removeAll(set2,set1,set);
        chart.getData().clear();
    }

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chart.setLegendVisible(false);
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


            System.out.println("Map = " + map);

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
