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

    public static String rangeExtraction(int[] arr) {

        ArrayList<int[]> ranges = new ArrayList<>();
        int left = arr[0];
        int right = arr[0];
        for (int i = 0; i < arr.length; i++) {
            int currentElem = arr[i];
            if (Math.abs(right - currentElem) > 1) {
                if (Math.abs(right - left) == 1) {
                    // only two elems
                    int[] range = new int[]{left, left};
                    ranges.add(range);
                    range = new int[]{right, right};
                    ranges.add(range);
                } else {
                    // found new starter
                    int[] range = new int[]{left, right};
                    ranges.add(range);
                }
                left = currentElem;
                right = currentElem;
            } else {
                right = currentElem;
            }
        }
        if (Math.abs(left - right) == 1) {
            int[] range = new int[]{left, left};
            ranges.add(range);
            range = new int[]{right, right};
            ranges.add(range);
        } else {
            int[] lastRange = new int[]{left, right};
            ranges.add(lastRange);
        }
        ArrayList<String> strList = new ArrayList<>();
        for (int[] eacharr : ranges) {
            if (eacharr[0] == eacharr[1]) {
                strList.add(eacharr[0] + "");
            } else {
                strList.add(eacharr[0] + "-" + eacharr[1]);
            }
        }
        return String.join(",", strList);
    }

    public static String stringy(int size) {

        return IntStream.range(0, size).mapToObj(e -> e % 2 == 0 ? "1" : "0").collect(Collectors.joining(""));

    }

    public static int summation(int n) {
        return IntStream.rangeClosed(1, n).sum();
    }

    public int min(int[] list) {

        Arrays.sort(list);
        return list[0];

    }

    public int max(int[] list) {
        Arrays.sort(list);
        return list[list.length - 1];
    }

    public static String[] capitalize(String s) {

        s = s.toLowerCase();
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        AtomicInteger index = new AtomicInteger();
        Stream.of(s.split("")).forEach(e -> {
            if (index.get() % 2 == 0) {
                sb1.append(e.toUpperCase());
                sb2.append(e.toLowerCase());
            } else {
               sb1.append(e.toLowerCase());
               sb2.append(e.toUpperCase());  
            }
            index.incrementAndGet();
        });
        return new String[] {sb1.toString(), sb2.toString()};

    }

    public static long fib (int n) {
        ArrayList<Integer> intList = new ArrayList<>(Arrays.asList(1, 1));
        while (intList.size() < n) {
            intList.add(intList.get(intList.size() - 1) + intList.get(intList.size() - 2));
        }
        return intList.get(intList.size() - 1);
    }

    public static long cardGame(long n) {

        long alice = 0;
        long bob = 0;
        boolean turn = true;
        while (n > 0) {
            if (n <= 4) {
                alice += turn ? n - 1 : 1;
                bob += turn ? 1 : n - 1;
                return alice;
            } else if (n % 2 != 0) {
                bob += turn ? 0 : 1;
                alice += turn ? 1 : 0;
                n -= 1;
            } else if ((n / 2) % 2 != 0) {
                // 10 --> 5
                alice += turn ? n / 2 : 0;
                bob += turn ? 0 : n / 2;
                n /= 2;
            } else if ((n / 2) % 2 == 0) {
                alice += turn ? 1 : 0;
                bob += turn ? 0 : 1;
                n -= 1;
            } else if (n % 2 == 0) {
                alice += turn ? n / 2 : 0;
                bob += turn ? 0 : n / 2;
                n /= 2;
            } else {
                alice += turn ? 1 : 0;
                bob += turn ? 0 : 1;
                n -= 1;
            }
            turn = !turn;
        }
        return alice;


    }

    // 11 -- 1
    // 10 -- 1
    // 5 -- 6
    // 4 -- 6
    // 

    // 6 -- 3
    // 3 -- 3
    // 2 -- 4

    public static boolean smallEnough(int[] a, int limit) {
        return IntStream.of(a).filter(e -> e <= limit).toArray().length == a.length;
    }

    static final String alphanumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ $%*+-./:";

    public static String byteMode(String msg) {

        StringBuilder sb = new StringBuilder();
        sb.append("0100");
        String binLength = String.format("%8s",Integer.toBinaryString(msg.length())).replace(" ", "0");
        sb.append(binLength);
        for (int i = 0; i < msg.length(); i++) {
            char result = msg.charAt(i);
            String binString = String.format("%8s", Integer.toBinaryString(result)).replace(" ", "0");
            sb.append(binString);
        }
        return sb.toString();

    }

    public static List<String> alphaNumericSeparateGroups(String msg) {
        ArrayList<String> sb = new ArrayList<>();
        String[] splitStr = msg.split("");
        String emptyString = "";
        for (String eachletter : splitStr) {
            if (emptyString.length() == 2) {
                sb.add(emptyString);
                emptyString = eachletter;
            } else {
                emptyString += eachletter;
            }
        }
        if (emptyString.length() > 0) {
            sb.add(emptyString);
        }
        return sb;
    }

    public static String convertToPairAlpha(String pair) {
        if (pair.length() == 1) {
            return String.format("%6s", Integer.toBinaryString(alphanumeric.indexOf(pair.charAt(0)+""))).replaceAll(" ", "0");
        } else {
            int firstVal = alphanumeric.indexOf(pair.charAt(0)+"");
            int secondVal = alphanumeric.indexOf(pair.charAt(1)+"");
            int total = (firstVal * 45) + secondVal;
            return String.format("%11s", Integer.toBinaryString(total)).replaceAll(" ", "0");
        }
    }

    public static String alphaNumericMode(String msg) {

        StringBuilder sb = new StringBuilder();
        sb.append("0010");
        String binLength = String.format("%9s", Integer.toBinaryString(msg.length())).replaceAll(" ", "0");
        sb.append(binLength);
        List<String> pairs = alphaNumericSeparateGroups(msg);
        pairs.forEach(e -> {
            String binPair = convertToPairAlpha(e);
            sb.append(binPair);
        });
        return sb.toString();
    }

    public static List<String> pairsNumericMode(String msg) {

        ArrayList<String> sb = new ArrayList<>();
        String emptyString = "";
        String[] splitString = msg.split("");
        for (String eachletter: splitString) {
            if (emptyString.length() == 3) {
                sb.add(emptyString);
                emptyString = eachletter;
            } else {
                emptyString += eachletter;
            }
        }
        if (emptyString.length() > 0) {
            sb.add(emptyString);
        }
        return sb;
    }

    public static String numericMode(String msg) {

        List<String> groups = pairsNumericMode(msg);
        StringBuilder sb = new StringBuilder();
        sb.append("0001");
        sb.append(String.format("%10s", Integer.toBinaryString(msg.length())).replace(" ", "0"));
        groups.forEach(e -> {
            int result = Integer.parseInt(e);
            if (result > 99) {
                sb.append(String.format("%10s", Integer.toBinaryString(result)).replace(" ", "0"));
            } else if (result >= 10) {
                sb.append(String.format("%7s", Integer.toBinaryString(result)).replace(" ", "0"));
            } else {
                sb.append(String.format("%4s", Integer.toBinaryString(result)).replace(" ", "0"));
            }
        });
        return sb.toString();
    }

    public static boolean containsAllAlphanumeric(String msg) {
        return Stream.of(msg.split("")).allMatch(e -> alphanumeric.contains(e));
    }

    public static String encode(String message) {

        System.out.println(String.format("Testing: %s\n", message));
        try {
            Double.parseDouble(message);
            return numericMode(message);
        } catch (Exception e) {
            if (containsAllAlphanumeric(message)) {
                // alphanumeric
                return alphaNumericMode(message);
            } else {
                // byte mode
                return byteMode(message);
            }
        }

    }


    public static void main(String[] args) {

        String[] tests = new String[] {"0240758114147728781524", "Hello World"};
        String[] results = new String[] {"0001000001011000110001001011110010101101100111101100000100110110111000100110000100", "0100000010110100100001100101011011000110110001101111001000000101011101101111011100100110110001100100"};

        for (int i = 0; i < tests.length; i++) {
            String result = encode(tests[i]);
            String expected = results[i];
            if (result.equals(expected)) {
                System.out.println(String.format("Test %d: Correct", i+1));
            } else {
                System.out.println(String.format("Test %d: Failed", i+1));
            }
        }

    }

}
