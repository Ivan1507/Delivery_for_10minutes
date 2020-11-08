package sample.Delivery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DeliverySerializer implements Serializable {
    private ArrayList<Delivery> DList;

    public DeliverySerializer(ObservableList<Delivery> collections){
        DList = collections.stream().collect(Collectors.toCollection(ArrayList::new));
    }
    public DeliverySerializer(){
    }
    public ArrayList<Delivery> getDList() {
        return DList;
    }


    public  void SaveObject(String name) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File( "Delivery" + name + ".dat"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(DList);
        objectOutputStream.close();
    }


    public ObservableList<Delivery> LoadObject(String name) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("Delivery" + name + ".dat")));
       ArrayList<Delivery> DList = (ArrayList<Delivery>) objectInputStream.readObject();
        //System.out.println(DList.size());
        return FXCollections.observableArrayList(DList);
    }


}
