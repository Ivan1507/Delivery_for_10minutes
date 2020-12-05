package sample.Graphic;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import sample.MapLogic.Graph;
import sample.MapLogic.Vertex;

import java.util.Timer;
import java.util.TimerTask;

// Обертка над элементами управлени точки
public class UI_Wrapper {


    private Pane pane;


    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;

    }

    public UI_Wrapper(Pane root){
        super();
        pane = root;
    }

    public UI_Wrapper(){
        super();
    }
    public void changes(double x, double y, Pane pane, Vertex source){
        if (pane == null) return;

        for( Object o : pane.getChildren()) {

            if (o instanceof Text && ((Text) o).getUserData().equals(source)) {
                ((Text) o).setX(x-15);
                ((Text) o).setY(y-10);

            }

            if (o instanceof Circle  && ((Circle) o).getUserData().equals(source)) {
                ((Circle) o).setCenterX(x);
                ((Circle) o).setCenterY(y);

            }
//            if (o instanceof Text && !(o instanceof Line)) {
//                ((Text) pane.getChildren().get(10)).setX(x);
//                ((Text) pane.getChildren().get(10)).setY(y);
//            }
        }
    }

    public void delete(Pane pane, Vertex source){
        if (pane == null)return;


        for( Object o : pane.getChildren()) {

            if (o instanceof Text && ((Text) o).getUserData().equals(source)) {
                ((Text) o).setOpacity(0);
                //pane.getChildren().remove(o);
            }

            if (o instanceof Circle && ((Circle) o).getUserData().equals(source)) {
                ((Circle) o).setOpacity(0);
                //pane.getChildren().remove(o);
            }

            if (o instanceof Polygon && ((Polygon) o).getUserData().equals(source)) {
                ((Polygon) o).setOpacity(0);
                //pane.getChildren().remove(o);
            }

        }
    }
    public void Init(Vertex self){

        switch (self.getPointType()) {
            case Triangle:
                Polygon polygon = new Polygon();
                polygon.getPoints().addAll(-10.0, -10.0,
                        0.0, 10.0,
                        10.0, -10.0);
                polygon.setTranslateX(self.getX());
                polygon.setTranslateY(self.getY());
                polygon.setFill(Color.BLACK);
                polygon.setStroke(Color.WHEAT);
                polygon.setStrokeWidth(2);
                polygon.setUserData(self);
                pane.getChildren().addAll( polygon );

                break;
            case Square:
                Rectangle rectangle = new Rectangle(25,25,Color.RED);
                rectangle.setX(self.getX()-12.5);
                rectangle.setY(self.getY()-12.5);
                rectangle.setUserData(self);
                pane.getChildren().addAll(rectangle );
                break;
            case TwoCricle:
                Circle circle = new Circle();
                circle.setRadius(8);
                circle.setCenterX(self.getX());
                circle.setCenterY(self.getY());
                circle.setStroke(Color.BLUE);
                circle.setFill(Color.WHITE);
                circle.setUserData(self);
                Circle mark = new Circle();
                mark.setRadius(4);
                mark.setCenterX(self.getX());
                mark.setCenterY(self.getY());
                mark.setFill(Color.BLACK);
                mark.toFront();
                mark.setUserData(self);
                circle.toFront();
                getPane().getChildren().add(circle );
                getPane().getChildren().addAll(mark );

                break;

            case Circle:
                Circle circle2 = new Circle();
                circle2.setRadius(8);
                circle2.setCenterX(self.getX());
                circle2.setCenterY(self.getY());
                circle2.setStroke(Color.BLUE);
                circle2.setUserData(self);
                circle2.setFill(Color.WHITE);

                getPane().getChildren().addAll(circle2 );
                break;
        }

        Text Text1 = new Text(self.getX()-10,self.getY()-15,self.getName());
        Text1.setTextAlignment(TextAlignment.CENTER);
        Text1.setUserData(self);
        Text1.setText(self.getName());

        pane.getChildren().addAll(Text1);
    }


}
