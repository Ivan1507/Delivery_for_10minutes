package sample.MapLogic;

import javafx.scene.paint.Color;
// Качество дороги
// Первый параметр - число от 0 до 1
// 0 - ужасная дорога
// 1 - наилучшая дорога
// Второй параметр - цвет дороги в формате HEX

public enum Quality_Road {
    Abstract_bad(0.2,Color.web("#e32c22")),
    very_bad(0.65,Color.web("#e32c22")),
    bad(0.7,Color.web("#e37f22")),
    average(0.78,Color.web("#44751b")),
    good(0.94,Color.web("#9df257")),
    Perfect( 1,Color.web("#9df257"));


    private final double quality;
    private final Color color;

    Quality_Road(double q, Color c){
        quality=q;
        color=c;
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
