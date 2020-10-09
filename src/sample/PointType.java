package sample;

public enum PointType {
    Point("Point"),Production("Production") ;

    String Type;
    PointType(String type){
        Type=type;
    }
    PointType(){

    }
    public String getType() {
        return Type;
    }

    @Override
    public String toString() {
        return Type;
    }
}