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

    @Override
    public void AddToGUI(Pane rt) {
        this.setRoot(rt);

        Circle circle = new Circle(this.getX(),this.getY(),3);
        circle.setStroke(Color.BLACK);
        //circle.setTranslateX( this.X);
        //circle.setTranslateY(this.Y);

        Text Text1 = new Text(this.getX(),this.getY()-10,this.getName());

       // Text1.setOnMouseClicked(OpenSettings());
        //circle.setOnDragDropped(OnDragged());
       // circle.setOnDragDetected(OnDragStarted(circle));

        DragObject dragObject = DragObject.NewObject();
        dragObject.setSource(circle);
        dragObject.setText( Text1 ) ;
        dragObject.setTarget(rt);
        dragObject.init();
//        circle.setOnDragDetected(evt -> {
//            circle.startFullDrag();
//
//        });
//
//        rt.setOnMouseDragReleased(evt -> {
//            System.out.println("over");
//            circle.setCenterX(evt.getX());
//            circle.setCenterY(evt.getY());
//        });

        //Text1.setTextAlignment(TextAlignment.JUSTIFY);
       getRoot().getChildren().addAll(circle,Text1);
    }


    public EventHandler<MouseEvent> OpenSettings(){

        return (event)->{
            StackPane secondaryLayout = new StackPane();
            Scene secondScene = new Scene(secondaryLayout, 450, 100);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Свойства точки " + ((Text)event.getSource()).getText());
            newWindow.centerOnScreen();
            newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            //newWindow.setX( 200);
            //newWindow.setY(100);

            newWindow.show();
        };
    }


    }
