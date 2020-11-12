package sample;

import sample.MapLogic.Vertex;

import java.util.ArrayList;

//this class need to know what will prepare kitchen
public class Kitchen extends Vertex {
    private ArrayList<Product> products_of_kitchen=new ArrayList<>();

    public Kitchen(double x, double y) {
        super(x, y);
    }

    public Kitchen(ArrayList<Product> products_of_kitchen) {
        this.products_of_kitchen = products_of_kitchen;
    }

    public Kitchen() {
    }

    public void add_products(Product pr){
        System.out.println("Кухня приступила к приготовлению пищи "+pr.getName());
        products_of_kitchen.add(pr);
    }

    public void clear(){
        products_of_kitchen.clear();
    }

    public ArrayList<Product> getProducts_of_kitchen() {
        return products_of_kitchen;
    }

    public void setProducts_of_kitchen(ArrayList<Product> products_of_kitchen) {
        this.products_of_kitchen = products_of_kitchen;
    }
}
