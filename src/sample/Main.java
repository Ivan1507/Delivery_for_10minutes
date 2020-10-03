package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        primaryStage.setTitle("Hello World");
       // BorderPane root = new BorderPane();
        Vertex v=new Vertex(1,2);
        v.setName("MIET");
        v.SaveToFile("src\\save.txt");
        Vertex v1=new Vertex();
        v1.getFromFile("src\\save.txt");
        System.out.println(v1);
        //Graph Map = new Graph(root);

        //Map.addPoint("A",45,40);
       // Map.addPoint("B",80,40);
       // Map.connectPoint("A","B");



        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
