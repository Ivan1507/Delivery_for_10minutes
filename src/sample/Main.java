package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.MapLogic.Graph;
import sample.MapLogic.Graphic.PointType;

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

        map.addPoint("1",124,85, PointType.Triangle);
        map.addPoint("2",45,55, PointType.Square);
        map.addPoint("3",94,55, PointType.TwoCricle);


        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
