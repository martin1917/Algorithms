package logic;

public class Solution {
    public static double[] bubbleSort(int[] arr) {
        int last = arr.length - 1;
        
        double begin = System.nanoTime();
        int countCompare = 0;
        int countSwap = 0;

        while(last > 0) {
            countCompare++;
            int barrier = 0;
            for (int i = 0; i < last; i++) {
                countCompare++;
                if(arr[i] > arr[i+1]) {
                    countCompare++;
                    int tmp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = tmp;
                    barrier = i;
                    countSwap += 3;
                }
            }
            last = barrier;
        }
        double time = (System.nanoTime() - begin) / 1000;
        return new double[]{countCompare, countSwap, time};
    }
    
    public static double[] bubbleSortBidirectional(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        boolean swapOnStep = true;

        double begin = System.nanoTime();
        int countCompare = 0;
        int countSwap = 0;

        while(swapOnStep) {
            countCompare++;
            swapOnStep = false;
            for (int i = left; i < right; i++) {
                countCompare++;
                if(arr[i] > arr[i+1]) {
                    countCompare++;
                    int tmp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = tmp;
                    swapOnStep = true;
                    countSwap += 3;
                }
            }
            right--;

            for (int i = right; i >= left; i--) {
                countCompare++;
                if(arr[i] > arr[i+1]) {
                    countCompare++;
                    int tmp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = tmp;
                    swapOnStep = true;
                    countSwap += 3;
                }
            }
            left++;
        }
        double time = (System.nanoTime() - begin) / 1000;
        return new double[]{countCompare, countSwap, time};
    }

    public static double[] selectionSort(int[] arr) {
        int right = arr.length - 1;

        double begin = System.nanoTime();
        int countCompare = 0;
        int countSwap = 0;

        for (int i = 0; i <= right; i++) {
            countCompare++;
            int minIndex = i;
            int maxIndex = right;

            for (int j = i; j <= right; j++) {
                countCompare++;
                if(arr[j] > arr[maxIndex]) {
                    countCompare++;
                    maxIndex = j;
                }
                if(arr[j] < arr[minIndex]) {
                    countCompare++;
                    minIndex = j;
                }
            }

            int tmp = 0;

            if(minIndex != right || maxIndex != i) {
                countCompare += 2;
                tmp = arr[right];
                arr[right] = arr[maxIndex];
                arr[maxIndex] = tmp;
                countSwap += 3;
                
                tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
                countSwap += 3;

            } else {
                countCompare += 2;
                tmp = arr[right];
                arr[right] = arr[i];
                arr[i] = tmp;
                countSwap += 3;
            }

            right--;
        }

        double time = (System.nanoTime() - begin) / 1000;
        return new double[]{countCompare, countSwap, time};
    }

    public static double[] sortbByInserts(int[] arr) {
        double begin = System.nanoTime();
        int countCompare = 0;
        int countSwap = 0;

        for(int i = 1; i < arr.length; i++) {
            countCompare++;
            int value = arr[i];
            int first = arr[0];
            arr[0] = value;
            int index = i;
            
            while(arr[index - 1] > value) {
                countCompare++;
                arr[index] = arr[index - 1];
                countSwap++;
                index--;
            }
            if(first > value) {
                countCompare++;
                arr[0] = value;
                arr[index] = first;
                countSwap += 2;
            } else {
                countCompare++;
                arr[0] = first;
                arr[index] = value;
                countSwap += 2;
            }
        }

        double time = (System.nanoTime() - begin) / 1000;
        return new double[]{countCompare, countSwap, time};
    }

    public static double[] sortShell(int[] arr) {
        double begin = System.nanoTime();
        int countCompare = 0;
        int countSwap = 0;

        //кол-во проходов (Н. Вирт)
        final int t = (int)(Math.log(arr.length) / Math.log(2)) - 1;

        //шаги (h[t-1] = 1; h[k-1] = 2h[k] + 1)
        int[] steps = new int[t];
        steps[t - 1] = 1;
        for (int i = t - 2; i >= 0; i--) {
            steps[i] = 2*steps[i+1] + 1;
        }

        for(int k = 0; k < steps.length; k++) {
            countCompare++;
            int i, j;
            int step = steps[k];
            for (i = step; i < arr.length; i++) {
                countCompare++;
                int value = arr[i];
                for (j = i - step; (j >= 0) && (arr[j] > value); j -= step) {
                    countCompare += 2;
                    arr[j + step] = arr[j];
                    countSwap++;
                }
                arr[j + step] = value;
            }
        }

        double time = (System.nanoTime() - begin) / 1000;
        return new double[]{countCompare, countSwap, time};
    }

    public static double[] linearSort(int[] arr) {
        double begin = System.nanoTime();
        int[] temp = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            temp[arr[i]] += 1;
        }
        
        int index = 0;
        for(int i = 0; i < temp.length; i++) {
            for(int j = 0; j < temp[i]; j++) {
                arr[index] = i;
                index++;
            }
        }
        double time = (System.nanoTime() - begin) / 1000;
        return new double[]{0, 0, time};
    }
}