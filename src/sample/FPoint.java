package sample;

import javafx.scene.layout.Pane;


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

        EHandler eHandler =  new EHandler();
        eHandler.setRoot(rt);
        eHandler.init( wrapper );

    }




    }
