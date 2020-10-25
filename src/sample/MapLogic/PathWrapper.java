package sample.MapLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PathWrapper {
    private Map<Vertex,Double> shortest_distance = new HashMap<>();
    private ArrayList<String> path=new ArrayList<>();

    public PathWrapper(Map<Vertex, Double> shortest_distance, ArrayList<String> path) {
        this.shortest_distance = shortest_distance;
        this.path = path;
    }

    public Map<Vertex, Double> getShortest_distance() {
        return shortest_distance;
    }

    public ArrayList<String> getPath() {
        return path;
    }




}
