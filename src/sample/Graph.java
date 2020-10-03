package sample;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Objects;

public class Graph implements Serializable {
    private transient Group root;
    private HashMap<String,Vertex> Points = new HashMap<>(200,0.75f);
    private transient HashMap<String,Vertex> ConnectedPoints = new HashMap<>(200,0.75f);

    public void setRoot(Group root) {
        this.root = root;
    }

    public Graph(Group root) {
        this.root = root;
    }
    public Graph() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph = (Graph) o;
        return Objects.equals(Points, graph.Points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Points);
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

    public void SaveObject(String name) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("Graph"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject( this );
        objectOutputStream.close();
    }


    public Graph LoadObject() throws IOException{
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("Graph")));
        return new Graph();
    }
}
