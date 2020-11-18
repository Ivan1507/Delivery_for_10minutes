package sample.Delivery;

import javafx.collections.ObservableList;
import sample.LocalDateFormatted2;
import sample.Transport.BaseTransport;
import sample.Transport.TransportDepartment;

public class DeliveryLogic {
    public ObservableList<Delivery> DeliveryData; // Список заказов
    private TransportDepartment department = new TransportDepartment(); // Список транспорта
    public void add_delivery(Delivery e) throws CloneNotSupportedException {
        DeliveryData.add(e);
        System.out.println("Заказ успешно добавлен из таблицы!");

    }
    public BaseTransport getBestExecutor(Delivery e) throws CloneNotSupportedException {

            BaseTransport executor = null;
            Double time=-1d;

            for (BaseTransport baseTransport : department.getVehicles()) {
                if (baseTransport.getActiveDelivery() != null) continue;
                //if (baseTransport.getExecuteTime(e) == null) continue;
                if (time == -1d) {
                    time = baseTransport.getExecuteTime(e);
                    //System.out.println("time = " + time);
                    executor = baseTransport;
                }
                if (time > baseTransport.getExecuteTime(e)){
                    time = baseTransport.getExecuteTime(e);
                    //System.out.println("time = " + time);
                    executor = baseTransport;
                }
            }
        System.out.println("executor = " + executor + " and time = " + time);
        return executor;
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
    public void change_time_end(Integer id, LocalDateFormatted2 ld){
        for(Delivery del:DeliveryData){
            if(del.getId()==id) {
                del.setTime_end(ld);
                break;
            }
        }
    }

    public ObservableList<Delivery> getDeliveryData() {
        return DeliveryData;
    }

    public TransportDepartment getDepartment() {
        return department;
    }
}
