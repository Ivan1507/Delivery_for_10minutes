package sample.MapLogic.Delivery;

import sample.MapLogic.Vertex;

import java.io.Serializable;
// Класс для заполнения записей в таблице "Заказы"
public class Delivery implements Serializable {

    private int id;
    private String executor; // Исполнитель
    private String status; // Статус заказа
    private String time_start; // Начало
    private String time_end; // Конец
    private String goods; // Список товаров
    private Vertex address; // Адрес, куда нужно доставлять заказ

    public Delivery(int id, String executor, String status, String time_start, String time_end, String goods, Vertex address) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public Vertex getAddress() {
        return address;
    }

    public void setAddress(Vertex address) {
        this.address = address;
    }
}