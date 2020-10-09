package sample;

public enum ActionType{
    Point("Point"),Road("Road"), Empty("Empty"),NULL("null") ;

    String Type;
    ActionType(String type){
        Type=type;
    }
    ActionType(){

    }
    public String getType() {
        return Type;
    }

    @Override
    public String toString() {
        return Type;
    }
}