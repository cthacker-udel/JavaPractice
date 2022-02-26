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

    public static boolean validParentheses(String parens) {

        Stack<String> parenStack = new Stack<>();
        for (int i = 0; i < parens.length(); i++) {
            char theParen = parens.charAt(i);
            if (")".contains(theParen + "")) {
                // is a bracket
                switch (theParen) {
                    case ')': {
                        if (parenStack.size() > 0 && parenStack.peek().equals("(")) {
                            parenStack.pop();
                        } else {
                            return false;
                        }
                        break;
                    }
                }
            } else if ("(".contains(theParen + "")) {
                parenStack.push(theParen+"");
            }
        }
        return true;
    }

    public static String pluralize_time(int count, char spec) {

        if (count > 1) {
            switch (spec) {
                case 'y':
                    return String.format("%d years", count);
                case 'd':
                    return String.format("%d days", count);
                case 'h':
                    return String.format("%d hours", count);
                case 'm':
                    return String.format("%d minutes", count);
                case 's':
                    return String.format("%d seconds", count);
                default:
                    return "";
            }
        } else {
            switch (spec) {
                case 'y':
                    return String.format("%d year", count);
                case 'd':
                    return String.format("%d day", count);
                case 'h':
                    return String.format("%d hour", count);
                case 'm':
                    return String.format("%d minute", count);
                case 's':
                    return String.format("%d second", count);
                default:
                    return "";
            }
        }

    }

    public static String formatDuration(int seconds) {
        if (seconds <= 0) {
            return "now";
        }
        int years = 0, days = 0, hours = 0, minutes = 0, s = 0;
        while (seconds >= 31536000) {
            years++;
            seconds -= 31536000;
        }
        while (seconds >= 86400) {
            days++;
            seconds -= 86400;
        }
        while (seconds >= 3600) {
            hours++;
            seconds -= 3600;
        }
        while (seconds >= 60) {
            minutes++;
            seconds -= 60;
        }
        s = seconds;
        char specs[] = {'y', 'd', 'h', 'm', 's'};
        int amts[] = {years, days, hours, minutes, s};
        ArrayList<String> times = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int amt = amts[i];
            char spec = specs[i];
            if (amt > 0) {
                times.add(pluralize_time(amt, spec));
            }
        }
        if (times.size() > 1) {
            if (times.size() > 2) {
                String lastOne = times.remove(times.size() - 1);
                String secondToLast = times.remove(times.size() - 1);
                times.add(String.format("%s and %s", secondToLast, lastOne));
                return String.join(", ", times);
            } else {
                return String.join(" and ", times);
            }
        } else {
            return times.get(0);
        }

    }

    public static String maskify(String str) {

        return str.length() >= 4 ? "#".repeat(str.length() - 4) + str.substring(str.length() - 4) : str;

    }

    public static void main(String[] args) {

        formatDuration(3662);

    }

}
