package sample;

public class FPoint extends Vertex{
    private PointType pointType;
    public FPoint() {
        super();
    }

    public FPoint(double x, double y) {
        super(x, y);
        setName("Undefined #");
    }
}
