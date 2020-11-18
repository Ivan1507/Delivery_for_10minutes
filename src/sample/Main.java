package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Delivery.*;
import sample.MapLogic.Graph;
import sample.Graphic.PointType;
import sample.MapLogic.Quality_Road;
import sample.MapLogic.Vertex;
import sample.Transport.BaseTransport;
import sample.Transport.Quadrocopter;

import java.util.*;

public class Main extends Application {

    public static Graph map = new Graph();
    public static Kitchen kitchen=new Kitchen();
    public static DeliveryLogic deliveryLogic=new DeliveryLogic();
    static {
        deliveryLogic.DeliveryData=FXCollections.observableArrayList();


        BaseTransport Car = new BaseTransport(166+80,157+7);
        Car.setName("Машина 2");
        Car.setPointType(PointType.Circle);
        deliveryLogic.getDepartment().getVehicles().add( Car );


//        BaseTransport Car3 = new Quadrocopter(206+80,157+25);
//
//
//        Car3.setPointType(PointType.Circle);
//        Car3.setName("Машина 3");
//        deliveryLogic.getDepartment().getVehicles().add( Car3 );


    }

    public static ObservableList<Delivery> DeliveryData;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Frames/MainScene.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Delivery for 10 minutes");


        // Создаем точки на карте
        map.AddPoint("1",400,200, PointType.Triangle);
        map.AddPoint("13",200,200, PointType.TwoCricle);
        map.AddPoint("2",500,240, PointType.TwoCricle);
        map.AddPoint("3",460,300, PointType.Circle);
        map.AddPoint("4",350,230, PointType.TwoCricle);
        map.AddPoint("5",320,300, PointType.TwoCricle);
        map.AddPoint("6",280,125, PointType.TwoCricle);

        map.AddPoint("7",350,100, PointType.TwoCricle);
        map.AddPoint("8",470,80, PointType.TwoCricle);
        map.AddPoint("8.5", (470+720) / 2,45, PointType.TwoCricle);
        map.AddPoint("9",720,90, PointType.TwoCricle);
        map.AddPoint("10",545,140, PointType.TwoCricle);
        map.AddPoint("11",625,210, PointType.TwoCricle);
        map.AddPoint("12",760,150, PointType.TwoCricle);

        map.AddProduction("Production",760,250, PointType.TwoCricle);



        HashMap<Integer,Double> traffic = new HashMap<>();
        for( int i =1; i<=24;i++ ){
            double traffic_value = switch(i) {
                case 1, 3, 2, 4, 5, 6, 12, 11 -> 0.05;
                case 7, 9 -> 0.10;
                case 8, 17 -> 0.15;
                case 10 -> 0.07;
                case 13, 14 -> 0.08;
                case 15, 16, 20 -> 0.09;
                case 18 -> 0.18;
                case 19 -> 0.20;
                default -> 0.04;
            };

            traffic.put(i,traffic_value);
        }


        map.Connect("1","3",Quality_Road.average,traffic);
        map.Connect("1","2",Quality_Road.Abstract_bad,traffic);
        map.Connect("2","3",Quality_Road.average,traffic);
        map.Connect("13","6",Quality_Road.average,traffic);
        map.Connect("5","13",Quality_Road.good,traffic);
        map.Connect("3","4",Quality_Road.average,traffic);
        map.Connect("4","5",Quality_Road.good,traffic);
        map.Connect("8.5","8",Quality_Road.very_bad,traffic);
        map.Connect("8.5","9",Quality_Road.bad,traffic);
        map.Connect("8.5","10",Quality_Road.very_bad,traffic);
        map.Connect("5","6",Quality_Road.average,traffic);
        map.Connect("6","7",Quality_Road.average,traffic);
        map.Connect("1","7",Quality_Road.average,traffic);
        map.Connect("1","8",Quality_Road.average,traffic);
        map.Connect("8","9",Quality_Road.good,traffic);
        map.Connect("9","10",Quality_Road.average,traffic);
        map.Connect("2","11",Quality_Road.average,traffic);
        map.Connect("11","12",Quality_Road.average,traffic);
        map.Connect("9","12",Quality_Road.average,traffic);
        map.Connect("10","11",Quality_Road.average,traffic);
        map.Connect("1","4",Quality_Road.average,traffic);

        map.Connect("Production","12",Quality_Road.average,traffic);
        map.Connect("Production","11",Quality_Road.average,traffic);


        HashSet<Vertex> current_ver=new HashSet<>();
        for (int i = 0; i<5; i++){
            Delivery dev=DeliveryGenerator.generate();
          if(!current_ver.contains(dev.getAddress())) {
              current_ver.add(dev.getAddress());
              deliveryLogic.add_delivery(dev);
          }
      }


        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        TimerTask task = new TimerTask() {
            public void run() {
                Main.deliveryLogic.remove_by_key(0);
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 4000L;
        timer.schedule(task, delay);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
