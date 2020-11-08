package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.MapLogic.Delivery.*;
import sample.MapLogic.Graph;
import sample.MapLogic.Graphic.PointType;
import sample.MapLogic.Quality_Road;
import sample.MapLogic.Vertex;

import java.util.*;

public class Main extends Application {

    public static Graph map = new Graph();
   public static DeliveryLogic deliveryLogic=new DeliveryLogic();
    public static ObservableList<Delivery> DeliveryData;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Frames/MainScene.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Delivery for 10 minutes");


        map.addPoint("1",400,200, PointType.Triangle);
        map.addPoint("13",200,200, PointType.TwoCricle);
        map.addPoint("2",500,240, PointType.TwoCricle);
        map.addPoint("3",460,300, PointType.TwoCricle);
        map.addPoint("4",350,230, PointType.TwoCricle);
        map.addPoint("5",320,300, PointType.TwoCricle);
        map.addPoint("6",280,125, PointType.TwoCricle);
        //map.addPoint("2",45,55, PointType.Square);
        map.addPoint("7",350,100, PointType.TwoCricle);
        map.addPoint("8",470,80, PointType.TwoCricle);
        map.addPoint("8.5",(470+720) / 2,45, PointType.TwoCricle);
        map.addPoint("9",720,90, PointType.TwoCricle);
        map.addPoint("10",545,140, PointType.TwoCricle);
        map.addPoint("11",625,210, PointType.TwoCricle);
        map.addPoint("12",760,150, PointType.TwoCricle);
        Quality_Road q = Quality_Road.good;
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


        map.FillGraph("1","3",Quality_Road.average,traffic);
        map.FillGraph("1","2",Quality_Road.Abstract_bad,traffic);
        map.FillGraph("2","3",Quality_Road.average,traffic);
        map.FillGraph("13","6",Quality_Road.average,traffic);
        map.FillGraph("5","13",Quality_Road.good,traffic);
        map.FillGraph("3","4",Quality_Road.average,traffic);
        map.FillGraph("4","5",Quality_Road.good,traffic);
        map.FillGraph("8.5","8",Quality_Road.very_bad,traffic);
        map.FillGraph("8.5","9",Quality_Road.bad,traffic);
        map.FillGraph("8.5","10",Quality_Road.very_bad,traffic);
        map.FillGraph("5","6",Quality_Road.average,traffic);
        map.FillGraph("6","7",Quality_Road.average,traffic);
        map.FillGraph("1","7",Quality_Road.average,traffic);
        map.FillGraph("1","8",Quality_Road.average,traffic);
        map.FillGraph("8","9",Quality_Road.good,traffic);
        map.FillGraph("9","10",Quality_Road.average,traffic);
        map.FillGraph("2","11",Quality_Road.average,traffic);
        map.FillGraph("11","12",Quality_Road.average,traffic);
        map.FillGraph("9","12",Quality_Road.average,traffic);
        map.FillGraph("10","11",Quality_Road.average,traffic);
        map.FillGraph("1","4",Quality_Road.average,traffic);

        System.out.println(map.quality_road);
        for(Map.Entry<Vertex, HashSet<Vertex>> x:map.graph.entrySet()){

        };



        deliveryLogic.DeliveryData=FXCollections.observableArrayList();

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

    }


    public static void main(String[] args) {
        launch(args);
    }
}
