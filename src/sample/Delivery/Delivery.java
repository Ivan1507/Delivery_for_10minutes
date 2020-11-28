package sample.Delivery;

import sample.LocalDateFormatted2;
import sample.MapLogic.Vertex;
import sample.Product;
import sample.Transport.BaseTransport;

import java.io.Serializable;
import java.util.ArrayList;

// Класс для заполнения записей в таблице "Заказы"
public class Delivery implements Serializable,Cloneable {

    private int id;
    private BaseTransport executor; // Исполнитель
    private DeliveryStatus status; // Статус заказа
    private LocalDateFormatted2 time_start; // Начало
    private LocalDateFormatted2 time_end; // Конец
    private ArrayList<Product> goods; // Список товаров
    private Vertex address; // Адрес, куда нужно доставлять заказ

    public Delivery(int id , BaseTransport executor, DeliveryStatus status, LocalDateFormatted2 time_start, LocalDateFormatted2 time_end, ArrayList<Product> goods, Vertex address) {
        this.id = id;
        this.executor = executor;
        this.status = status;
        this.time_start = time_start;
        this.time_end = time_end;
        this.goods = goods;
        this.address = address;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BaseTransport getExecutor() {
        return executor;
    }

    public void setExecutor(BaseTransport executor) {
        this.executor = executor;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public LocalDateFormatted2 getTime_start() {
        return time_start;
    }

    public void setTime_start(LocalDateFormatted2 time_start) {
        this.time_start = time_start;
    }

    public LocalDateFormatted2 getTime_end() {
        return time_end;
    }

    public void setTime_end(LocalDateFormatted2 time_end) {
        this.time_end = time_end;
    }

    public ArrayList<Product> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<Product> goods) {
        this.goods = goods;
    }

    public Vertex getAddress() {
        return address;
    }

    public void setAddress(Vertex address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", executor='" + executor.getName() + '\'' +
                ", status=" + status +
                ", time_start=" + time_start +
                ", time_end=" + time_end +
                ", goods=" + goods +
                ", address=" + address +
                '}';
    }

}
