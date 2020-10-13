package sample.MapLogic;

import javafx.scene.layout.Pane;
import sample.MapLogic.Graphic.PointType;
import sample.Transport.BaseTransport;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class Graph implements Serializable {
    private transient Pane root;

    private HashMap<String, Vertex> Points = new HashMap<>(200,0.75f);
    private HashMap<String, BaseTransport> Transport = new HashMap<>(200,0.75f);
    //private transient HashMap<String,Vertex> ConnectedPoints = new HashMap<>(200,0.75f);
    HashSet<HashSet<Vertex>> graph=new HashSet<>();
    public void setRoot(Pane root) {
        this.root = root;
    }

    public Graph(Pane root) {
        this.root = root;
    }
    public Graph() {

    }
    public void InputGraph(int vertex,int edges){
        System.out.println("Введите количество вершин и количество ребер:");
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

    public void addPoint(String name, double X, double Y, PointType type){
        Vertex A1 = new Vertex(X,Y);
        A1.setName(name);
        A1.setPointType(type);
        Points.put(name,A1);
    }

    public void draw(){
    for(Map.Entry<String,Vertex> point : Points.entrySet())
        point.getValue().placeTo(root);
    }

    public void connectPoint(String From, String To, Quality_Road quality_road){
        //Points.get(From).connectTo(Points.get(To),quality_road);
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
