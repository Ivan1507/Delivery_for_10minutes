package sample.Delivery;

import sample.LocalDateFormatted2;
import sample.Graphic.PointType;
import sample.MapLogic.Vertex;
import sample.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class DeliveryGenerator {
    private static int curInt = 0;
    private static HashSet<Vertex> nonRepeats = new HashSet<>();

    static public Delivery generate() {
        ArrayList<ArrayList<Product>> products = new ArrayList<>();
        ArrayList<Vertex> vertexes = new ArrayList<>();

        Vertex A2 = new Vertex(225, 96);
        A2.setName("Заказ 2");
        A2.setPointType(PointType.Triangle);
        products.add(new ArrayList<>() {{
            add(new Product("Pizza", 0.5, 30.0));
            add(new Product("Vegetables", 0.4, 10));
            add(new Product("Potato", 0.3, 15));
            add(new Product("Cheese", 0.7, 10));
        }});
        products.add(new ArrayList<>() {{
            add(new Product("Pizza", 0.5, 30.0));
        }});
        products.add(new ArrayList<>() {{
            add(new Product("Картошка", 0.5, 30.0));
        }});
        products.add(new ArrayList<>() {{
            add(new Product("Макароны", 0.5, 30.0));
        }});
        products.add(new ArrayList<>() {{
            add(new Product("Сок", 0.5, 30.0));
        }});
        products.add(new ArrayList<>() {{
            add(new Product("Вода", 0.5, 30.0));
        }});
        products.add(new ArrayList<>() {{
            add(new Product("Pizza", 0.5, 30.0));
            add(new Product("Kola", 0.4, 10));
            add(new Product("Pepsi", 0.3, 15));
            add(new Product("Cheese", 0.7, 10));
        }});
        vertexes.add(
                new Vertex(50, 125) {
                    {
                        setName("Заказ 13");
                        setPointType(PointType.Triangle);
                    }
                }
        );
        vertexes.add(new Vertex(150, 325) {
            {
                setName("Заказ 17");
                setPointType(PointType.Triangle);
            }
        });
        vertexes.add(new Vertex(230, 285) {
            {
                setName("Заказ 27");
                setPointType(PointType.Triangle);
            }
        });
        vertexes.add(new Vertex(450, 155) {
            {
                setName("Заказ 47");
                setPointType(PointType.Triangle);
            }
        });

        vertexes.add(new Vertex(340, 85) {
            {
                setName("Заказ 47");
                setPointType(PointType.Triangle);
            }
        });
//    int i, j;
//    i = new Random().nextInt(products.size() - 1);
//    j = new Random().nextInt(vertexes.size() - 1);
//    Delivery dev=new Delivery();
//    if (!nonRepeats.contains(vertexes.get(j))) {
//        nonRepeats.add(vertexes.get(j));
        return new Delivery(curInt++, "", DeliveryStatus.WAITING, new LocalDateFormatted2(LocalDateTime.now()), new LocalDateFormatted2(LocalDateTime.now().plusMinutes(15)), products.get(new Random().nextInt(products.size() - 1))
                , vertexes.get(new Random().nextInt(vertexes.size() - 1)));


    }
}
//Delivery(int id, String executor, String status, String time_start, String time_end, ArrayList<Product> goods, Vertex address)