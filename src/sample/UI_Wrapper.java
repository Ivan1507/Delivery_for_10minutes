package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class UI_Wrapper {
    private Circle circle ;

    private Text text;
    private Pane pane;

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Pane getPane() {
        return pane;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    UI_Wrapper(Pane root){
        pane = root;
    }

    public Circle getCircle() {
        return circle;
    }

    public Text getText() {
        return text;
    }

    public void Init(FPoint self){

        Circle circle = new Circle(self.getX(),self.getY(),3);
        circle.setStroke(Color.BLACK);
        setCircle(circle);
        //circle.setTranslateX( this.X);
        //circle.setTranslateY(this.Y);

        Text Text1 = new Text(self.getX(),self.getY()-10,self.getName());
        setText(Text1);
        pane.getChildren().addAll(circle,Text1);
    }


}
