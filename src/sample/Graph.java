package sample;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Objects;

public class Graph implements Serializable {
    private transient Pane root;
    private HashMap<String,FPoint> Points = new HashMap<>(200,0.75f);
    private transient HashMap<String,FPoint> ConnectedPoints = new HashMap<>(200,0.75f);

    public void setRoot(Pane root) {
        this.root = root;
    }

    public Graph(Pane root) {
        this.root = root;
    }
    public Graph() {

    }
    private static Graph Singleton;
    public static Graph getInstance(Pane root)
    {

        if (Singleton != null) {
            return Singleton;
        }
            else {
                return new Graph(root);
            }
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
        FPoint A1 = new FPoint(X,Y);
        A1.setName(name);
        A1.addUI(root);
        Points.put(name,A1);
    }

    public void connectPoint(String From, String To,Quality_Road quality_road){
        Points.get(From).connectTo(Points.get(To),quality_road);
    }

    public  void SaveObject(String name) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("Graph.dat"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }


    public void LoadObject(String name) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("Graph.dat")));
        Graph readed = (Graph) objectInputStream.readObject();
        System.out.println(readed.Points.size());
    }
}
