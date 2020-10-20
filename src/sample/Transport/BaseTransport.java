package sample.Transport;

import sample.MapLogic.Vertex;
// Базовый класс для описания всех видов транспортых средств
public class BaseTransport extends Vertex {

    private double maxSpeed=120;
    public double getMaxSpeed(){
        return maxSpeed;
    }

    public double getAvgSpeed(){
        return getMaxSpeed()/Math.sqrt(2);
    }

}
