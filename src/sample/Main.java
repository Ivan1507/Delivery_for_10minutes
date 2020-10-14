package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import sample.MapLogic.Graph;
import sample.MapLogic.Graphic.PointType;
import sample.MapLogic.Vertex;

import java.util.HashSet;
import java.util.Map;

public class Main extends Application {

    public static Graph map = new Graph();
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Frames/MainScene.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
       //BorderPane root = new BorderPane();
       // Vertex v=new Vertex(1,2);
       // v.setName("MIET");
        //v.SaveToFile("src\\save.txt");
        //Vertex v1=new Vertex();
       // v1.getFromFile("src\\save.txt");
        //System.out.println(v1);
      // Graph Map = new Graph(root);

        //Map.addPoint("A",45,40);
       // Map.addPoint("B",80,40);
       // Map.connectPoint("A","B",Quality_Road.Very_bad);

        //Map.LoadObject("");

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
        map.addPoint("9",720,90, PointType.TwoCricle);
        map.addPoint("10",545,140, PointType.TwoCricle);
        map.addPoint("11",625,210, PointType.TwoCricle);
        map.addPoint("12",760,150, PointType.TwoCricle);
        map.FillGraph("1","3");
        map.FillGraph("1","2");
        map.FillGraph("2","3");
        map.FillGraph("6","13");
        map.FillGraph("5","13");
        map.FillGraph("3","4");
        map.FillGraph("4","5");
        map.FillGraph("5","6");
        map.FillGraph("6","7");
        map.FillGraph("1","7");
        map.FillGraph("1","8");
        map.FillGraph("8","9");
        map.FillGraph("9","10");
        map.FillGraph("2","11");
        map.FillGraph("11","12");
        map.FillGraph("9","12");
        map.FillGraph("10","11");
        for(Map.Entry<Vertex, HashSet<Vertex>> x:map.graph.entrySet()){
                 System.out.print(x);

            System.out.println();
        }
        //map.FillGraph("1","5");
        //map.FillGraph("1","6");




        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
