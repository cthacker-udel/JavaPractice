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

    public static int[] smaller(int[] unsorted) {

        int count = 0;

        for (int i = unsorted.length - 1; i >= 0; i--) {
            if (i <= unsorted.length - 2 && unsorted[i] > unsorted[i + 1]) {
                unsorted[i] = ++count;
            } else {
                unsorted[i] = count;
            }
        }
        return unsorted;

    }

    public static int[] waveSort(int[] array) {

        Arrays.sort(array);
        for (int i = 0; i < array.length - 1; i += 2) {
            int tmp = array[i + 1];
            array[i + 1] = array[i];
            array[i] = tmp;
        }
        return array;

    }

    public static String meeting(String s) {

        List<String> people = Stream.of(s.split(";")).collect(Collectors.toList());
        Collections.sort(people, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                String[] names_o1 = o1.split(":");
                String[] names_o2 = o2.split(":");
                if (names_o1[1].toUpperCase().equals(names_o2[1].toUpperCase())) {
                    // same last names
                    return names_o1[0].toUpperCase().compareTo(names_o2[0].toUpperCase());
                }
                return names_o1[1].toUpperCase().compareTo(names_o2[1].toUpperCase());
            }
            
        });
        return people.stream().map(e -> String.format("(%s, %s)", e.split(":")[1].toUpperCase(), e.split(":")[0].toUpperCase())).collect(Collectors.joining(""));

    }

    public static void main(String[] args) {

        System.out.println(Arrays.toString(waveSort(new int[] {1, 2, 34, 4, 5, 5, 5, 65, 6, 65, 5454, 4})));

    }

}
