package logic;

public class Solution {
    
    public static void sortBinary(int[] arr) {
        sortBinary(arr, 0, arr.length);
    }
    
    public static void sortBinary(int[] arr, int start, int end) {
        
        for(int i = start + 1; i < end; i++) {
            int value = arr[i];
            int L = 0;
            int R = i;
            
            while(L < R) {
                int m = (L + R) / 2;
                if(arr[m] <= value) {
                    L = m + 1;
                } else {
                    R = m;
                }
            }
            
            for(int j = i; j >= R + 1; j--) {
                arr[j] = arr[j-1];
            }
            arr[R] = value;
        }

    }
    
    public static void sortLinear(int[] arr) {
        sortLinear(arr, 0, arr.length);
    }
    
    public static void sortLinear(int[] arr, int start, int end) {

        for(int i = start + 1; i < end; i++) {
            int value = arr[i];
            int index = i;
            while((index > 0) && (arr[index - 1] > value)) {
                arr[index] = arr[index - 1];
                index--;
            }
            arr[index] = value;
        }

    }

    public static void sortWithMin(int[] arr) {

        //поиск минимального элемента в массиве
        int min = arr[0];
        int minIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if(arr[i] < min) {
                min = arr[i];
                minIndex = i;
            }
        }

        //меняем местами минимальный и первый элементы в массиве
        int tmp = arr[0];
        arr[0] = min;
        arr[minIndex] = tmp;

        //сортировка
        for (int i = 1; i < arr.length; i++) {
            int value = arr[i];
            int index = i;
            while(arr[index - 1] > value) {
                arr[index] = arr[index - 1];
                index--;
            }
            arr[index] = value;
        }
    }
}
