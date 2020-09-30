package sample;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Objects;

// Вершина
public class Vertex {
   private double X;
   private double Y;
   private String Name;
   private ArrayList<Edge> Edges = new ArrayList<>();
   public Vertex(double x, double y){
        X=x;
        Y=y;
}
   private Pane root;

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
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

    public void addUI(Pane rt){
        root = rt;
        Circle circle = new Circle(this.X,this.Y,3);
        circle.setStroke(Color.BLACK);
        //circle.setTranslateX( this.X);
        //circle.setTranslateY(this.Y);

        Text circleText = new Text(this.X,this.Y-10,this.getName());
        //circleText.setTranslateX(this.X);
        //circleText.setTranslateY(this.Y);
        circleText.setTextAlignment(TextAlignment.JUSTIFY);
        rt.getChildren().addAll(circle,circleText);
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
    public void parseNames(){

       if (Edges.size()>0) {
           for( Edge S : Edges) {
               Vertex Point = S.getAnotherPoint(this);
               System.out.println(Name + " имеет свзяь с " + Point.getName());
               Point.parseNames();
           }


       }
       else
       {
           System.out.println("Точка " + Name + " уже не имеет связей");
       }
    }
    public void connectTo( Vertex To){
        //double Distance = Math.sqrt(Math.pow(this.getX()-To.getX(),2) + Math.pow(this.getY() - To.getY(),2));
       Edge A =  Edge.ConnectVertexes(this,To,false);
       Edges.add( A );

       try {
           Line line = new Line(this.getX(), this.getY(), To.getX(), To.getY());
           line.setStroke(Color.RED);
           root.getChildren().add(line);

         //  Text CenterDistance = new Text((line.getEndX()+line.getStartX())/2,(line.getEndY()+line.getStartY())/2,String.valueOf(Math.round(Distance)));
         //  root.getChildren().add(CenterDistance);
       }
       catch (NullPointerException nptr){}
    }

}
