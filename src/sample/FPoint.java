package sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.event.InputEvent;

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

        Text circleText = new Text(this.getX(),this.getY()-10,this.getName());

        circleText.setOnMouseClicked(OpenSettings());
        //circle.setOnDragDropped(OnDragged());
        circle.setOnDragDetected(OnDragStarted());

        circle.setOnDragDone(OnDragOver());
        //circle.setOnDragDone(OnDragged());

        circleText.setTextAlignment(TextAlignment.JUSTIFY);
        rt.getChildren().addAll(circle,circleText);
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

    public EventHandler<DragEvent> OnDragged(){

        return (dragEvent -> {

            var source = dragEvent.getSource().getClass();
            System.out.println("1234");
            System.out.println(source);
            dragEvent.setDropCompleted(true);
            dragEvent.consume();
        });
    }
    public EventHandler<MouseEvent> OnDragStarted(){
        return mouseEvent -> {
            Dragboard db = ((Circle)mouseEvent.getSource()).startDragAndDrop(TransferMode.MOVE);

            /* Put a string on a dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf((Circle)((Circle) mouseEvent.getSource()).getUserData()));
            db.setContent(content);

            mouseEvent.consume();
        };
    }

    public EventHandler<DragEvent> OnDragOver(){
        return dragEvent -> {

          //  if (dragEvent.getGestureSource() != (Circle)( dragEvent.getSource()) &&
          //          dragEvent.getDragboard().hasString()) {
                /* allow for both copying and moving, whatever user chooses */
                //dragEvent.acceptTransferModes(TransferMode.MOVE);
                //dragEvent.setDropCompleted(true);
          //  }
            System.out.println("2");
            System.out.println(dragEvent.getScreenX());
            System.out.println(dragEvent.getX());
           // System.out.println(dragEvent.getScreenX());

            ((Circle) dragEvent.getSource()).setCenterX(dragEvent.getX());
            ((Circle) dragEvent.getSource()).setCenterY(dragEvent.getY());
            dragEvent.consume();


        };
    }


    }
