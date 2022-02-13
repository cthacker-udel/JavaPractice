import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.*;

public class Kata {
    
    public static int findInt(int[] a) {

        List<Integer> linkedList = IntStream.of(a).boxed().collect(Collectors.toList());
        return IntStream.of(a).filter(e -> Collections.frequency(linkedList, e) % 2 != 0).findFirst().getAsInt();

    }

    public static int findEvenIndex(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            final int index = i;
            int leftSide = IntStream.range(0, index).map(e -> arr[e]).sum();
            int rightSide = IntStream.range(index + 1, arr.length).map(e -> arr[e]).sum();
            if (leftSide == rightSide) {
                return i;
            }
        }
        return -1;
    }

    public static int[] sortArray(int[] array) {

        List<Integer> oddNumbers = IntStream.of(array).filter(e -> e % 2 != 0).boxed().collect(Collectors.toList());
        Collections.sort(oddNumbers);
        final AtomicInteger index = new AtomicInteger(0);
        return IntStream.of(array).map(e -> e % 2 == 0 ? e : oddNumbers.get(index.getAndIncrement())).toArray();

    }

    public int solution(int number) {
        return IntStream.range(3, number).filter(e -> e % 3 == 0 || e % 5 == 0).sum();
    }

    public static String pigIt(String str) {

        final String punctuation = "?!,.";

        return Stream.of(str.split(" ")).map(e -> e.contains("?") || e.contains("!") || e.contains(",") || e.contains(".") ? e : e.substring(1) + e.charAt(0) + "ay").collect(Collectors.joining(" "));

    }

    public static void main(String[] args) {

        System.out.println(Arrays.toString(sortArray(new int[]{5, 8, 6, 3, 4})));

    }

}
