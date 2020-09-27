public class Demonstrate {
    public static void main(String[] args){
        Transport ped=new Pedestrian("Иван","Разинкин",12,21,15);
        System.out.println(ped);
        Bicycle zubr=new Bicycle("Zubr","Pegas",40,30);
        System.out.println(zubr);
        System.out.println(zubr.hashCode());
        System.out.println("=====");
        Point MIET=new Point("MIET",0,10);
        Point Krukovo=new Point("Krukovo",10,0);
        System.out.println(MIET);
        Road Miet_Krukovo=new Road(MIET,Krukovo);
        System.out.println(Miet_Krukovo);
        Bus bs=new Bus("Audi","A112",80,1000);
        Bus bs1=new Bus("Audi","A112",80,1000);
        System.out.println(bs.equals(bs1));
        Quadcopter Aue=new Quadcopter("Qudro","19901A",120,10);
        System.out.println(Aue);
    }
}
