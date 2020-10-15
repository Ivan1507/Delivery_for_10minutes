package sample.MapLogic;

public enum Quality_Road {
    Very_bad(0.2),
    bad(0.4),
    average(0.6),
    good(0.9);

    private double status;
    Quality_Road( double a){
        status=a;
    }

    public double getStatus() {
        return status;
    }
}
