package sample.Transport;

import sample.MapLogic.Vertex;
// Базовый класс для описания всех видов транспортых средств
public class BaseTransport extends Vertex implements Cloneable {

    private double maxSpeed=120;
    public double getMaxSpeed(){
        return maxSpeed;
    }

    public double getAvgSpeed(){
        return getMaxSpeed()/Math.sqrt(2);
    }

    public BaseTransport(double x, double y) {
        super(x, y);
    }
    public BaseTransport() {
        super(0, 0);
    }

    @Override
    public BaseTransport clone() throws CloneNotSupportedException {
        return (BaseTransport) super.clone();
    }
}
