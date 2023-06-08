package Data;

public class InfoAboutSort {
    private String nameSorting;
    private double countCompare;
    private double countSwap;
    private double time;

    private InfoAboutSort(String nameSorting, double countCompare, double countSwap, double time) {
        this.nameSorting = nameSorting;
        this.countCompare = countCompare;
        this.countSwap = countSwap;
        this.time = time;
    }

    public static InfoAboutSort create(String name, double[] params) {
        return new InfoAboutSort(name, params[0], params[1], params[2]);
    }


    public String getNameSorting() {
        return this.nameSorting;
    }
    public void setNameSorting(String nameSorting) {
        this.nameSorting = nameSorting;
    }

    public double getCountCompare() {
        return this.countCompare;
    }
    public void setCountCompare(int countCompare) {
        this.countCompare = countCompare;
    }

    public double getCountSwap() {
        return this.countSwap;
    }
    public void setCountSwap(int countSwap) {
        this.countSwap = countSwap;
    }

    public double getTime() {
        return this.time;
    }
    public void setTime(double time) {
        this.time = time;
    }

}
