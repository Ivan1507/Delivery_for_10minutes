package sample.MapLogic.Delivery;

import sample.MapLogic.Vertex;
import sample.Product;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

// Класс для заполнения записей в таблице "Заказы"
public class Delivery implements Serializable {

    private int id;
    private String executor; // Исполнитель
    private DeliveryStatus status; // Статус заказа
    private LocalDateTime time_start; // Начало
    private LocalDateTime time_end; // Конец
    private ArrayList<Product> goods; // Список товаров
    private Vertex address; // Адрес, куда нужно доставлять заказ

    public Delivery(int id , String executor, DeliveryStatus status, LocalDateTime time_start, LocalDateTime time_end, ArrayList<Product> goods, Vertex address) {
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

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public LocalDateTime getTime_start() {
        return time_start;
    }

    public void setTime_start(LocalDateTime time_start) {
        this.time_start = time_start;
    }

    public LocalDateTime getTime_end() {
        return time_end;
    }

    public void setTime_end(LocalDateTime time_end) {
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
}
