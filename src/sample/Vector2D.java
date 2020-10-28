package sample;

import sample.MapLogic.Vertex;

public class Vector2D {

    private double X;
    private double Y;
    private final int step = 2500;
    public Vector2D(double x, double y) {
        X = x;
        Y = y;
    }

    public Vector2D(Vertex start) {
        X = start.getX();
        Y = start.getY();
    }
    public Vector2D(Vector2D start) {
        X = start.getX();
        Y = start.getY();
    }


    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }
    public double length() {
        return Math.sqrt(Math.pow(getX() , 2) + Math.pow(getY(), 2));
    }
    public void sub(Vector2D another){
        setX( getX() - another.getX());
        setY( getY() - another.getY());
    }

    public void add(Vector2D another){
        setX( getX() + another.getX());
        setY( getY() + another.getY());
    }

    public void div(double k){
        setX( getX() / k);
        setY( getY() / k);
    }
    public void inc(double val, Vector2D normalize){
        setX( getX() + val*normalize.getX());
        setY( getY() + val*normalize.getY());
    }

    public int normalize(){
         setX( getX()/step);
         setY( getY()/step);
         return step;
    }
    public double dot( Vector2D v2){
        return this.getX() * v2.getX() + v2.getY()*this.getY();
    }
    public Vertex convertToVertex(){
        return new Vertex(getX(),getY());
    }

    public double getCosBetweenVectors(Vector2D vector2D){
        return(vector2D.dot(this) / ((vector2D.length()) * this.length()));
    }
    public  double getAngles(Vector2D vector2D){
        return 180*Math.acos( getCosBetweenVectors(vector2D))/Math.PI;
    }
    @Override
    public String toString() {
        return "Vector2D{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }


    public Vector2D getDirection() {
        Vector2D normalized = new Vector2D(this);
        normalized.div( normalized.length() );
        return normalized;
    }
}
