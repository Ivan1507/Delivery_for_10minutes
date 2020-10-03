import javax.swing.text.MutableAttributeSet;

public class Road extends Point{
    private Point From;
    private Point To;
    private final double Distance;

    public Road(Point from,Point to) {
        super("",0,0);
        From=from;
        To=to;
        Distance=Math.sqrt(Math.pow(to.X-from.X,2)+Math.pow(to.Y-from.Y,2));
    }

    @Override
    public String toString() {
        return "Road:" +
                "From=" + From +
                " To=" + To +
                ", Distance=" +String.format("%.2f",Distance)+" km";
    }
}

