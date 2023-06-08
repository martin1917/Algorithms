package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GeneratorArrays {
    
    public static int[] sortedArray(int size) {
        List<Integer> arr = generateList(size);
        Collections.sort(arr);
        return arr.stream().mapToInt(el->el).toArray();
    }
    
    public static int[] unsortedArray(int size) {
        List<Integer> arr = generateList(size);
        return arr.stream().mapToInt(el->el).toArray();
    }
    
    public static int[] sortedDescArray(int size) {
        List<Integer> arr = generateList(size);
        Collections.sort(arr);
        Collections.reverse(arr);
        return arr.stream().mapToInt(el->el).toArray();
    }
    
    public static int[] someSortedArray(int size, int procent) {
        int size1 = size * procent/100;
        int size2 = size - size1;
        List<Integer> arr1 = generateList(size1);
        List<Integer> arr2 = generateList(size2);
        Collections.sort(arr1);
        arr1.addAll(arr2);
        return arr1.stream().mapToInt(el->el).toArray();
    }
        
    private static List<Integer> generateList(int size) {
        List<Integer> arr = new ArrayList<Integer>(size);
        for (int i = 0; i < size; i++) {
            arr.add((int)(Math.random() * 65000 + 1));
        }
        return arr;
    }
}
