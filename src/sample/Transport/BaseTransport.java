package sample.Transport;

import sample.MapLogic.Vertex;

public class BaseTransport extends Vertex {

    private double maxSpeed=120;
    public double getMaxSpeed(){
        return maxSpeed;
    }

    public double getAvgSpeed(){
        return getMaxSpeed()/Math.sqrt(2);
    }
}
