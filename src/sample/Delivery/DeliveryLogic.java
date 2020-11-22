package sample.Delivery;

import javafx.collections.ObservableList;
import sample.LocalDateFormatted2;
import sample.Main;
import sample.Transport.BaseTransport;
import sample.Transport.TransportDepartment;

import java.util.Timer;
import java.util.TimerTask;

public class DeliveryLogic {
    public ObservableList<Delivery> DeliveryData; // Список заказов
    private TransportDepartment department = new TransportDepartment(); // Список транспорта
    public void add_delivery(Delivery e) throws CloneNotSupportedException {
        DeliveryData.add(e);
        System.out.println("Заказ успешно добавлен из таблицы!");

    }
    public BaseTransport getBestExecutor(Delivery e) throws CloneNotSupportedException {

            BaseTransport executor = null;
            Double time=null;

            for (BaseTransport baseTransport : department.getVehicles()) {
                    if (baseTransport.getActiveDelivery() != null) continue;

                //if (baseTransport.getExecuteTime(e) == null) continue;
                if (baseTransport.getExecuteTime(e) == null){continue;}
                if (time == null) {
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
    public void TakeDelivery( Delivery E ) {


        for (Delivery e : DeliveryData) {
            System.out.println("e = " + e);
            if (e.getAddress().getName() != "Заказ 47") continue;
            try {
                //System.out.println("Start");
                BaseTransport t;
                t = (Main.deliveryLogic.getBestExecutor(e));
                System.out.println("t = " + t);
                //if (t == null) throw new NullPointerException();
                System.out.println(t + " берет заказ " + e);
                Main.map.DrawPath(t.getPathToDelivery(e).getPath());
                //System.out.println("End");
            } catch (NullPointerException | CloneNotSupportedException tt) {
                tt.printStackTrace();
            }


        }
    }

    // Обновляет местоположение машин
    public void UpdateLc(){
        TimerTask task = new TimerTask() {
            public void run() {
                for (BaseTransport transport:department.getVehicles() ){
                    if (transport == null){continue;}
                    if (transport.resultWaypoints==null){continue;}
                    if (transport.resultWaypoints.isEmpty()){continue;}
                    if ( transport.indWaypoint  >= transport.resultWaypoints.size()){continue;}
                    transport.setX( transport.resultWaypoints.get(transport.indWaypoint).getX());
                    transport.setY( transport.resultWaypoints.get(transport.indWaypoint).getY());
                  transport.indWaypoint++;
                }

                UpdateLc();
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 100L;
        timer.schedule(task, delay);
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
