package sample;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class DragObject {


    private Circle source;
    private Pane target;
    private Text Text1;

    public Text getText() {
        return Text1;
    }

    public void setText(Text text1) {
        Text1 = text1;
    }

    public Circle getSource() {
        return source;
    }

    public void setSource(Circle source) {
        this.source = source;
    }

    public Pane getTarget() {
        return target;
    }

    public void setTarget(Pane target) {
        this.target = target;
    }

    static public DragObject NewObject() {
        return new DragObject();
    }

    public void init() {

        getSource().setOnDragDetected(evt -> {
            source.startFullDrag();

        });

        getTarget().setOnMouseDragReleased(evt -> {
           // System.out.println("over");
            (source).setCenterX(evt.getX());
            ( source).setCenterY(evt.getY());

            getText().setX(evt.getX());
            getText().setY(evt.getY()-10);

        });

    }
}