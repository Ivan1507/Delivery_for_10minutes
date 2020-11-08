package sample.MapLogic.Delivery;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class DeliveryLogic {
    public ObservableList<Delivery> DeliveryData;

    public void add_delivery(Delivery e){
        DeliveryData.add(e);
        System.out.println("Заказ успешно добавлен из таблицы!");
    }
    public void remove_by_key(Integer id){

        for(int i=0;i<DeliveryData.size();i++){
            if(DeliveryData.get(i).getId()==id) {
                DeliveryData.remove(i);
                break;
            }
        }
        System.out.println("Заказ успешно удален из таблицы!");
    }
    public void change_status(Integer id,DeliveryStatus status){
        for(Delivery del:DeliveryData){
            if(del.getId()==id) {
                del.setStatus(status);
                break;
            }
        }
    }
    public void change_executor(Integer id, String executor){
        for(Delivery del:DeliveryData){
            if(del.getId()==id) {
                del.setExecutor(executor);
                break;
            }
        }
    }
    public void change_time_end(Integer id,LocalDateTime ld){
        for(Delivery del:DeliveryData){
            if(del.getId()==id) {
                del.setTime_end(ld);
                break;
            }
        }
    }
}
