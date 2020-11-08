package sample.Transport;

import sample.MapLogic.Vertex;
import sample.Product;

import java.util.ArrayList;

// Базовый класс для описания всех видов транспортых средств
public class BaseTransport extends Vertex {

    private double maxSpeed=120;
    private ArrayList<Product> products;
    private double max_volume_baggage = 100;
    private double max_weight_baggage = 100;
    public double getMaxSpeed(){
        return maxSpeed;
    }
    public BaseTransport(double x, double y) {
        super(x, y);
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

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public boolean hasSpace(ArrayList<Product> A){
        double cur_volume = 0d;
        double cur_weight = 0d;
        for( Product a : products){
            cur_volume+=a.getVolume();
            cur_weight+=a.getWeight();
        }
        if (cur_volume>max_volume_baggage || cur_weight>max_weight_baggage) return false;

        return true;
    }

    @Override
    public BaseTransport clone() throws CloneNotSupportedException {
        return (BaseTransport)super.clone();
    }
}
