import java.util.Objects;

public class Pedestrian extends  Transport{
    private String Surname;
    private int Age;
    public Pedestrian(String name,String surname, int speed,int age,int lf) {
        super(name, speed,lf);
        Surname=surname;
        Age=age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Surname, Age);
    }

    @Override
    public String toString() {
        return "Pedestrian:"+
                "Surname=" + Surname+
                ", Age=" + Age +
                ", Name=" + Name+
                ", Max_Speed=" + Max_Speed;
    }
}
