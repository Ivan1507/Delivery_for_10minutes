package sample.MapLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PathWrapper {
    private Map<Vertex,Double> shortest_distance = new HashMap<>();
    private ArrayList<Vertex> path=new ArrayList<>();

    public PathWrapper(Map<Vertex, Double> shortest_distance, ArrayList<Vertex> path) {
        this.shortest_distance = shortest_distance;
        this.path = path;
    }

    public PathWrapper() {
    }
    //Слияние двух путей
    public PathWrapper MergePathsWrappers(PathWrapper pt,PathWrapper pt2){
        if(pt2!=null)
        for(Vertex v:pt2.getPath()){
            pt.path.add(v);
        }
        return pt;
    }
    public void addToPath(Vertex v){
        this.path.add(v);
    }
    public void setPath(ArrayList<Vertex> path) {
        this.path = path;
    }

    public Map<Vertex, Double> getShortest_distance() {
        return shortest_distance;
    }

    public ArrayList<Vertex> getPath() {
        return path;
    }




}
