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
    private String Name;



    public double getMaxSpeed(){
        return maxSpeed;
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

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }


    // Может ли транспорт взять заказ?
    public boolean hasSpace(ArrayList<Product> A){
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

    @Override
    public BaseTransport clone() throws CloneNotSupportedException {
        return (BaseTransport)super.clone();
    }
}
