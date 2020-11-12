package sample.Transport;

import javafx.scene.paint.Color;
import sample.Controllers.MapController;
import sample.Delivery.Delivery;
import sample.Kitchen;
import sample.Main;
import sample.MapLogic.PathWrapper;
import sample.MapLogic.Vertex;
import sample.Product;
import sample.Vector2D;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

// Базовый класс для описания всех видов транспортых средств
public class BaseTransport extends Vertex {

    private double maxSpeed=120;
    public ArrayList<Product> products=new ArrayList<>();
    private Delivery activeDelivery;
    private double max_volume_baggage = 100;
    private double max_weight_baggage = 100;
    private String Name;



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
        Name = name;
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

        ArrayList<Product> A  = delivery.getGoods();
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
        for( Product a : A){
            i_volume+=a.getVolume();
            i_weight+=a.getWeight();
        }
        // Проверка на максимальный объем в транспорте
        // или
        // максимальной допустимой массы в транспорте
        if ((cur_volume+i_volume)>max_volume_baggage || (cur_weight+i_weight)>max_weight_baggage) return false;

        return true;
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
    public double Count_time(PathWrapper pathWrapper){
        double time=0.0;
        for(Vertex s:pathWrapper.getPath()){
            time+=pathWrapper.getShortest_distance().get(s);
        }
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
    public PathWrapper MakeDelivery(Delivery delivery) throws CloneNotSupportedException {
        PathWrapper pt=new PathWrapper();
        if(products.size()==0) {
            pt = FindPath(Main.map.productPoint);
            if(hasSpace(delivery.getGoods())){//Checking for space in Transport
                products =delivery.getGoods();// Main.kitchen.getProducts_of_kitchen();
                System.out.println("Товары успешно погружены в транспорт"+products);
            }
        }

        PathWrapper pt2 = FindPath(delivery.getAddress());
        pt.MergePathsWrappers(pt2);
        System.out.println("pt= "+pt.getPath());
        return pt;
    }
    public void getExecuteTime(Delivery delivery) throws CloneNotSupportedException {
        try {
            boolean hasProducts = hasProducts(delivery);
            if (!hasProducts) {
                try {
                    // System.out.println("Main.map.productPoint = " + Main.map.productPoint);
                    PathWrapper wrapper = FindPath(Main.map.productPoint);


                    var clone = clone();
                    clone.setX(Main.map.productPoint.getX());
                    clone.setY(Main.map.productPoint.getY());
                    PathWrapper wrapper2 = FindPath(clone, delivery.getAddress());

                    wrapper.getPath().forEach((e) -> {
                        // System.out.println(e.getName());
                    });


                    wrapper2.getPath().forEach((e) -> {
                        //  System.out.println("Отсюда" + e.getName());
                    });
                    //Main.map.DrawPath(wrapper.getPath(), Color.rgb(255,25,25));
                    Main.map.DrawPath(wrapper.MergePathsWrappers(wrapper2).getPath());
                    System.out.println("Для машины " + Count_time(wrapper.MergePathsWrappers(wrapper2)));
                }
                catch (NullPointerException e) {
                    throw new Exception("Машина " + this.toString() + " не может построить маршрут");
                }

            } else {

                throw new Exception("Машина " + this.toString() + " не может построить маршрут");
            }

        }
        catch (Exception e){
            //System.out.println("Не построить маршрут!");
        }

    }

    @Override
    public BaseTransport clone() throws CloneNotSupportedException {
        return (BaseTransport)super.clone();
    }
}
