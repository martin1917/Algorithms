package logic;

import java.util.List;
import java.util.ArrayList;

public class SolutionWithInfo {
    public static List<List<Integer>> BubbleSort(int[] arr) {
        List<List<Integer>> steps = new ArrayList<>();
        int num = 0;
        int last = arr.length - 1;

        List<Integer> zeroStep = new ArrayList<>();
        zeroStep.add(0);
        for(int i = 0; i < arr.length; i++) {
            zeroStep.add(arr[i]);
        }
        steps.add(zeroStep);

        while(last > 0) {
            List<Integer> currStep = new ArrayList<>();

            int barrier = 0;
            for (int i = 0; i < last; i++) {
                if(arr[i] > arr[i+1]) {
                    int tmp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = tmp;
                    barrier = i;
                }
            }
            last = barrier;

            currStep.add(++num);
            for(int i = 0; i < arr.length; i++) {
                currStep.add(arr[i]);
            }
            steps.add(currStep);
        }

        return steps;
    }
    
    public static List<List<Integer>> BubbleSortBidirectional(int[] arr) {
        List<List<Integer>> steps = new ArrayList<>();
        int num = 0;

        int left = 0;
        int right = arr.length - 1;
        boolean swapOnStep = true;

        List<Integer> zeroStep = new ArrayList<>();
        zeroStep.add(0);
        for(int i = 0; i < arr.length; i++) {
            zeroStep.add(arr[i]);
        }
        steps.add(zeroStep);

        while(swapOnStep) {
            List<Integer> currStep = new ArrayList<>();

            swapOnStep = false;
            for (int i = left; i < right; i++) {
                if(arr[i] > arr[i+1]) {
                    int tmp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = tmp;
                    swapOnStep = true;
                }
            }
            right--;

            for (int i = right; i >= left; i--) {
                if(arr[i] > arr[i+1]) {
                    int tmp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = tmp;
                    swapOnStep = true;
                }
            }
            left++;

            currStep.add(++num);
            for(int i = 0; i < arr.length; i++) {
                currStep.add(arr[i]);
            }
            steps.add(currStep);
        }

        return steps;
    }

    public static List<List<Integer>> selectionSort(int[] arr) {
        List<List<Integer>> steps = new ArrayList<>();
        int num = 0;
        int right = arr.length - 1;

        List<Integer> zeroStep = new ArrayList<>();
        zeroStep.add(0);
        for(int i = 0; i < arr.length; i++) {
            zeroStep.add(arr[i]);
        }
        steps.add(zeroStep);

        for (int i = 0; i <= right; i++) {
            List<Integer> currStep = new ArrayList<>();

            int minIndex = i;
            int maxIndex = right;

            for (int j = i; j <= right; j++) {
                if(arr[j] > arr[maxIndex]) {
                    maxIndex = j;
                }
                if(arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            int tmp = 0;

            if(minIndex != right || maxIndex != i) {
                tmp = arr[right];
                arr[right] = arr[maxIndex];
                arr[maxIndex] = tmp;
                
                tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;

            } else {
                tmp = arr[right];
                arr[right] = arr[i];
                arr[i] = tmp;
            }

            right--;

            currStep.add(++num);
            for(int j = 0; j < arr.length; j++) {
                currStep.add(arr[j]);
            }
            steps.add(currStep);
        }

        return steps;
    }

    public static List<List<Integer>> SortByInserts(int[] arr) {
        List<List<Integer>> steps = new ArrayList<>();
        int num = 0;

        List<Integer> zeroStep = new ArrayList<>();
        zeroStep.add(0);
        for(int i = 0; i < arr.length; i++) {
            zeroStep.add(arr[i]);
        }
        steps.add(zeroStep);

        for(int i = 1; i < arr.length; i++) {
            List<Integer> currStep = new ArrayList<>();

            int value = arr[i];
            int first = arr[0];
            arr[0] = value;
            int index = i;
            
            while(arr[index - 1] > value) {
                arr[index] = arr[index - 1];
                index--;
            }
            if(first > value) {
                arr[0] = value;
                arr[index] = first;
            } else {
                arr[0] = first;
                arr[index] = value;
            }

            currStep.add(++num);
            for(int j = 0; j < arr.length; j++) {
                currStep.add(arr[j]);
            }
            steps.add(currStep);
        }
        return steps;
    }

    public static List<List<Integer>> SortShell(int[] arr) {
        List<List<Integer>> steps1 = new ArrayList<>();
        int num = 0;

        List<Integer> zeroStep = new ArrayList<>();
        zeroStep.add(0);
        for(int i = 0; i < arr.length; i++) {
            zeroStep.add(arr[i]);
        }
        steps1.add(zeroStep);

        //кол-во проходов (Н. Вирт)
        final int t = (int)(Math.log(arr.length) / Math.log(2)) - 1;

        //шаги (h[t-1] = 1; h[k-1] = 2h[k] + 1)
        int[] steps = new int[t];
        steps[t - 1] = 1;
        for (int i = t - 2; i >= 0; i--) {
            steps[i] = 2*steps[i+1] + 1;
        }

        for(int k = 0; k < steps.length; k++) {
            int i, j;
            int step = steps[k];
            List<Integer> currStep = new ArrayList<>();
            for (i = step; i < arr.length; i++) {

                int value = arr[i];
                for (j = i - step; (j >= 0) && (arr[j] > value); j -= step) {
                    arr[j + step] = arr[j];
                }
                arr[j + step] = value;

            }
            currStep.add(++num);
            for(int c = 0; c < arr.length; c++) {
                currStep.add(arr[c]);
            }
            steps1.add(currStep);
        }

        return steps1;
    }

    public static List<List<Integer>> LinearSort(int[] arr) {
        List<List<Integer>> steps = new ArrayList<>();

        List<Integer> zeroStep = new ArrayList<>();
        zeroStep.add(0);
        for(int i = 0; i < arr.length; i++) {
            zeroStep.add(arr[i]);
        }
        steps.add(zeroStep);

        int[] temp = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            temp[arr[i]] += 1;
        }

        List<Integer> step1 = new ArrayList<>();
        step1.add(1);
        for(int c = 0; c < arr.length; c++) {
            step1.add(temp[c]);
        }
        steps.add(step1);
        
        int index = 0;
        for(int i = 0; i < temp.length; i++) {
            for(int j = 0; j < temp[i]; j++) {
                arr[index] = i;
                index++;
            }
        }

        List<Integer> step2 = new ArrayList<>();
        step2.add(2);
        for(int c = 0; c < arr.length; c++) {
            step2.add(arr[c]);
        }
        steps.add(step2);

        return steps;
    }
}