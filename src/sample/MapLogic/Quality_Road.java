package sample.MapLogic;

import javafx.scene.paint.Color;

public enum Quality_Road {
    very_bad(0.65,Color.web("#e32c22")),
    bad(0.7,Color.web("#e37f22")),
    average(0.78,Color.web("#44751b")),
    good(0.94,Color.web("#9df257"));

    private double quality;
    private Color color;

    Quality_Road( double q, Color c){

        quality=q;
        color=c;
    }
    Quality_Road(){

    }

    public double getStatus() {
        return quality;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Quality_Road{" +
                "quality=" + quality +
                ", color=" + color +
                '}';
    }
}


// q = 0.65

//