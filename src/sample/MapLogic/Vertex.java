package sample.MapLogic;

import javafx.scene.layout.Pane;
import sample.Graphic.PointType;
import sample.Graphic.UI_Wrapper;

import java.io.*;
import java.util.Objects;

// Вершина
public class Vertex implements Serializable,Cloneable {
   private double X;
   private double Y;
   private String Name; // Название вершины
   private PointType PointType; // Какая будет иконка на карте
   private boolean isSpecial = false; // Тип вершины: true - заказ, false - все остальное

    @Override
    public Vertex clone() throws CloneNotSupportedException {
        return (Vertex)super.clone();
    }

   private UI_Wrapper wrapper = new UI_Wrapper(); // Объект для вырисовки точек
   public Vertex(double x, double y){
       X=x;
       Y=y;
    }
   private transient Pane root;

    public Vertex() {

    }

    public void setX(double x) {
        X = x;
        wrapper.changes(x,this.getY(),root,this);
    }

    public void setY(double y) {
        Y = y;
        System.out.println("root = " + root);
        wrapper.changes(this.getX(),y,root,this);
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public void setPointType(PointType pointType) {
        this.PointType = pointType;
    }


    public Pane getRoot() {
        return root;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public PointType getPointType() {
        return PointType;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Double.compare(vertex.X, X) == 0 &&
                Double.compare(vertex.Y, Y) == 0 &&
                Objects.equals(Name, vertex.Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y, Name);
    }

    public void placeTo(Pane rt){
        setRoot(rt);
        wrapper.setPane(rt);

        wrapper.Init(this);

    }

    public void setName( String name){
       this.Name = name;
    }
    public String getName(){
       return Name;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "X=" + X +
                ", Y=" + Y +
                ", Name='" + getName() + '\'' +
                '}';
    }

}
