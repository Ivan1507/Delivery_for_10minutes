package sample;

import javafx.scene.layout.Pane;

import java.util.HashSet;


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

        UI_Wrapper wrapper = new UI_Wrapper(rt); // Извлечь поле в класс
        wrapper.Init(this);
        HashSet<Integer> s = new HashSet<>();
        s.add(2);
        EHandler eHandler =  new EHandler();
        eHandler.setRoot(rt);
        eHandler.init( wrapper );

    }




    }
