package sample;

class ButtonUserData{
    private ActionType type;
    public ButtonUserData(  ActionType actionType){
        type = actionType;
    }
    public ButtonUserData( ){
    }
    public ActionType getType() {
        return type;
    }
    public void setType(ActionType type){
        this.type=type;
    }
    public void clear(){
        type = null;
        System.gc();
    }
}
