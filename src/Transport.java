public abstract class Transport {
    protected String Name;
    protected  int Max_Speed;
    protected int Lifting_capacity;
    public Transport(String name,int speed,int lifting_capacity){
        Name=name;
        Max_Speed=speed;
        Lifting_capacity=lifting_capacity;
    }
    @Override
    public String toString() {
        return "Transport " +
                "Name=" + Name+
                ", Max_Speed=" + Max_Speed;
    }
}
