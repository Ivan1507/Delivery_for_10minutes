package sample.MapLogic;

import javafx.scene.layout.Pane;
import sample.MapLogic.Graphic.PointType;
import sample.MapLogic.Graphic.UI_Wrapper;

import java.io.*;
import java.util.Objects;

// Вершина
public class Vertex implements Serializable {
   private double X;
   private double Y;
   private String Name;
   private PointType pointType; // Какая будет иконка на карте
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
    }

    public void setY(double y) {
        Y = y;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public void setPointType(PointType pointType) {
        this.pointType = pointType;
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
        return pointType;
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
        wrapper.setPane(rt);
        wrapper.Init(this);
    }
    public void setPos( double x, double y){
       X=x;
       Y=y;
    }
    public void setName( String name){
       Name = name;
    }
    public String getName(){
       return Name;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "X=" + X +
                ", Y=" + Y +
                ", Name='" + Name + '\'' +
                '}';
    }

    public static double dot(Vertex v1, Vertex v2){
        return v1.getX() * v2.getX() + v2.getY()*v1.getY();
    }
//    public void SaveToFile(String path) throws IOException {
//        try(FileWriter fw=new FileWriter(path,true)){
//            fw.append(this.toString());
//        }catch(IOException e){
//            System.out.println(e.getMessage());
//        }
//    }
//    public void getFromFile(String path){
//        try(FileReader fd=new FileReader(path)){
//            BufferedReader br=new BufferedReader(new FileReader(path));
//            String temp="";
//            String key="";
//            String value="";
//            boolean s=true;
//            int i;
//            while ((temp = br.readLine()) != "" & temp != null) {
//                if (temp.contains("Vertex{")) {
//                    temp=br.readLine();
//                    s= true;
//                } else if (temp.length() == 0) {
//                    s= false;
//                }
//
//                if (s) {
//                    key = temp.substring(0, temp.indexOf('='));
//                    value = temp.substring(temp.indexOf('=') +1);
//
//                    if (!key.isEmpty() && !value.isEmpty()) {
//                        switch (key) {
//                            case "X"->this.setX(Double.parseDouble(value));
//                            case "Y"->this.setY(Double.parseDouble(value));
//                            case "Name"->this.setName(value);
//                        }
//                    }
//                }
//            }
//        }catch(Exception ex){
//            System.out.println(ex.getMessage());
//        }
//    }
}
