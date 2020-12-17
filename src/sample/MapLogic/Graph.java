package sample.MapLogic;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sample.Delivery.DeliveryEdgeInfo;
import sample.Graphic.PointType;

import sample.Transport.BaseTransport;
import sample.Vector2D;

import java.io.*;
import java.time.LocalTime;
import java.util.*;

// Основной класс хранения данных ребер и вершин
public class Graph implements Serializable {
    private transient Pane root;
    public static HashMap<String, Vertex> Points = new HashMap<>(200,0.75f);
    public static Vertex productPoint;
    public HashMap<Vertex,HashSet<Vertex>> graph=new HashMap<>();
    public HashMap<Vertex,HashSet<Vertex>> edges=new HashMap<>();
    public HashMap<Vertex,HashMap<Vertex,Quality_Road>> quality_road=new HashMap<>();
    public HashMap< Vertex, HashMap<Vertex, HashMap<Integer, Double>>> Traffic=new HashMap<>();
    private ArrayList<Line> lines = new ArrayList<>();

    public void setRoot(Pane root) {
        this.root = root;
    }

    public Graph(Pane root) {
        this.root = root;
    }
    public Graph() { }
    public void delete(Vertex source){
     source.delete();
    }

    // Рисует соединения между точками
    public void DrawGraph(){
        for(Map.Entry<Vertex,HashSet<Vertex>> x:edges.entrySet()){
            for(Vertex y:x.getValue()){
                Line l=new Line(x.getKey().getX(),x.getKey().getY(),y.getX(),y.getY());
                try {
                    Color c = quality_road.get(x.getKey()).get(y).getColor();
                    l.setStroke(c);
                }catch (NullPointerException e){
                    System.out.println("Не выходит получить цвет дороги!");
                }
                l.setStrokeWidth(1.7);
                l.toBack();
                root.getChildren().addAll(l);

            }
        }
    }
    public void ClearConnection(Vertex a){
        for( Map.Entry<Vertex,HashSet<Vertex>> MapEntry:graph.entrySet()) {
            if (a.equals(MapEntry.getKey())){
                //graph.put(MapEntry.getKey(),null);
                graph.remove(MapEntry.getKey());
            }
            if (!MapEntry.getValue().isEmpty()) {
                for (Vertex vertex : MapEntry.getValue()) {
                    if (vertex.equals(a)) {
                        graph.get(MapEntry.getKey()).remove(vertex);
                    }

                }
            }
        }
    }

    public DeliveryEdgeInfo getOrtogonalEdges(Vertex Delivery, boolean isCar){
        DeliveryEdgeInfo info = new DeliveryEdgeInfo();
        ArrayList<Vertex> roads = new ArrayList<>();
        HashMap<Vertex,HashSet<Vertex>> Смежные_вершины = new HashMap<>();
        for( Map.Entry<Vertex,HashSet<Vertex>> MapEntry:edges.entrySet()) {
            for (Vertex vertex : MapEntry.getValue()) {
                //if (quality_road.get(vertex).get(MapEntry.getKey()) == null && quality_road.get(MapEntry.getKey()).get(vertex) == null){continue;}
                //if (quality_road.get(MapEntry.getKey()).get(vertex) == null){continue;}
                Vector2D directionToB = new Vector2D(vertex);
                directionToB.sub( new Vector2D(MapEntry.getKey()));

                Vector2D directionToDelivery = new Vector2D(Delivery);
                directionToDelivery.sub( new Vector2D(vertex));

                double angle = directionToB.getAngles(directionToDelivery);

                double projection = directionToB.getCosBetweenVectors(directionToDelivery)*directionToDelivery.length();
                Vector2D ProjectionVector = new Vector2D(vertex);
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


                //Line l = new Line(Delivery.getX(), Delivery.getY(), ProjectionVector.getX(), ProjectionVector.getY());
                Vertex PointOnEdge = ProjectionVector.convertToVertex();
                PointOnEdge.setName("Точка"+ Math.abs(projection)+24);
                roads.add(PointOnEdge);


                //l.setStroke(Color.FUCHSIA);
                //l.setStrokeWidth(1.7);

                //root.getChildren().addAll(l);


                HashSet<Vertex> hashSet = new HashSet();
                hashSet.add(MapEntry.getKey());
                hashSet.add(vertex);
                Смежные_вершины.put(PointOnEdge,hashSet);
                break;
            }
        }

        info.setPointOnEdge(roads);
        info.setPointDelivery(Delivery);
        info.setAdjacentVertexes(Смежные_вершины);
        return info;


    }

    public PathWrapper FindPath(BaseTransport vehicleOriginal, Vertex to) throws CloneNotSupportedException {
        try{
            BaseTransport vehicle = vehicleOriginal.clone();
            DeliveryEdgeInfo veh = getOrtogonalEdges(vehicle,false);
            DeliveryEdgeInfo end = getOrtogonalEdges(to,false);
            if (end.getAdjacentVertexes().size() == 0) { return null; }
            if (veh.getAdjacentVertexes().size() == 0) { return null; }
            var quality=Quality_Road.good;
            HashMap<Integer, Double> traffic = new HashMap<>();
            var f = veh.getAdjacentVertexes();
            for( Map.Entry<Vertex,HashSet<Vertex>> e : f.entrySet()){
                for( Vertex vert : e.getValue()) {
                    //if (quality_road.get(vert).get(e.getKey()) == null && quality_road.get(e.getKey()).get(vert) == null){continue;}
                    quality = Quality_Road.average;
                    if (quality_road.get(vert).get(veh.getAnotherVertex(vert)) != null) {
                        quality = quality_road.get(vert).get(veh.getAnotherVertex(vert));
                    }
                    if (Traffic.get(vert).get(veh.getAnotherVertex(vert)) != null) {
                        traffic =Traffic.get(vert).get(veh.getAnotherVertex(vert)) ;
                    }
                    Vector2D v = new Vector2D(e.getKey());
                    v.sub( new Vector2D(vehicle));

                    //System.out.println("vert" + vert);
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
                    Vector2D v = new Vector2D(e.getKey());
                    v.sub( new Vector2D(to));


                    FillGraph2(vert,e.getKey(),quality,traffic);
                    FillGraph2(to,e.getKey(),quality,traffic);
                }
            }
            return Find_min_path_with_optimized(vehicle,to);
        }catch(Exception e){
            System.out.println("Маршрут нельзя построить!");
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }


    }


//    public PathWrapper findPath( BaseTransport vehicle, Vertex to){
//
//        DeliveryEdgeInfo veh = parseAllEdges(vehicle,true);
//        DeliveryEdgeInfo end = parseAllEdges(to,false);
//        var quality=Quality_Road.good;
//        HashMap<Integer, Double> traffic = new HashMap<>();
//        var f = veh.getAdjacentVertexes();
//        for( Map.Entry<Vertex,HashSet<Vertex>> e : f.entrySet()){
//            for( Vertex vert : e.getValue()) {
//
//                if (quality_road.get(vert).get(veh.getAnotherVertex(vert)) != null)
//                    quality = quality_road.get(vert).get(veh.getAnotherVertex(vert));
//
//                if (Traffic.get(vert).get(veh.getAnotherVertex(vert)) != null) {
//                    traffic =Traffic.get(vert).get(veh.getAnotherVertex(vert)) ;
//                }
//
//                FillGraph2(vert, vehicle, quality,traffic);
//
//            }
//        }
//
//        for( Map.Entry<Vertex,HashSet<Vertex>> e : end.getAdjacentVertexes().entrySet()){
//            for( Vertex vert : e.getValue()) {
//                if (quality_road.get(vert).get(end.getAnotherVertex(vert)) != null)
//                    quality = quality_road.get(vert).get(end.getAnotherVertex(vert));
//
//                if (Traffic.get(vert).get(end.getAnotherVertex(vert)) != null) {
//                    traffic =Traffic.get(vert).get(end.getAnotherVertex(vert)) ;
//                }
//
//                FillGraph2(vert,e.getKey(),quality,traffic);
//                FillGraph2(to,e.getKey(),quality,traffic);
//
//            }
//        }
//        return find_min_path_with_optimized(vehicle,to);
//
//
//
//    }

    // Нахождением мин. пути по алгоритму Дейстктры
    public PathWrapper Find_min_path_with_optimized(BaseTransport start, Vertex end){
        //System.out.println("graph = "+graph);
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
                if(shortest_distances.get(u)==null || (shortest_distances.get(cur_v)+time) < shortest_distances.get(u)){
                //    System.out.println("Vert2 " + u);
               //     System.out.println("Cuv " + cur_v);
               //     System.out.println("T = "+shortest_distances.get(cur_v)+time);
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

        return new PathWrapper(shortest_distances,path);
    }

    // Подсчет время-затрат на путь path
    @Deprecated
    public double Count_time(PathWrapper pathWrapper){
        double time=0.0;
        for(Vertex s:pathWrapper.getPath()){
            time+=pathWrapper.getShortest_distance().get(s);
        }
        return time;
    }


    public DeliveryEdgeInfo parseAllEdges(Vertex Delivery, boolean isCar){
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


                    int length_par = isCar ? 2 : 100;
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

        info.setPointOnEdge(roads);
        info.setPointDelivery(Delivery);
        info.setAdjacentVertexes(Смежные_вершины);
        return info;
    }

    // Прорисовка минимального пути на графе
    public void DrawPath( ArrayList<Vertex> path){

        DrawPath(path, Color.GOLD);

    }

    public void DrawPath( ArrayList<Vertex> path,Color color){

        SequentialTransition sequentialTransition = new SequentialTransition();

        sequentialTransition.setCycleCount(1);
        sequentialTransition.setAutoReverse(false);

        int size = path.size();
        for (int i = 0; i < size-1; i++) {

            Vertex x = path.get(i);
            Vertex y = path.get(i+1);
            Line l = new Line(x.getX(), x.getY(), y.getX(), y.getY());
            l.setStroke(color);
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
    public void Connect(String v1, String v2, Quality_Road qr, HashMap<Integer,Double> traffic){

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
    public void AddPoint(String name, double X, double Y, PointType type){
        Vertex A1 = new Vertex(X,Y);
        A1.setName(name);
        A1.setPointType(type);
        Points.put(name,A1);
    }

    public void AddProduction(String name, double X, double Y, PointType type){
        productPoint = new Vertex(X,Y);
        productPoint.setName(name);
        productPoint.setPointType(type);
        Points.put(name,productPoint);
    }


    // Отобразить все точки на графе
    public void Draw(){
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