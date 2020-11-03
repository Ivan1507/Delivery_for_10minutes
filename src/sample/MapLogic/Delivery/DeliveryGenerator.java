package sample.MapLogic.Delivery;

import sample.MapLogic.Graphic.PointType;
import sample.MapLogic.Vertex;
import sample.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DeliveryGenerator {
    private static int curInt = 0;


public Delivery generate(){
    Vertex A2 = new Vertex(225,96);
    A2.setName("Заказ 2");
    A2.setPointType(PointType.Triangle);

    return new Delivery(curInt++, "",DeliveryStatus.WAITING, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),new ArrayList<Product>(){{
        add(new Product("Картошка",1,1));
    }},A2);
}
}
//Delivery(int id, String executor, String status, String time_start, String time_end, ArrayList<Product> goods, Vertex address)