package sample.Transport;

import sample.Delivery.Delivery;
import sample.Main;
import sample.MapLogic.Graph;
import sample.MapLogic.PathWrapper;
import sample.MapLogic.Quality_Road;
import sample.MapLogic.Vertex;
import sample.Product;
import sample.Vector2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// Базовый класс для описания всех видов транспортых средств
public class BaseTransport extends Vertex {
    public List<Vertex> resultWaypoints;
    public int indWaypoint=0;
    private double maxSpeed=120;
    public ArrayList<Product> products=new ArrayList<>();
    private Delivery activeDelivery=null;
    private double max_volume_baggage = 100;
    private double max_weight_baggage = 100;


    public Delivery getActiveDelivery() {
        return activeDelivery;
    }

    public void setActiveDelivery(Delivery activeDelivery) {
        this.activeDelivery = activeDelivery;
    }

    public double getMaxSpeed(){
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public BaseTransport(double x, double y) {
        super(x, y);
    }

    public BaseTransport(double x, double y, double maxSpeed, String name) {
        super(x, y);
        this.maxSpeed = maxSpeed;
       setName(name);
    }

    public BaseTransport() {
        super(0, 0);
    }

    public double getAvgSpeed(){
        return getMaxSpeed()/Math.sqrt(2);
    }

    public double getVolume_baggage() {
        return max_volume_baggage;
    }
    public void setVolume_baggage(double volume_baggage) {
        this.max_volume_baggage = volume_baggage;
    }

    public double getMax_weight_baggage() {
        return max_weight_baggage;
    }

    public void setMax_weight_baggage(double max_weight_baggage) {
        this.max_weight_baggage = max_weight_baggage;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }


    // Может ли транспорт взять заказ?
    public boolean hasSpace(Delivery delivery){
       return  hasSpace(delivery.getGoods());
    }
    //Перегуженный метод hasSpace
    public boolean hasSpace(ArrayList<Product> goods){

        // вес в машине
        double cur_volume = 0d;
        double cur_weight = 0d;
        for( Product a : products){
            cur_volume+=a.getVolume();
            cur_weight+=a.getWeight();
        }

        // взимаемый вес
        double i_volume = 0d;
        double i_weight = 0d;
        for( Product a : goods){
            i_volume+=a.getVolume();
            i_weight+=a.getWeight();
        }
        // Проверка на максимальный объем в транспорте
        // или
        // максимальной допустимой массы в транспорте
        if ((cur_volume+i_volume)>max_volume_baggage || (cur_weight+i_weight)>max_weight_baggage) return false;

        return true;
    }

    public PathWrapper FindPath(Vertex to) throws CloneNotSupportedException {
       return Main.map.FindPath(this, to);
    }
    public PathWrapper FindPath(BaseTransport from, Vertex to) throws CloneNotSupportedException {
        return Main.map.FindPath(from, to);
    }

    public void GetWaypoints(PathWrapper pathWrapper){
        List<Vertex> resultWaypoints = new ArrayList<>();
        List<Vertex> vertexList = pathWrapper.getPath();
        int size = vertexList.size();
        for (int i =1; i<size; i++){
            Vertex prev = vertexList.get(i-1);
            Vertex next = vertexList.get(i);

            Vector2D start = new Vector2D(prev);

            Vector2D direction = new Vector2D(next);
            direction.sub(new Vector2D(prev));


            int div = (int) ((int)(direction.length() / 5)/(getAvgSpeed()/240));

            direction.div(div);

            for( int j = 0; j<div;j++){
                Vector2D vector2D = new Vector2D(start);
                for (int k = 0; k<j; k++) {
                    vector2D.add(direction);
                }
                resultWaypoints.add(vector2D.convertToVertex());

            }


        }
        resultWaypoints.add(vertexList.get(vertexList.size()-1));
        this.resultWaypoints = resultWaypoints;
        System.out.println("Result = " + resultWaypoints);
//        for(Vertex s:pathWrapper.getPath()){
//            if (pathWrapper.getShortest_distance().get(s)==null){
//                System.out.println("Skipped element with"+ s);
//
//                continue;
//            }
//            time+=pathWrapper.getShortest_distance().get(s);
//        }
    }



    public double CountTime(PathWrapper pathWrapper){
        Double speed = this.getAvgSpeed();
        Double traffic = 0.0;//
        double time=0.0;
        List<Vertex> vertexList = pathWrapper.getPath();
        int size = vertexList.size();
        for (int i =0; i<size; i++){

                if (pathWrapper.getShortest_distance().get(vertexList.get(i))==null){
                    // TODO: try and catch
                    System.out.println("Skipped element with"+ vertexList.get(i));
                    Double length=Math.sqrt(Math.pow((vertexList.get(i-1).getX()-vertexList.get(i).getX()),2)+Math.pow((vertexList.get(i-1).getY()-vertexList.get(i).getY()),2));
                    Double quality = Quality_Road.good.getStatus();
                    // TODO: Walking speed
                    Double time2 = 0.38*length/(speed*(1-traffic)*(quality));
                    time+=time2;
                    continue;
                }else{
                    time+=pathWrapper.getShortest_distance().get(vertexList.get(i));
                }

        }
//        for(Vertex s:pathWrapper.getPath()){
//            if (pathWrapper.getShortest_distance().get(s)==null){
//                System.out.println("Skipped element with"+ s);
//
//                continue;
//            }
//            time+=pathWrapper.getShortest_distance().get(s);
//        }
        return time;
    }

    public boolean hasProducts(Delivery delivery){
        ArrayList<Product> deliveryGoods = delivery.getGoods();
        boolean hasProduct = true;
        for(Product product: deliveryGoods){
            if (!this.products.contains(product)){
                hasProduct = false;
                break;
            }

        }
        return hasProduct;
    }
        public boolean ShipProductsInCar(Delivery delivery) throws CloneNotSupportedException {
            Vector2D distance  = new Vector2D(Main.map.productPoint);
            distance.sub(new Vector2D(this));
            //if (distance.length() > 35) return false;

            if(hasSpace(delivery.getGoods())){ //Checking for space in Transport
                for (Product product : delivery.getGoods()){
                    if (!products.contains(product)){
                        products.add(product);
                    }


                }
            }
        return true;
    }

    public void UnShipProductsInCar(Delivery delivery) throws CloneNotSupportedException {
        Vector2D distance  = new Vector2D(Main.map.productPoint);
        distance.sub(new Vector2D(this));
        //if (distance.length() > 35) return false;
        //if(hasSpace(delivery.getGoods())){ //Checking for space in Transport
        //products.clear();
            for (Product product : delivery.getGoods()){
                if (products.contains(product)){
                    products.remove(product);
                }

            }
        //}

    }
    public PathWrapper getPathToDelivery(Delivery delivery) throws CloneNotSupportedException {
        try {
            boolean hasProducts = hasProducts(delivery);
            if (!hasProducts) {
                PathWrapper wrapper = FindPath(Main.map.productPoint);

                double x = this.getX();
                double y = this.getY();
                BaseTransport clone = clone();
                clone.setX(Main.map.productPoint.getX());
                clone.setY(Main.map.productPoint.getY());
                PathWrapper wrapper2 = clone.FindPath(delivery.getAddress());


                clone.setX(x);
                clone.setY(y);
                PathWrapper full_path = wrapper.MergePathsWrappers(wrapper2);
                System.out.println("wrapper2 = " + wrapper2.getPath());

               // System.out.println("full_path = " + full_path.getPath());
                return full_path;
            } else {
                PathWrapper wrapper = FindPath(delivery.getAddress());
                return wrapper;
            }

        }
        catch (Exception r){ return null; }
    }


    public Double getExecuteTime(Delivery delivery) throws CloneNotSupportedException {
        try {
            boolean hasProducts = hasProducts(delivery);
            if (!hasProducts) {
                PathWrapper wrapper = this.FindPath(Graph.productPoint);

                double x = this.getX();
                double y = this.getY();
                BaseTransport clone = clone();
                clone.setX(Graph.productPoint.getX());
                clone.setY(Graph.productPoint.getY());
                PathWrapper wrapper2 = clone.FindPath(delivery.getAddress());


                clone.setX(x);
                clone.setY(y);
                PathWrapper full_path = wrapper.MergePathsWrappers(wrapper2);
                System.out.println("wrapper2 = " + wrapper2.getPath());
                System.out.println("wrapper2.sd = " + wrapper2.getShortest_distance());
                System.out.println("wrapper.sd = " + wrapper.getShortest_distance());
                // System.out.println("full_path = " + full_path.getPath());
                System.out.println("Count" + CountTime(wrapper));
                return CountTime(wrapper2)+CountTime(wrapper);
            } else {
                PathWrapper wrapper = FindPath(delivery.getAddress());
                return CountTime(wrapper);
            }

        }
        catch (Exception r){
            //r.printStackTrace();
            return null; }

    }





    @Override
    public BaseTransport clone() throws CloneNotSupportedException {
        return (BaseTransport)super.clone();
    }

//    @Override
//    public String toString() {
//        return "BaseTransport{" +
//                "maxSpeed=" + maxSpeed +
//                ", products=" + products +
//                ", activeDelivery=" + activeDelivery +
//                ", max_volume_baggage=" + max_volume_baggage +
//                ", max_weight_baggage=" + max_weight_baggage +
//                ", Name='" + super.getName() + '\'' +
//                '}';
//    }

    @Override
    public String toString() {
        return getName();
    }

}
