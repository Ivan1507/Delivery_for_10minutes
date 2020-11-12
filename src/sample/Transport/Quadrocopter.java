package sample.Transport;

import javafx.scene.paint.Color;
import sample.Delivery.Delivery;
import sample.Delivery.DeliveryEdgeInfo;
import sample.Main;
import sample.MapLogic.Graph;
import sample.MapLogic.PathWrapper;
import sample.MapLogic.Quality_Road;
import sample.MapLogic.Vertex;
import sample.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Quadrocopter extends BaseTransport {

    public void init(){
        init(false);
    }
    public Delivery current_dev;
    public void init(boolean set_speed){
        setVolume_baggage(5);
        setMax_weight_baggage(4);
        if (set_speed)  setMaxSpeed(90);
    }
    public Quadrocopter() {
        init(true);
    }
    public Quadrocopter(double x, double y) {
        super(x, y);
        init(true);
    }

    public Quadrocopter(double x, double y, double maxSpeed, String name) {

        super(x, y, maxSpeed, name);
        init();
    }

    @Override
    public double Count_time(PathWrapper pathWrapper){
        ArrayList<Vertex> path = pathWrapper.getPath();
        Vertex v1 = path.get(0);
        Vertex v2 = path.get(1);

        Vector2D vector2D = new Vector2D(v1);
        vector2D.sub(new Vector2D(v2));

        return 0.38*vector2D.length()/(getAvgSpeed());
    }

    public PathWrapper FindPath(Vertex to) throws CloneNotSupportedException {
        try{
            PathWrapper pathWrapper = new PathWrapper();
            ArrayList<Vertex> vertices = new ArrayList<>();
            vertices.add( this );
            vertices.add( to );
            pathWrapper.setPath( vertices );
            return pathWrapper;
        }catch(Exception e){
            System.out.println("Маршрут нельзя построить!");
            System.out.println(e.getMessage());
            return null;
        }

    }
    public PathWrapper FindPath(Vertex from, Vertex to) throws CloneNotSupportedException {
        try{
            PathWrapper pathWrapper = new PathWrapper();
            ArrayList<Vertex> vertices = new ArrayList<>();
            vertices.add( from );
            vertices.add( to );
            pathWrapper.setPath( vertices );
            return pathWrapper;
        }catch(Exception e){
            System.out.println("Маршрут нельзя построить!");
            System.out.println(e.getMessage());
            return null;
        }

    }





    public void getExecuteTime(BaseTransport bas,Delivery delivery) throws CloneNotSupportedException {
        try {
            boolean hasProducts = hasProducts(delivery);
            if (!hasProducts) {
                System.out.println("Main.map.productPoint = " + Graph.productPoint);
                PathWrapper wrapper = FindPath(Graph.productPoint);

                System.out.println("wrapper = " + wrapper);
                double x = getX();
                double y = getY();
                bas.products=Main.kitchen.getProducts_of_kitchen();
                current_dev=delivery;

                PathWrapper wrapper2 = FindPath(Graph.productPoint,delivery.getAddress());


                //setX(x);
                //setY(y);


                //Main.map.DrawPath(wrapper.getPath(), Color.rgb(255,25,25));
                Main.map.DrawPath(wrapper.MergePathsWrappers(wrapper2).getPath());
                System.out.println("Для квадро = " + Count_time(wrapper.MergePathsWrappers(wrapper2)));
            } else {


            }

        }
        catch (Exception e){
            //System.out.println("Не построить маршрут!");
        }

    }
    @Override
    public Quadrocopter clone() throws CloneNotSupportedException {
        return (Quadrocopter) super.clone();
    }



}
//
//