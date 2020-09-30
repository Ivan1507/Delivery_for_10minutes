package sample;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

// Ребро
// Todo: Refactor class name to 'Road'
public class Edge {

    private Vertex From;
    private Vertex To;
    private boolean Directed;
    private double Traffic_jam=0d;
    private HashMap<Integer,Double> Traffic = new HashMap<>();
    {
        Traffic.put(15,0.25);

    }
    public Edge(Vertex from, Vertex to, boolean directed) {
        From = from;
        To = to;
        Directed = directed;
    }

    public Vertex getAnotherPoint(Vertex Base) {
        if (!Base.equals(To)) {return  To;}
        else
        {
            return From;
        }
    }
    public static Edge ConnectVertexes(Vertex from, Vertex to, boolean directed){
        //A.setDistance(Distance);
        return new Edge(from,to,directed);
    }

    public double Traffic( int T) {
        Double Result = Traffic.get(T);
        if (Result.equals( null) ) {
            return 0;
        }
        else
        {
            return Result;
        }
    }

    public Vertex getFrom() {
        return From;
    }

    public void setFrom(Vertex from) {
        From = from;
    }

    public Vertex getTo() {
        return To;
    }

    public void setTo(Vertex to) {
        To = to;
    }

    public boolean isDirected() {
        return Directed;
    }

    public void setDirected(boolean directed) {
        Directed = directed;
    }

    public double calculateDistance(){
        return Math.sqrt(Math.pow(getFrom().getX()-getTo().getX(),2) + Math.pow(getFrom().getY() - getTo().getY(),2));
    }
    public double getTraffic_jam() {
        return Traffic_jam;
    }

    public void setTraffic_jam(double traffic_jam) {
        Traffic_jam = traffic_jam;
    }

}
