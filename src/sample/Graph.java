package sample;

import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.HashMap;

public class Graph {
    private Pane root;
    private HashMap<String,Vertex> Points = new HashMap<>(200,0.75f);
    private HashMap<String,Vertex> ConnectedPoints = new HashMap<>(200,0.75f);

    public void setRoot(Pane root) {
        this.root = root;
    }

    public Graph(Pane root) {
        this.root = root;
    }

    public void addPoint(String name, double X, double Y){
        Vertex A1 = new Vertex(X,Y);
        A1.setName(name);
        A1.addUI(root);
        Points.put(name,A1);
    }
    public void connectPoint(String From, String To,Quality_Road quality_road){
        Points.get(From).connectTo(Points.get(To),quality_road);
    }
}
