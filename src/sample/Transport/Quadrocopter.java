package sample.Transport;

import sample.Delivery.Delivery;
import sample.Main;
import sample.MapLogic.PathWrapper;
import sample.MapLogic.Vertex;
import sample.Vector2D;

import java.util.ArrayList;

public class Quadrocopter extends BaseTransport {

    public void init() {
        init(false);
    }

    public void init(boolean set_speed) {
        setVolume_baggage(5);
        setMax_weight_baggage(4);
        if (set_speed) setMaxSpeed(90);
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
    public double CountTime(PathWrapper pathWrapper) {
        ArrayList<Vertex> path = pathWrapper.getPath();
        Vertex v1 = path.get(0);
        Vertex v2 = path.get(1);

        Vector2D vector2D = new Vector2D(v1);
        vector2D.sub(new Vector2D(v2));

        return 0.38 * vector2D.length() / (getAvgSpeed());
    }

    @Override
    public PathWrapper FindPath(Vertex to) throws CloneNotSupportedException {
        try {
            PathWrapper pathWrapper = new PathWrapper();
            ArrayList<Vertex> vertices = new ArrayList<>();
            vertices.add(this);
            vertices.add(to);
            pathWrapper.setPath(vertices);
            return pathWrapper;
        } catch (Exception e) {
            System.out.println("Маршрут нельзя построить!");
            System.out.println(e.getMessage());
            return null;
        }

    }

    public PathWrapper FindPath(Vertex from, Vertex to) throws CloneNotSupportedException {
        try {
            PathWrapper pathWrapper = new PathWrapper();
            ArrayList<Vertex> vertices = new ArrayList<>();
            vertices.add(from);
            vertices.add(to);
            pathWrapper.setPath(vertices);
            return pathWrapper;
        } catch (Exception e) {
            System.out.println("Маршрут нельзя построить!");
            System.out.println(e.getMessage());
            return null;
        }

    }


    @Override
    public Quadrocopter clone() throws CloneNotSupportedException {
        return (Quadrocopter) super.clone();
    }

    @Override
    public Double getExecuteTime(Delivery delivery) throws CloneNotSupportedException {
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
                PathWrapper wrapper2 = FindPath(Main.map.productPoint, delivery.getAddress());


                PathWrapper full_path = wrapper.MergePathsWrappers(wrapper2);
               // Main.map.DrawPath(full_path.getPath());
                return CountTime(full_path);
            } else {

                PathWrapper wrapper = FindPath(delivery.getAddress());

               // Main.map.DrawPath(wrapper.getPath());

                return CountTime(wrapper);

            }

        } catch (Exception e) {
            //System.out.println("Не построить маршрут!");
            return null;
        }

    }
@Override
    public PathWrapper getPathToDelivery(Delivery delivery) throws CloneNotSupportedException {
        try {
            boolean hasProducts = hasProducts(delivery);
            if (!hasProducts) {
                PathWrapper wrapper = FindPath(Main.map.productPoint);

                double x = this.getX();
                double y = this.getY();
                Quadrocopter clone = clone();
                clone.setX(Main.map.productPoint.getX());
                clone.setY(Main.map.productPoint.getY());
                PathWrapper wrapper2 = clone.FindPath(delivery.getAddress());
                PathWrapper full_path = wrapper.MergePathsWrappers(wrapper2);
                //clone.setX(x);
                //clone.setY(y);
//                if (wrapper2.getPath().size() == 0) {
//                return null;
//                }


                return full_path;
            } else {
                PathWrapper wrapper = FindPath(delivery.getAddress());

                return wrapper;
            }

        }
        catch (Exception r){ return null; }
    }
}
//
//