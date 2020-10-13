import java.util.Objects;

public class Bicycle extends Transport {
    private String Mark;
    public Bicycle(String name,String mark, int speed,int lifting_capacity) {
        super(name, speed,lifting_capacity);
        Mark=mark;
    }

    @Override
    public String toString() {
        return "Bicycle: "+
                "Mark=" + Mark+
                ", Name=" + Name+
                ", Max_Speed=" + Max_Speed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Max_Speed);
    }
}
