package sample;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Класс обработчиков событий (нажатий на кнопку, перетаскивания)
public class EHandler {

  private Pane root;

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public void init(UI_Wrapper ui_wrapper) {

        EventHandler<MouseEvent> dragEvent = dragEvent1 -> {
                ui_wrapper.getCircle().startFullDrag();
                getRoot().setUserData(ui_wrapper); // Сохраняем объект-обертку элементов управления, чтобы из него достать окружность
                // и текст
        };
        ui_wrapper.getText().setOnDragDetected(dragEvent);
        ui_wrapper.getCircle().setOnDragDetected(dragEvent);

        getRoot().setOnMouseDragReleased(evt -> {
            (((UI_Wrapper)getRoot().getUserData()).getCircle()).setCenterX(evt.getX());
            (((UI_Wrapper)getRoot().getUserData()).getCircle()).setCenterY(evt.getY());
            (((UI_Wrapper)getRoot().getUserData()).getText()).setX(evt.getX());
            (((UI_Wrapper)getRoot().getUserData()).getText()).setY(evt.getY()-10);
        });

        EventHandler<MouseEvent> eventEventHandler = (e)->{
            StackPane secondaryLayout = new StackPane();
            Scene secondScene = new Scene(secondaryLayout, 450, 100);

            // New window (Stage)
            Stage newWindow = new Stage();
            try {
                newWindow.setTitle("Свойства точки " + ((Text) e.getSource()).getText());
            } catch (ClassCastException exp) {
                System.out.println("Свойства точки");
                newWindow.setTitle("Свойства точки " );

            }
            newWindow.centerOnScreen();
            newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            //newWindow.setX( 200);
            //newWindow.setY(100);

            newWindow.show();
        };

        ui_wrapper.getText().setOnMouseClicked(eventEventHandler);
        ui_wrapper.getCircle().setOnMouseClicked(eventEventHandler);


    }
}