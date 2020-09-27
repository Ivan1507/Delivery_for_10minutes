public class Quadcopter extends Transport {
    public String Model;
    public Quadcopter(String name,String model, int speed, int lifting_capacity) {
        super(name, speed, lifting_capacity);
        Model=model;
    }

    @Override
    public String toString() {
        return "Quadcopter" +
                "Model=" + Model+
                ",Name=" + Name +
                ", Max_Speed=" + Max_Speed +
                ", Lifting_capacity=" + Lifting_capacity;
    }
}
