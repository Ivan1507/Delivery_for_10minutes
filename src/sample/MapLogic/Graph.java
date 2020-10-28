package sample.MapLogic;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import sample.DeliveryEdgeInfo;
import sample.MapLogic.Graphic.PointType;
import sample.Transport.BaseTransport;
import sample.Vector2D;

import java.io.*;
import java.time.LocalTime;
import java.util.*;

// Основной класс хранения данных ребер и вершин
public class Graph implements Serializable {
    private transient Pane root;
    public HashMap<String, Vertex> Points = new HashMap<>(200,0.75f);
    private HashMap<String, BaseTransport> Transport = new HashMap<>(200,0.75f);
    public HashMap<Vertex,HashSet<Vertex>> graph=new HashMap<>();
    public HashMap<Vertex,HashSet<Vertex>> edges=new HashMap<>();
    public HashMap<Vertex,HashMap<Vertex,Quality_Road>> quality_road=new HashMap<>();
    public HashMap< Vertex, HashMap<Vertex, HashMap<Integer, Double>>> Traffic=new HashMap<>();
    private ArrayList<Line> lines = new ArrayList<>();
    //public Map<Vertex,Double> shortest_distance=new HashMap<Vertex,Double>();

    public void setRoot(Pane root) {
        this.root = root;
    }

    public Graph(Pane root) {
        this.root = root;
    }
    public Graph() { }
    // Рисует соединения между точками
    public void DrawGraph(){
        for(Map.Entry<Vertex,HashSet<Vertex>> x:graph.entrySet()){
            for(Vertex y:x.getValue()){
                Line l=new Line(x.getKey().getX(),x.getKey().getY(),y.getX(),y.getY());
                try {
                    Color c = quality_road.get(x.getKey()).get(y).getColor();
                    l.setStroke(c);
                }catch (NullPointerException e){
                    System.out.println("Не выходит получить цвет дороги!");
                }
                l.setStrokeWidth(1.7);
                root.getChildren().addAll(l);
            }
        }
    }






    public PathWrapper findPath( BaseTransport vehicleOriginal, Vertex to) throws CloneNotSupportedException {
       // try{
        BaseTransport vehicle = vehicleOriginal.clone();
        DeliveryEdgeInfo veh = getOrtogonalEdges(vehicle,true);
        DeliveryEdgeInfo end = getOrtogonalEdges(to,false);
        var quality=Quality_Road.good;
        HashMap<Integer, Double> traffic = new HashMap<>();
        var f = veh.getAdjacentVertexes();
            for( Map.Entry<Vertex,HashSet<Vertex>> e : f.entrySet()){
                for( Vertex vert : e.getValue()) {
                    //if (quality_road.get(vert).get(e.getKey()) == null && quality_road.get(e.getKey()).get(vert) == null){continue;}


                    if (quality_road.get(vert).get(veh.getAnotherVertex(vert)) != null)
                        quality = quality_road.get(vert).get(veh.getAnotherVertex(vert));

                    if (Traffic.get(vert).get(veh.getAnotherVertex(vert)) != null) {
                        traffic =Traffic.get(vert).get(veh.getAnotherVertex(vert)) ;
                    }

                    //FillGraph2(vert, vehicle, quality,traffic); // for vehicle
                    FillGraph2(vert,e.getKey(),quality,traffic);
                    FillGraph2(vehicle,e.getKey(),quality,traffic);
                }
            }

        for( Map.Entry<Vertex,HashSet<Vertex>> e : end.getAdjacentVertexes().entrySet()){
            for( Vertex vert : e.getValue()) {
                //if (quality_road.get(vert).get(e.getKey()) == null && quality_road.get(e.getKey()).get(vert) == null){continue;}


                if (quality_road.get(vert).get(end.getAnotherVertex(vert)) != null)
                    quality = quality_road.get(vert).get(end.getAnotherVertex(vert));

                if (Traffic.get(vert).get(end.getAnotherVertex(vert)) != null) {
                    traffic =Traffic.get(vert).get(end.getAnotherVertex(vert)) ;
                }

                FillGraph2(vert,e.getKey(),quality,traffic);
                FillGraph2(to,e.getKey(),quality,traffic);

            }
        }
        return find_min_path_with_optimized(vehicle,to);
//        }catch(Exception e){
//            System.out.println("Маршрут нельзя построить!");
//            System.out.println(e.getMessage());
//            return null;
//        }


    }

    public PathWrapper find_min_path_with_optimized(BaseTransport start, Vertex end){
        Map<Vertex,Double> shortest_distances=new HashMap<>();
        Map<Vertex,Vertex> parents=new HashMap<>();
        parents.put(start,null);
        ArrayList<Vertex> path=new ArrayList<>();
        path.add(end);
        shortest_distances.put(start,0.0);
        ArrayDeque<Vertex> d=new ArrayDeque<>();
        d.add(start);
        while(d.size()!=0){
            Vertex cur_v= d.pop();
            Integer Curtime = LocalTime.now().getHour();
            for(Vertex u:graph.get(cur_v)){
                Double length=Math.sqrt(Math.pow((u.getX()-cur_v.getX()),2)+Math.pow((u.getY()-cur_v.getY()),2));
                Double quality = quality_road.get(u).get(cur_v).getStatus();
                Double traffic = 0.0;//

                if(Traffic.get(u).get(cur_v) != null){
                    traffic = Traffic.get(u).get(cur_v).get(Curtime);
                }
                double speed = start.getAvgSpeed();
                if( cur_v.isSpecial() || u.isSpecial()){

                    speed = 50;
                    traffic=0d;
                    quality = Quality_Road.Perfect.getStatus();
                }
                if(traffic == null) { traffic = 0d; };
                Double time = 0.38*length/(speed*(1-traffic)*(quality));// Вычисляем время доставки учитывая загруженность и качество дороги,
                if(shortest_distances.get(u)==null || shortest_distances.get(cur_v)+time < shortest_distances.get(u)){
                    shortest_distances.put(u,shortest_distances.get(cur_v)+time);
                    d.add(u);
                    parents.put(u,cur_v);
                }
            }
        }
        //this.shortest_distance=shortest_distances;
        var parent=parents.get(end);
        while(parents.get(parent) !=null){
            path.add(parent);
            parent =parents.get(parent);
        }
        path.add(start);
        Collections.reverse(path);
        return new PathWrapper( shortest_distances,path);
    }

    // Подсчет время-затрат на путь path
    public double getPathDrivingTime(PathWrapper pathWrapper){
        double d=0.0;
        for(Vertex s:pathWrapper.getPath()){
           // Vertex v=Points.get(s);
            d+=pathWrapper.getShortest_distance().get(s);
        }
        return d;
    }
    public DeliveryEdgeInfo getOrtogonalEdges(Vertex Delivery, boolean isCar){
        DeliveryEdgeInfo info = new DeliveryEdgeInfo();
        ArrayList<Vertex> roads = new ArrayList<>();
        HashMap<Vertex,HashSet<Vertex>> Смежные_вершины = new HashMap<>();
        long start = System.nanoTime();
        for( Map.Entry<Vertex,HashSet<Vertex>> MapEntry:edges.entrySet()) {
            for (Vertex vertex : MapEntry.getValue()) {
            if (quality_road.get(vertex).get(MapEntry.getKey()) == null){continue;}
            if (quality_road.get(MapEntry.getKey()).get(vertex) == null){continue;}
                Vector2D directionToB = new Vector2D(vertex);
                directionToB.sub( new Vector2D(MapEntry.getKey()));

                Vector2D directionToDelivery = new Vector2D(Delivery);
                directionToDelivery.sub( new Vector2D(vertex));

                double angle = directionToB.getAngles(directionToDelivery);
               // System.out.println("Angle between two vectors is " + angle);
                double projection = directionToB.getCosBetweenVectors(directionToDelivery)*directionToDelivery.length();
                //System.out.println("projection = " + projection);
                Vector2D ProjectionVector = new Vector2D(vertex);
                //ProjectionVector.sub( new Vector2D(MapEntry.getKey()));
                int length_par = isCar ? 45 : 45;
                if (Math.abs(projection) > 200)continue;

                ProjectionVector.inc( -Math.abs(projection),directionToB.getDirection() );
                Vector2D test = new Vector2D(Delivery);
                test.sub(ProjectionVector);

                Vector2D test2 = new Vector2D(MapEntry.getKey());
                test2.sub(new Vector2D(vertex));


                Vector2D test3 = new Vector2D(MapEntry.getKey());
                test3.sub(new Vector2D(Delivery));

                Vector2D test4 = new Vector2D(vertex);
                test4.sub(new Vector2D(Delivery));

                if (test4.length() > test2.length() || test3.length() > test2.length()){ continue; }
                if (test.length() > length_par) { continue;}
                Line l = new Line(Delivery.getX(), Delivery.getY(), ProjectionVector.getX(), ProjectionVector.getY());
                Vertex PointOnEdge = ProjectionVector.convertToVertex();
                PointOnEdge.setName("Точка"+ Math.abs(projection)+24);
                roads.add(PointOnEdge);

                l.setStroke(Color.FUCHSIA);
                l.setStrokeWidth(1.7);
                root.getChildren().addAll(l);
                HashSet<Vertex> hashSet = new HashSet();
                hashSet.add(MapEntry.getKey());
                hashSet.add(vertex);
                Смежные_вершины.put(PointOnEdge,hashSet);
                break;
            }
        }

        long end = System.nanoTime();

        info.setPointOnEdge(roads);
        info.setPointDelivery(Delivery);
        info.setAdjacentVertexes(Смежные_вершины);
        System.out.println( (end-start)/Math.pow(10,6) + " ms");
        return info;


    }

    public DeliveryEdgeInfo parseAllEdges(Vertex Delivery, boolean isCar){
        long startTime = System.nanoTime();
        DeliveryEdgeInfo info = new DeliveryEdgeInfo();
        ArrayList<Vertex> roads = new ArrayList<>();
        HashMap<Vertex,HashSet<Vertex>> hashSetHashMap;
        HashMap<Vertex,HashSet<Vertex>> Смежные_вершины = new HashMap<>();

        for( Map.Entry<Vertex,HashSet<Vertex>> MapEntry:edges.entrySet()) {


            for (Vertex vertex : MapEntry.getValue()) {

                Vector2D direction = new Vector2D(vertex);
                direction.sub( new Vector2D(MapEntry.getKey()));
               int step = direction.normalize();
               Vector2D start = new Vector2D(0,0);
                for(int i = 0; i < step; i++){
                    Vector2D dot_param = new Vector2D(Delivery);
                    dot_param.sub(start);
                    dot_param.sub(new Vector2D(MapEntry.getKey()));
                    double dot_product = Math.abs(direction.dot(dot_param));
                    Vector2D result =  new Vector2D(0,0);
                    result.add(new Vector2D(MapEntry.getKey()));
                    result.add(start);
                    result.sub(new Vector2D(Delivery));


                    int length_par = isCar ? 20 : 100;
                    if (dot_product < 0.005 && result.length()<length_par) {


                        Vector2D Out = new Vector2D(0, 0);
                        Out.add(new Vector2D(MapEntry.getKey()));
                        Out.add(start);



                        Line l = new Line(Delivery.getX(), Delivery.getY(), Out.getX(), Out.getY());
                        l.setStroke(Color.FUCHSIA);
                        l.setStrokeWidth(1.7);
                        root.getChildren().addAll(l);
                        Vertex PointOnEdge = Out.convertToVertex();
                        PointOnEdge.setName("Точка"+ i);
                        roads.add(PointOnEdge);

                        HashSet<Vertex> hashSet = new HashSet();
                        hashSet.add(MapEntry.getKey());
                        hashSet.add(vertex);
                        Смежные_вершины.put(PointOnEdge,hashSet);
                        //Смежные_вершины.add(MapEntry.getKey());
                        break;
                    }
                    start.add( direction );
                }

            }
        }
        long endTime = System.nanoTime();

        System.out.println( (endTime-startTime)/Math.pow(10,6) + " ms - итерационный");

        info.setPointOnEdge(roads);
        info.setPointDelivery(Delivery);
        info.setAdjacentVertexes(Смежные_вершины);
        return info;
    }

    // Прорисовка минимального пути на графе
    public void DrawPath( ArrayList<Vertex> path){

        SequentialTransition sequentialTransition = new SequentialTransition();

        sequentialTransition.setCycleCount(1);
        sequentialTransition.setAutoReverse(false);

        int size = path.size();
        for (int i = 0; i < size-1; i++) {

            Vertex x = path.get(i);
            Vertex y = path.get(i+1);
            Line l = new Line(x.getX(), x.getY(), y.getX(), y.getY());
            l.setStroke(Color.GOLD);
            l.setStrokeWidth(3);

            root.getChildren().addAll(l);

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), l);
            fadeTransition.setFromValue(0f);
            fadeTransition.setToValue(1f);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(true);
            sequentialTransition.getChildren().addAll(fadeTransition);
        }



        sequentialTransition.play();

        }


    // Заполняет данные об качестве дороги и трафике
    public void FillGraph2(Vertex ver1,Vertex ver2,Quality_Road qr, HashMap<Integer,Double> traffic){
        if (edges.get(ver1) == null){
         //   edges.put(ver1,new HashSet<>());
        }
        //edges.get(ver1).add(ver2); // Добавили несимметричное соеднинение

        if(graph.get(ver1)==null){
            graph.put(ver1,new HashSet<Vertex>());
        }
        if(graph.get(ver2)==null){
            graph.put(ver2,new HashSet<Vertex>());
        }
        graph.get(ver1).add(ver2);
        graph.get(ver2).add(ver1);
        //edges.get(ver1).add(ver2); // Добавили несимметричное соеднинение


        // Качество покрытия
        if(quality_road.get(ver1)==null){
            quality_road.put(ver1, new HashMap<>());
        }
        if(quality_road.get(ver2)==null){
            quality_road.put(ver2, new HashMap<>());
        }

        quality_road.get(ver1).put(ver2,qr);
        quality_road.get(ver2).put(ver1,qr);

        quality_road.get(ver1).put(ver1,qr);
        quality_road.get(ver2).put(ver2,qr);


        if(Traffic.get(ver1)==null){
            Traffic.put(ver1, new HashMap<Vertex, HashMap<Integer, Double>>());
        }
        if(traffic.get(ver2)==null){
            Traffic.put(ver2, new HashMap<Vertex, HashMap<Integer, Double>>());
        }
        Traffic.get(ver1).put(ver2,traffic);
        Traffic.get(ver2).put(ver1,traffic);

        Traffic.get(ver1).put(ver1,traffic);
        Traffic.get(ver2).put(ver2,traffic);

    }
        // Заполняет данные об качестве дороги и трафике
    public void FillGraph(String v1,String v2,Quality_Road qr, HashMap<Integer,Double> traffic){

        Vertex ver1=Points.get(v1);
        Vertex ver2=Points.get(v2);
        if (edges.get(ver1) == null){
            edges.put(ver1,new HashSet<>());
        }

        if(graph.get(ver1)==null){
            graph.put(ver1,new HashSet<Vertex>());
        }
        if(graph.get(ver2)==null){
            graph.put(ver2,new HashSet<Vertex>());
        }
        graph.get(ver1).add(ver2);
        graph.get(ver2).add(ver1);
        edges.get(ver1).add(ver2); // Добавили несимметричное соеднинение


        // Качество покрытия
        if(quality_road.get(ver1)==null){
            quality_road.put(ver1, new HashMap<>());
        }
        if(quality_road.get(ver2)==null){
            quality_road.put(ver2, new HashMap<>());
        }

        quality_road.get(ver1).put(ver2,qr);
        quality_road.get(ver2).put(ver1,qr);

        quality_road.get(ver1).put(ver1,qr);
        quality_road.get(ver2).put(ver2,qr);


        if(Traffic.get(ver1)==null){
            Traffic.put(ver1, new HashMap<Vertex, HashMap<Integer, Double>>());
        }
        if(traffic.get(ver2)==null){
            Traffic.put(ver2, new HashMap<Vertex, HashMap<Integer, Double>>());
        }
        Traffic.get(ver1).put(ver2,traffic);
        Traffic.get(ver2).put(ver1,traffic);

        Traffic.get(ver1).put(ver1,traffic);
        Traffic.get(ver2).put(ver2,traffic);

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
    // Добавить точку на граф (без рисования)
    public void addPoint(String name, double X, double Y, PointType type){
        Vertex A1 = new Vertex(X,Y);
        A1.setName(name);
        A1.setPointType(type);
        Points.put(name,A1);
    }

    // Отобразить все точки на графе
    public void draw(){
    for(Map.Entry<String,Vertex> point : Points.entrySet())
        point.getValue().placeTo(root);
    }

    // Сериализовать граф в файл
     public  void SaveObject(String name) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("Graph.dat"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }

    // Десериализовать граф из файла
    public void LoadObject(String name) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("Graph.dat")));
        Graph readed = (Graph) objectInputStream.readObject();
        //System.out.println(readed.Points.size());
    }
}
