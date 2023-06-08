package Data;

public class RowForTable {
    private int size;
    private double timeLinear;
    private double timeBinary;
    private double timeLinearWithMin;

    public RowForTable(int size, double time1, double time2, double time3) {
        this.size = size;
        this.timeLinear = time1;
        this.timeBinary = time2;
        this.timeLinearWithMin = time3;
    }

    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }

    public double getTimeLinear() {
        return timeLinear;
    }
    public void setTimeLinear(double time) {
        this.timeLinear = time;
    }

    public double getTimeBinary() {
        return timeBinary;
    }
    public void setTimeBinary(double time) {
        this.timeBinary = time;
    }

    public double getTimeLinearWithMin() {
        return timeLinearWithMin;
    }
    public void setTimeLinearWithMin(double time) {
        this.timeLinearWithMin = time;
    }
}