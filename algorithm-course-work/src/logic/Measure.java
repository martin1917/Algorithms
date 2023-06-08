package logic;

public class Measure {
    
    public static double getTime(int[] arr, Algorithm kindSort) {
        double before = System.nanoTime();
        kindSort.sort(arr);
        double after = System.nanoTime();
        return (after - before) / 1_000_000;
    }
}