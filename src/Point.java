

public class Point {
    private String Name;
    protected double X;
    protected double Y;
    public Point(String name,double x, double y){
        Name=name;
        X=x;
        Y=y;
    }
    public static Point getInstance(String n,double x, double y) {
        return new Point(n,x,y);
    }

    @Override
    public String toString() {
        return "Point:" +
                "Name=" + Name +
                "(" + X +
                ";" + Y+')';
    }
}
