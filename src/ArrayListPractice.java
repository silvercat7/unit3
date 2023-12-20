import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListPractice {
    public static void main(String[] args) {
        ArrayList<Integer> test = new ArrayList<>(Arrays.asList(5, 3, 2, 6, 3));
        ArrayList<Integer> randomList = makeRandomList(8, 100);
        System.out.println(randomList);
    }

    public static ArrayList<Integer> makeRandomList(int n, int MAX) {
        ArrayList<Integer> randomList = new ArrayList<>();
        int i = 0;
        while (i < n) {
            randomList.add((int) (Math.random() * MAX));
            i++;
        }
        return randomList;
    }

    public static void removeSmall(ArrayList<Double> numList, double min) {
        for (int i = 0; i < numList.size(); i++) {
            if (numList.get(i) < min) {
                numList.remove(i);
                i--;
            }
        }
    }

    public static void swap(ArrayList<Integer> list, int a, int b) {
        int first = Math.min(a, b);
        int second = (a + b) - first;
        int firstInt = list.remove(first);
        int secondInt = list.remove(second - 1);
        list.add(first, secondInt);
        list.add(second,firstInt);
    }
}
