package logic;

import java.util.List;

@FunctionalInterface
public interface SortMethod {
    List<List<Integer>> sort(int[] arr);
}
