package sample.Delivery;

import javafx.collections.ObservableList;
import sample.Config;
import sample.Controllers.ChartController;
import sample.Controllers.DeliveryController;
import sample.LocalDateFormatted2;
import sample.Main;
import sample.MapLogic.Graph;
import sample.Transport.BaseTransport;
import sample.Transport.TransportDepartment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Timer;
import java.util.TimerTask;

public class DeliveryLogic {
    public ObservableList<Delivery> DeliveryData; // Список заказов
    public Timer timer = new Timer("Timer");
    private TransportDepartment department = new TransportDepartment(); // Список транспорта
    public void add_delivery(Delivery e) throws CloneNotSupportedException {
        DeliveryData.add(e);
        System.out.println("Заказ успешно добавлен из таблицы!");
        SetDelivery(e);
    }
    public BaseTransport getBestExecutor(Delivery e) throws CloneNotSupportedException {
            BaseTransport executor = null;
            Double time=null;
            for (BaseTransport baseTransport : department.getVehicles()) {
                    if (baseTransport.getActiveDelivery() != null) {
                        System.out.println("Continued, slot is busy: " + baseTransport);
                        continue;};
                    if (!baseTransport.hasSpace(e)){
                        System.out.println("Continued, vehicle doesnt have space: " + baseTransport);
                        continue;};
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

    // Обновляет местоположение машин
    public void UpdateLc(){
        TimerTask task = new TimerTask() {
            public void run() {
                for (BaseTransport transport:department.getVehicles() ){
                    if (transport == null){continue;}
                    if (transport.resultWaypoints==null){continue;}
                    if (transport.resultWaypoints.isEmpty()){continue;}
                    if ( transport.indWaypoint  >= transport.resultWaypoints.size()){continue;}
                    if ( transport.indWaypoint  == transport.resultWaypoints.size()-1){
                        try {
                            transport.UnShipProductsInCar(transport.getActiveDelivery());
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                        Main.map.delete(transport.getActiveDelivery().getAddress());
                        //System.out.println("Graph.Points.get(transport.getActiveDelivery().getAddress().getName()) = " + Graph.Points.get(transport.getActiveDelivery().getAddress().getName()));
                        Graph.Points.remove(transport.getActiveDelivery().getAddress().getName());
                        transport.getActiveDelivery().getAddress().setFinished(true);
                        transport.getActiveDelivery().setExecutor(null);


                        LocalDateTime time =  LocalDateTime.now();

                        LocalDateFormatted2 dateFormatted2 = new LocalDateFormatted2(time);

                        transport.getActiveDelivery().setTime_end(dateFormatted2);
                   long min  = Duration.between(time, transport.getActiveDelivery().getTime_start().getLocalDateTime()).toMinutes();
                        ChartController.addData((int) min);
                   if (min > Config.TIME_DELIVERY){
                       transport.getActiveDelivery().setStatus(DeliveryStatus.DELAYED);
                   }
                   else
                   {
                       transport.getActiveDelivery().setStatus(DeliveryStatus.OK);
                   }
                        transport.setActiveDelivery(null);


                        for (Delivery d: getDeliveryData()){
                            if (d.getStatus() == DeliveryStatus.OK) continue;
                            if (d.getExecutor() != null) continue;
                            //System.out.println(" Testing= " +d.getAddress());
                            //System.out.println("Delivery + " + d.get);
                            SetDelivery(d);
                        }
                    }

                    //if (transport.getActiveDelivery().getExecutor() != transport){continue;}
                    //System.out.println(transport.products);
                    transport.setX( transport.resultWaypoints.get(transport.indWaypoint).getX());
                    transport.setY( transport.resultWaypoints.get(transport.indWaypoint).getY());
                  transport.indWaypoint++;
                }

                UpdateLc();
            }
        };


        long delay = 50L;
        timer.schedule(task, delay);
    }

    public boolean SetDelivery(Delivery delivery){

            try {
                BaseTransport t;
                t = (Main.deliveryLogic.getBestExecutor(delivery));
                if (t == null) throw new NullPointerException();
                t.setActiveDelivery(delivery);
                delivery.setExecutor( t );

                t.GetWaypoints(t.getPathToDelivery(delivery));
                t.indWaypoint = 0;
                t.ShipProductsInCar(delivery);
                System.out.println(t + " берет заказ " + delivery);

                return t != null;
                //Main.map.DrawPath(t.getPathToDelivery(e).getPath());
            }
            catch (NullPointerException | CloneNotSupportedException tt){
                //tt.printStackTrace();
                return false;
            }
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
    public void change_executor(Integer id, BaseTransport executor){
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
