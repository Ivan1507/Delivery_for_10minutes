package sample.MapLogic;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import sample.MapLogic.Graphic.PointType;
import sample.Transport.BaseTransport;

import java.io.*;
import java.util.*;
import java.util.Deque;

public class Graph implements Serializable {
    private transient Pane root;

    public HashMap<String, Vertex> Points = new HashMap<>(200,0.75f);
    private HashMap<String, BaseTransport> Transport = new HashMap<>(200,0.75f);
    //private transient HashMap<String,Vertex> ConnectedPoints = new HashMap<>(200,0.75f);
    public HashMap<Vertex,HashSet<Vertex>> graph=new HashMap<>();
    public HashMap<Vertex,HashMap<Vertex,Double>> quality_road=new HashMap<>();
    public HashMap< Vertex, HashMap<Vertex, HashMap<Integer, Double>>>  Traffic=new HashMap<>();

    // {
     //   V: {
         //  k: U, v: 0.1;

   // }
    //


    // {
    // V:
    // {
    // U: {15: 0.3, 16:0.2}
    // I:
    // }
    // HashMap< Vertex, HashMap<Vertex, HashMap<Integer, Double>>>  Traffic
    //{ V: {U{15:0.3, 16:0.2},I{15:0.3, 16:0.2}}
    // I: {U{15:0.3, 16:0.2},V{15:0.3, 16:0.2}}
    //}
    public void setRoot(Pane root) {
        this.root = root;
    }

    public Graph(Pane root) {
        this.root = root;
    }
    public Graph() {

    }
    public void DrawGraph(){
        for(Map.Entry<Vertex,HashSet<Vertex>> x:graph.entrySet()){
            for(Vertex y:x.getValue()){
                Line l=new Line(x.getKey().getX(),x.getKey().getY(),y.getX(),y.getY());
                l.setStroke(Color.GREY);
                root.getChildren().addAll(l);
            }
        }
    }
    public void Show_path(String s){
        Vertex v=Points.get(s);

    }
    public ArrayList<String> find_min_path(String star, String en){
        Vertex start=Points.get(star);
        Vertex end=Points.get(en);
        Map<Vertex,Double> shortest_distances=new HashMap<Vertex,Double>();
        Map<Vertex,Vertex> parents=new HashMap<Vertex,Vertex>();
        parents.put(start,null);
        ArrayList<String> path=new ArrayList<>();
        path.add(end.getName());
        shortest_distances.put(start,0.0);
        ArrayDeque<Vertex> d=new ArrayDeque<>();
        d.add(start);
        while(d.size()!=0){
            Vertex cur_v= (Vertex) d.pop();
            for(Vertex u:graph.get(cur_v)){
                Double cost=Math.sqrt(Math.pow((u.getX()-cur_v.getX()),2)+Math.pow((u.getY()-cur_v.getY()),2));


                if(shortest_distances.get(u)==null || shortest_distances.get(cur_v)+cost < shortest_distances.get(u)){
                    shortest_distances.put(u,shortest_distances.get(cur_v)+cost);
                    d.add(u);
                    parents.put(u,cur_v);
                }
            }
        }
        var parent=parents.get(end);
        while(parents.get(parent) !=null){
            path.add(parent.getName());
            parent =parents.get(parent);
        }
        path.add(star);
        Collections.reverse(path);
        return path;
    }
    public void InputGraph(int vertex,int edges){
        System.out.println("Введите количество вершин и количество ребер:");
    }

 //   public Map<Vertex,HashSet<HashMap<Vertex,Double>>> quality_road=new HashMap<>();
  //  public HashMap< Vertex, HashMap<Vertex, HashMap<Integer, Double>>>  Traffic=new HashMap<>();
    public void FillGraph(String v1,String v2,Quality_Road qr, HashMap<Integer,Double> traffic){
        Vertex ver1=Points.get(v1);
        Vertex ver2=Points.get(v2);
        if(graph.get(ver1)==null){
            graph.put(ver1,new HashSet<Vertex>());

        }
        if(graph.get(ver2)==null){
            graph.put(ver2,new HashSet<Vertex>());
        }
        graph.get(ver1).add(ver2);
        graph.get(ver2).add(ver1);



        // Качество покрытия
        if(quality_road.get(ver1)==null){
            quality_road.put(ver1, new HashMap<>());
        }
        if(quality_road.get(ver2)==null){
            quality_road.put(ver2, new HashMap<>());
        }

        quality_road.get(ver1).put(ver2,qr.getStatus());
        quality_road.get(ver2).put(ver1,qr.getStatus());


        // Пробки
        //HashMap< Vertex, HashMap<Vertex, HashMap<Integer, Double>>>
        if(Traffic.get(ver1)==null){
            Traffic.put(ver1, new HashMap<Vertex, HashMap<Integer, Double>>());
        }
        if(traffic.get(ver2)==null){
            Traffic.put(ver2, new HashMap<Vertex, HashMap<Integer, Double>>());
        }
        Traffic.get(ver1).put(ver2,traffic);
        Traffic.get(ver2).put(ver1,traffic);


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
