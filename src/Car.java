public class Car extends Transport {
    public String Model;
    public Car(String name, String model,int speed,int lifting_capacity) {
        super(name, speed,lifting_capacity);
        Model=model;
    }

    @Override
    public String toString() {
        return "Car:" +
                "Model=" + Model +
                ", Name='" + Name +
                ", Max_Speed=" + Max_Speed;
    }
}
