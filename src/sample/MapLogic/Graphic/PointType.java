package sample.MapLogic.Graphic;

public enum PointType {
    TwoCricle("1"), Triangle("2"), Square("3"), Circle("4");

    private String t;

    PointType(String t) {
        this.t = t;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }
}
