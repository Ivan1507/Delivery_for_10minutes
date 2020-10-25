package sample;

import sample.MapLogic.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DeliveryEdgeInfo {
private Vertex pointDelivery;
private ArrayList<Vertex> pointOnEdge; // Точки на ребрах
private HashMap<Vertex,HashSet<Vertex>> adjacentVertexes = new HashMap<>(); // Смежные вершины

public void setPointOnEdge(ArrayList<Vertex> pointOnEdge) {
        this.pointOnEdge = pointOnEdge;
}

    public void setAdjacentVertexes(HashMap<Vertex, HashSet<Vertex>> adjacentVertexes) {
        this.adjacentVertexes = adjacentVertexes;
    }

    public Vertex getPointDelivery() {
        return pointDelivery;
    }

    public void setPointDelivery(Vertex pointDelivery) {
        this.pointDelivery = pointDelivery;
        }

    public ArrayList<Vertex> getPointOnEdge() {
        return pointOnEdge;
    }

    public HashMap<Vertex, HashSet<Vertex>> getAdjacentVertexes() {
        return adjacentVertexes;
    }
    public Vertex getAnotherVertex(Vertex nonReturn) {
       Vertex source;

       for (Map.Entry<Vertex,HashSet<Vertex>> entry: getAdjacentVertexes().entrySet()){
           for( Vertex v: entry.getValue()){
               if (v == null) { continue; }
               if (!nonReturn.equals(v)){return v;}
           }
       }

       return null;
    }

    public void print(){
        System.out.println("Смежные");
        adjacentVertexes.forEach((k,v)-> System.out.println(k + "/" + v));
        System.out.println("Ребра");
        pointOnEdge.forEach((k)-> System.out.println(k ));

    }



}