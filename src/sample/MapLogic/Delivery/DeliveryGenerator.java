package sample.MapLogic.Delivery;

import sample.MapLogic.Graphic.PointType;
import sample.MapLogic.Vertex;
import sample.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class DeliveryGenerator {
    private static int curInt = 0;


static public Delivery generate(){
    ArrayList<ArrayList<Product>> products=new ArrayList<>();
    ArrayList<Vertex> vertexes=new ArrayList<>();
    Vertex A2 = new Vertex(225,96);
    A2.setName("Заказ 2");
    A2.setPointType(PointType.Triangle);
       products.add(new ArrayList<>(){{
           add(new Product("Pizza", 0.5, 30.0));
           add(new Product("Vegetables",0.4,10));
           add(new Product("Potato",0.3,15));
           add(new Product("Cheese",0.7,10));
       }});
    products.add(new ArrayList<>(){{
        add(new Product("Pizza", 0.5, 30.0));
        add(new Product("Kola",0.4,10));
        add(new Product("Pepsi",0.3,15));
        add(new Product("Cheese",0.7,10));
    }});
    vertexes.add(
            new Vertex(50,125){{
                setName("Заказ 13");
            }
    }
    );
    vertexes.add(   new Vertex(150,325){{
        setName("Заказ 17");
    }
    });
    vertexes.add(   new Vertex(250,255){{
        setName("Заказ 27");
    }
    });

    return new Delivery(curInt++, "",DeliveryStatus.WAITING, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),products.get(0)
,vertexes.get(1));
}
}
//Delivery(int id, String executor, String status, String time_start, String time_end, ArrayList<Product> goods, Vertex address)