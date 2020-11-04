package sample;

public class Product {
    private String Name;
    private double Weight;
    private double Volume;

    public Product( String n, double w, double v) {
        this.Name = n;
        this.Weight = w;
        this.Volume = v;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public double getVolume() {
        return Volume;
    }

    public void setVolume(double volume) {
        Volume = volume;

    }

    @Override
    public String toString() {
        return "" +
                "Name='" + Name + '\n';
    }
}