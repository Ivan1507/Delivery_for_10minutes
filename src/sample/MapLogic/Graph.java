package sample.MapLogic;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import sample.DeliveryEdgeInfo;
import sample.MapLogic.Delivery.Delivery;
import sample.MapLogic.Graphic.PointType;
import sample.Transport.BaseTransport;
import sample.Vector2D;

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Deque;
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


    // from- машина
    // to - заказ
    public void find_min_path( Vertex from , Vertex to, BaseTransport transport){


        DeliveryEdgeInfo from_info = parseAllEdges( from,true );
        DeliveryEdgeInfo to_info = parseAllEdges( to,false );
        Integer Curtime = LocalTime.now().getHour();
    to_info.getAdjacentVertexes().forEach(
            (to_k,to_v)-> {
                to_v.forEach(
                        to_vertex_adj->{ // to_vertex_adj - смежная вершина точки на ребре для заказа
                            from_info.getAdjacentVertexes().forEach((k, v) -> { // k - точка на ребре для заказа
                                v.forEach(vertex -> { // verex - смежная вершина стартовой точки
                                    // Для каждой точки для данного заказа находим дистанцию от точки на ребре(машина)
                                    // до каждой смежной вершины
                                    Vector2D Distance = new Vector2D(vertex);
                                    Distance.sub(new Vector2D(from));
                                    double dist = Distance.length();

                                    PathWrapper pathWrapper = find_min_path_with_vert(vertex,to_vertex_adj,transport );
                                    double time_to_target = Count_time( pathWrapper );
                                    Double length=Math.sqrt(Math.pow((to_vertex_adj.getX()-k.getX()),2)+Math.pow((to_vertex_adj.getY()-k.getY()),2));
                                   // Double quality = quality_road.get(u).get(cur_v).getStatus();
                                    /// cur_v

                                    double traffic=0;
                                    if(Traffic.get(k).get(to_vertex_adj) != null){
                                        traffic = Traffic.get(k).get(to_vertex_adj).get(Curtime);
                                    }

                                    Double time2 = length/(transport.getAvgSpeed()*(1-traffic)); // от смежн вершины до точки на ребре для заказа
                                    System.out.println(time2);
                                });
                            });
                        }
                );
            });
    }

    public void find_min_path_test( Vertex from , Vertex to, BaseTransport transport){


        DeliveryEdgeInfo from_info = parseAllEdges( from,true );
        DeliveryEdgeInfo to_info = parseAllEdges( to,false );
        Integer Curtime = LocalTime.now().getHour();
        to_info.getAdjacentVertexes().forEach(
                (to_k,to_v)-> {
                    to_v.forEach(
                            to_vertex_adj->{ // to_vertex_adj - смежная вершина точки на ребре для заказа
                                from_info.getAdjacentVertexes().forEach((k, v) -> { // k - точка на ребре для заказа
                                    v.forEach(vertex -> { // verex - смежная вершина стартовой точки
                                        Vector2D Distance = new Vector2D(vertex);
                                        Distance.sub(new Vector2D(from));
                                        double dist = Distance.length();

                                        PathWrapper pathWrapper = find_min_path_with_vert(vertex,to_vertex_adj,transport );
                                        double time_to_target = Count_time( pathWrapper );
                                        Double length=Math.sqrt(Math.pow((to_vertex_adj.getX()-k.getX()),2)+Math.pow((to_vertex_adj.getY()-k.getY()),2));
                                        System.out.println(to_info.getAnotherVertex(to_vertex_adj));
                                        double trafficInt=0;
                                        //if(Traffic.get(k).get(to_vertex_adj) != null){
                                       var traffic = Traffic.get(to_vertex_adj).get(to_vertex_adj);
                                      //  if (traffic.get(Curtime) != null) {
                                       //    // trafficInt =traffic.get(Curtime);
                                        //}
                                        System.out.println("traffic = " + traffic);
                                        Double quality = quality_road.get(to_vertex_adj).get(to_info.getAnotherVertex(to_vertex_adj)).getStatus();
                                        Double time = length/(transport.getAvgSpeed()*(1-trafficInt)*(quality));// Вычисляем время доставки учитывая загруженность и качество дороги,

                                        System.out.println(time_to_target+time);
                                    });
                                });
                            }
                    );
                });
    }

    public PathWrapper find_min_path_with_vert(Vertex start, Vertex end, BaseTransport transport){
        Map<Vertex,Double> shortest_distances=new HashMap<>();
        Map<Vertex,Vertex> parents=new HashMap<>();
        parents.put(start,null);
        ArrayList<String> path=new ArrayList<>();
        path.add(end.getName());
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

                Double time = length/(transport.getAvgSpeed()*(1-traffic)*(quality));// Вычисляем время доставки учитывая загруженность и качество дороги,
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
            path.add(parent.getName());
            parent =parents.get(parent);
        }
        path.add(start.getName());
        Collections.reverse(path);
        return new PathWrapper( shortest_distances,path);
    }
        // Метод нахождения минимального пути между двумя точками на базовом транспорте по алгоритму Дейкстры
     public PathWrapper find_min_path_with_vert(String star, String en, BaseTransport transport){
        Vertex start=Points.get(star);
        Vertex end=Points.get(en);
        Map<Vertex,Double> shortest_distances=new HashMap<>();
        Map<Vertex,Vertex> parents=new HashMap<>();
        parents.put(start,null);
        ArrayList<String> path=new ArrayList<>();
        path.add(end.getName());
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

                Double time = length/(transport.getAvgSpeed()*(1-traffic)*(quality));// Вычисляем время доставки учитывая загруженность и качество дороги,
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
            path.add(parent.getName());
            parent =parents.get(parent);
        }
        path.add(star);
        Collections.reverse(path);
        return new PathWrapper( shortest_distances,path);
    }
    // Подсчет время-затрат на путь path
    public double Count_time(PathWrapper pathWrapper){
        double d=0.0;
        for(String s:pathWrapper.getPath()){
            Vertex v=Points.get(s);
            d+=pathWrapper.getShortest_distance().get(v);
        }
        return d;
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

                       // System.out.println(Out);

                        Line l = new Line(Delivery.getX(), Delivery.getY(), Out.getX(), Out.getY());
                        l.setStroke(Color.FUCHSIA);
                        l.setStrokeWidth(1.7);
                        root.getChildren().addAll(l);
                        Vertex PointOnEdge = Out.convertToVertex();
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
    public void DrawPath( ArrayList<String> path){

        SequentialTransition sequentialTransition = new SequentialTransition();

        sequentialTransition.setCycleCount(1);
        sequentialTransition.setAutoReverse(false);

        int size = path.size();
        for (int i = 0; i < size-1; i++) {

            Vertex x = Points.get(path.get(i));
            Vertex y = Points.get(path.get(i+1));
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
