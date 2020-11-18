package sample.Transport;

import sample.Delivery.Delivery;
import sample.Main;
import sample.MapLogic.PathWrapper;
import sample.MapLogic.Vertex;
import sample.Product;
import sample.Vector2D;

import java.util.ArrayList;

// Базовый класс для описания всех видов транспортых средств
public class BaseTransport extends Vertex {

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
    public double CountTime(PathWrapper pathWrapper){

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
        public boolean ShipProductsInCar(Delivery delivery) throws CloneNotSupportedException {
            Vector2D distance  = new Vector2D(Main.map.productPoint);
            distance.sub(new Vector2D(this));
            if (distance.length() > 35) return false;

            if(hasSpace(delivery.getGoods())){ //Checking for space in Transport
                for (Product product : delivery.getGoods()){
                    if (!products.contains(product)){
                        products.add(product);
                    }


                }
            }
        return true;
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
                PathWrapper wrapper2 = FindPath(clone, delivery.getAddress());


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

    // Узнать время выполнения заказов для каждой машины
    public Double getExecuteTime(Delivery delivery) throws CloneNotSupportedException {


        PathWrapper wrapper = getPathToDelivery(delivery);

        return CountTime(wrapper);



    }

    @Override
    public BaseTransport clone() throws CloneNotSupportedException {
        return (BaseTransport)super.clone();
    }

    @Override
    public String toString() {
        return "BaseTransport{" +
                "maxSpeed=" + maxSpeed +
                ", products=" + products +
                ", activeDelivery=" + activeDelivery +
                ", max_volume_baggage=" + max_volume_baggage +
                ", max_weight_baggage=" + max_weight_baggage +
                ", Name='" + super.getName() + '\'' +
                '}';
    }
}
