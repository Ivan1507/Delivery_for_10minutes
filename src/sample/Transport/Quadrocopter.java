package sample.Transport;

import javafx.scene.paint.Color;
import sample.Delivery.Delivery;
import sample.Delivery.DeliveryEdgeInfo;
import sample.Main;
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



    @Override
    public Quadrocopter clone() throws CloneNotSupportedException {
        return (Quadrocopter) super.clone();
    }

    public void getExecuteTime(Delivery delivery) throws CloneNotSupportedException {
        try {
            boolean hasProducts = hasProducts(delivery);
            if (!hasProducts) {
                System.out.println("Main.map.productPoint = " + Main.map.productPoint);
                PathWrapper wrapper = FindPath(Main.map.productPoint);

                System.out.println("wrapper = " + wrapper);
                double x = getX();
                double y = getY();

                Quadrocopter clone = clone();
                clone.setX(Main.map.productPoint.getX());
                clone.setY(Main.map.productPoint.getY());
                PathWrapper wrapper2 = FindPath(Main.map.productPoint,delivery.getAddress());

                wrapper.getPath().forEach((e) -> {
                    // System.out.println(e.getName());
                });


                //setX(x);
                //setY(y);

                wrapper2.getPath().forEach((e) -> {
                    //  System.out.println("Отсюда" + e.getName());
                });
                Main.map.DrawPath(wrapper.getPath(), Color.rgb(255,25,25));
                Main.map.DrawPath(wrapper2.getPath());
            } else {


            }

        }
        catch (Exception e){
            //System.out.println("Не построить маршрут!");
        }

    }



}
//
//