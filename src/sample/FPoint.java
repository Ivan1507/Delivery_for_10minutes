package sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;



public class FPoint extends Vertex{
    private PointType pointType;
    public FPoint() {
        super();
    }

    public FPoint(double x, double y) {
        super(x, y);
        setName("Undefined #");
    }


    // Добавляет на Pane элементы управления (точки)
    @Override
    public void AddToGUI(Pane rt) {
        //this.setRoot(rt);
        UI_Wrapper wrapper = new UI_Wrapper(rt);
        wrapper.Init(this);

        DragObject dragObject =  new DragObject();
        dragObject.setRoot(rt);
        dragObject.init( wrapper );

    }




    }
