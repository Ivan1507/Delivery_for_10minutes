import java.util.Objects;

public class Bus extends Transport {
    private String Model;
    public Bus(String name,String model, int speed, int lifting_capacity) {
        super(name, speed, lifting_capacity);
        Model=model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bus)) return false;
        Bus bus = (Bus) o;
        return bus.Model.equals(this.Model)&&bus.Name.equals(this.Name)&&bus.Max_Speed==this.Max_Speed
                &&bus.Lifting_capacity==this.Lifting_capacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Model);
    }
}
