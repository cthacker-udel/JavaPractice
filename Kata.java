import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.stream.*;

import javax.swing.plaf.ColorUIResource;

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

		return Stream.of(str.split(" "))
				.map(e -> e.contains("?") || e.contains("!") || e.contains(",") || e.contains(".") ? e
						: e.substring(1) + e.charAt(0) + "ay")
				.collect(Collectors.joining(" "));

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
		return people.stream()
				.map(e -> String.format("(%s, %s)", e.split(":")[1].toUpperCase(), e.split(":")[0].toUpperCase()))
				.collect(Collectors.joining(""));

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
				parenStack.push(theParen + "");
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
		char specs[] = { 'y', 'd', 'h', 'm', 's' };
		int amts[] = { years, days, hours, minutes, s };
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
					int[] range = new int[] { left, left };
					ranges.add(range);
					range = new int[] { right, right };
					ranges.add(range);
				} else {
					// found new starter
					int[] range = new int[] { left, right };
					ranges.add(range);
				}
				left = currentElem;
				right = currentElem;
			} else {
				right = currentElem;
			}
		}
		if (Math.abs(left - right) == 1) {
			int[] range = new int[] { left, left };
			ranges.add(range);
			range = new int[] { right, right };
			ranges.add(range);
		} else {
			int[] lastRange = new int[] { left, right };
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
		return new String[] { sb1.toString(), sb2.toString() };

	}

	public static long fib(int n) {
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
		String binLength = String.format("%8s", Integer.toBinaryString(msg.length())).replace(" ", "0");
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
			return String.format("%6s", Integer.toBinaryString(alphanumeric.indexOf(pair.charAt(0) + "")))
					.replaceAll(" ", "0");
		} else {
			int firstVal = alphanumeric.indexOf(pair.charAt(0) + "");
			int secondVal = alphanumeric.indexOf(pair.charAt(1) + "");
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
		for (String eachletter : splitString) {
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

	static String toCamelCase(String s) {
		return s.length() > 0 ? s.charAt(0) + Stream.of(s.replaceAll("-", "_").split("_"))
				.map(e -> e.substring(0, 1).toUpperCase() + e.substring(1)).collect(Collectors.joining("")).substring(1)
				: "";
	}

	public static void printSpiral(int[][] spiral) {

		for (int i = 0; i < spiral.length; i++) {
			for (int j = 0; j < spiral.length; j++) {
				if (spiral[i][j] == 0) {
					System.out.printf(".");
				} else {
					System.out.printf("0");
				}
			}
			System.out.println("");
		}
		// debug System.out.println("############");

	}

	public static int[][] spiralize(int size) {

		String[] directions = new String[] { "e", "s", "w", "n" };
		int startingDirectionInd = 0;
		int[][] grid = new int[size][size];
		int row = 0;
		int col = 0;
		boolean outermostLayer = false;
		int movements = 0;
		while (movements < size) {
			// printSpiral(grid);
			String direction = directions[startingDirectionInd];
			movements++;
			switch (direction) {
				case "e": {
					for (; col < grid[row].length; col++) {
						if (col == (grid[row].length) || (outermostLayer && grid[row][col + 1] == 1)) {
							break;
						} else {
							grid[row][col] = 1;
						}
					}
					col--;
					startingDirectionInd++;
					break;
				}
				case "s": {
					for (; row < grid.length; row++) {
						if (row == (grid.length) || (outermostLayer && grid[row + 1][col] == 1)) {
							break;
						} else {
							grid[row][col] = 1;
						}
					}
					row--;
					startingDirectionInd++;
					break;
				}
				case "w": {
					for (; col >= 0; col--) {
						if (col == -1 || (outermostLayer && grid[row][col - 1] == 1)) {
							break;
						} else {
							grid[row][col] = 1;
						}
					}
					col++;
					startingDirectionInd++;
					break;
				}
				case "n": {
					for (; row >= 0; row--) {
						if (row == -1 || grid[row - 1][col] == 1) {
							break;
						} else {
							grid[row][col] = 1;
						}
					}
					row++;
					outermostLayer = true;
					startingDirectionInd = 0;
					break;
				}
			}
		}
		// printSpiral(grid);
		return grid;

	}

	public static String interpret(String... args) {
		String input;
		String code;
		if (args.length < 2) {
			input = "";
			code = args[0];
		} else {
			code = args[0];
			input = args[1];
		}
		ArrayList<Integer> convertedInput = Stream
				.of(
						Stream.of(
								input.replace("\\", "")
										.split("u"))
								.mapToInt(e -> Integer.parseInt(e, 16))
								.mapToObj(e -> Integer.toBinaryString(e))
								.map(e -> "00000000".substring(e.length()) + e)
								.map(e -> new StringBuilder(e).reverse().toString())
								.collect(Collectors.joining("")).split(""))
				.mapToInt(e -> Integer.parseInt(e.charAt(0) + ""))
				.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		while (convertedInput.size() % 8 != 0) {
			convertedInput.add(0);
		}

		ArrayList<Integer> bits = new ArrayList<>(List.of(0));
		// characters are read in using their ascii integer value, not stored as actual
		// characters
		int pointerInd = 0;
		int tapeIndex = code.length() / 2;
		int inputIndex = 0;
		StringBuilder sb = new StringBuilder("");
		while (tapeIndex < code.length()) {

			char theCharacter = code.charAt(tapeIndex);
			switch (theCharacter) {

				case '+': {
					int[] bitsArr = bits.stream().mapToInt(e -> e).toArray();
					bitsArr[pointerInd] = bitsArr[pointerInd] == 0 ? 1 : 0;
					bits = Arrays.stream(bitsArr).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
					tapeIndex++;
					break;
				}
				case ',': {
					int[] bitsArr = bits.stream().mapToInt(e -> e).toArray();
					bitsArr[pointerInd] = (int) convertedInput.get(inputIndex);
					bits = Arrays.stream(bitsArr).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
					tapeIndex++;
					break;
				}
				case 'a': {
					tapeIndex++;
					break;
				}
				case '<': {
					if (pointerInd == 0) {
						bits.add(0, 0);
					} else {
						pointerInd--;
					}
					tapeIndex++;
					break;
				}
				case '>': {
					if (pointerInd == bits.size() - 1) {
						bits.add(0);
					} else {
						pointerInd++;
					}
					tapeIndex++;
					break;
				}
				case ';': {
					sb.append(bits.get(pointerInd).intValue() + "");
					tapeIndex++;
					break;
				}
				case '[': {
					if (bits.get(pointerInd).intValue() == 0) {
						// loop to end
						for (int i = tapeIndex; i < code.length(); i++) {
							if (code.charAt(i) == ']') {
								tapeIndex = i + 1;
								break;
							}
						}
					} else {
						tapeIndex++;
					}
					break;
				}
				case ']': {
					if (bits.get(pointerInd).intValue() == 1) {
						for (int i = tapeIndex; i >= 0; i--) {
							if (code.charAt(i) == '[') {
								tapeIndex = i;
								break;
							}
						}
					} else {
						tapeIndex++;
					}
				}

			}

		}
		if (sb.toString().length() % 8 != 0) {
			while (sb.toString().length() % 8 != 0) {
				sb.append("0");
			}
		}
		return sb.toString();

	}

	public static boolean fieldValidator(int[][] field) {

		int battleship = 0;
		int cruisers = 0;
		int destroyers = 0;
		int submarines = 0;
		int consecSpaces = 0;
		// check corners first
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {

				boolean isValidSquare = field[i][j] == 1;
				if (isValidSquare) {

					// check corner
					// bottom left
					if (i < field.length - 1 && j > 0 && field[i + 1][j - 1] == 1) {
						return false;
					}
					// bottom right
					if (i < field.length - 1 && j < field[0].length - 1 && field[i + 1][j + 1] == 1) {
						return false;
					}
					// upper right
					if (i > 0 && j < field[0].length - 1 && field[i - 1][j + 1] == 1) {
						return false;
					}
					// upper left
					if (i > 0 && j > 0 && field[i - 1][j - 1] == 1) {
						return false;
					}

				}

			}
		}
		// check by rows first
		int extras = 0;
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				try {
					boolean isValidSquare = field[i][j] == 1;
					if (isValidSquare) {

						if (i > 0 && field[i - 1][j] == 1) {
							continue;
						}
						if (i < (field.length - 1) && field[i + 1][j] == 1) {
							continue;
						} else {
							field[i][j] = 0;
							consecSpaces++;
						}

					} else {
						if (consecSpaces > 0) {
							if (consecSpaces > 4) {
								return false;
							} else {
								switch (consecSpaces) {
									case 1: {
										if (submarines < 4) {
											submarines++;
										} else {
											extras++;
										}
										consecSpaces = 0;
										break;
									}
									case 2: {
										if (destroyers < 3) {
											destroyers++;
										} else {
											extras++;
										}
										consecSpaces = 0;
										break;
									}
									case 3: {
										if (cruisers < 2) {
											cruisers++;
										} else {
											extras++;
										}
										consecSpaces = 0;
										break;
									}
									default: {
										if (battleship < 1) {
											battleship++;
										} else {
											extras++;
										}
										consecSpaces = 0;
										break;
									}
								}
							}
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
			if (consecSpaces > 0) {
				if (consecSpaces > 4) {
					return false;
				} else {
					switch (consecSpaces) {
						case 1: {
							if (submarines < 4) {
								submarines++;
							} else {
								extras++;
							}
							consecSpaces = 0;
							break;
						}
						case 2: {
							if (destroyers < 3) {
								destroyers++;
							} else {
								extras++;
							}
							consecSpaces = 0;
							break;
						}
						case 3: {
							if (cruisers < 2) {
								cruisers++;
							} else {
								extras++;
							}
							consecSpaces = 0;
							break;
						}
						default: {
							if (battleship < 1) {
								battleship++;
							} else {
								extras++;
							}
							consecSpaces = 0;
							break;
						}
					}
				}
			}
		}
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				try {
					boolean isValidSquare = field[j][i] == 1;
					if (isValidSquare) {

						if (i > 0 && field[j][i - 1] == 1) {
							continue;
						}
						if (i < field.length - 1 && field[j][i + 1] == 1) {
							continue;
						} else {
							field[j][i] = 0;
							consecSpaces++;
						}

					} else {
						if (consecSpaces > 0) {
							if (consecSpaces > 4) {
								return false;
							} else {
								switch (consecSpaces) {
									case 1: {
										if (submarines < 4) {
											submarines++;
										} else {
											extras++;
										}
										consecSpaces = 0;
										break;
									}
									case 2: {
										if (destroyers < 3) {
											destroyers++;
										} else {
											extras++;
										}
										consecSpaces = 0;
										break;
									}
									case 3: {
										if (cruisers < 2) {
											cruisers++;
										} else {
											extras++;
										}
										consecSpaces = 0;
										break;
									}
									default: {
										if (battleship < 1) {
											battleship++;
										} else {
											extras++;
										}
										consecSpaces = 0;
										break;
									}
								}
							}
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
			if (consecSpaces > 0) {
				if (consecSpaces > 4) {
					return false;
				} else {
					switch (consecSpaces) {
						case 1: {
							if (submarines < 4) {
								submarines++;
							} else {
								extras++;
							}
							consecSpaces = 0;
							break;
						}
						case 2: {
							if (destroyers < 3) {
								destroyers++;
							} else {
								extras++;
							}
							consecSpaces = 0;
							break;
						}
						case 3: {
							if (cruisers < 2) {
								cruisers++;
							} else {
								extras++;
							}
							consecSpaces = 0;
							break;
						}
						default: {
							if (battleship < 1) {
								battleship++;
							} else {
								extras++;
							}
							consecSpaces = 0;
							break;
						}
					}
				}
			}
		}
		return battleship == 1 && cruisers == 2 && destroyers == 3 && submarines == 4 && extras == 0;

	}

	public static String generateCode(String theChars) {

		char theChar = theChars.charAt(0);
		switch (theChar) {
			case 'F': {
				return String.format("<span style=\"color: pink\">%s</span>", theChars);
			}
			case 'L': {
				return String.format("<span style=\"color: red\">%s</span>", theChars);
			}
			case 'R': {
				return String.format("<span style=\"color: green\">%s</span>", theChars);
			}
			default: {
				return String.format("<span style=\"color: orange\">%s</span>", theChars);
			}
		}

	}

	public static String highlight(String code) {

		ArrayList<String> commands = new ArrayList<>();
		String currentCommand = "";
		String validChars = "FLR0123456789";
		String numericals = "0123456789";
		for (int i = 0; i < code.length(); i++) {
			String iCommand = code.charAt(i) + "";
			if (validChars.contains(iCommand)) {

				if (currentCommand.length() > 0) {
					if (!currentCommand.endsWith(iCommand)) {
						if (numericals.contains(iCommand) && numericals.contains(currentCommand.substring(0, 1))) {
							currentCommand += iCommand;
							continue;
						} else {
							commands.add(generateCode(currentCommand));
							currentCommand = iCommand;
						}
					} else {
						currentCommand += iCommand;
					}
				} else {
					currentCommand += iCommand;
				}

			} else {
				if (currentCommand.length() > 0) {
					commands.add(generateCode(currentCommand));
				}
				commands.add(iCommand);
				currentCommand = "";
			}
		}
		if (currentCommand.length() > 0) {
			commands.add(generateCode(currentCommand));
		}
		return String.join("", commands);
	}

	public static int[] divisibleBy(int[] numbers, int divider) {
		return Arrays.stream(numbers).filter(e -> e % divider == 0).toArray();
	}

	public static int Past(int h, int m, int s) {
		return (h * 60 * 60 * 1000) + (m * 60 * 1000) + (s * 1000);
	}

	public static int nthPower(int[] array, int n) {

		return n >= array.length || n < 0 ? -1 : (int) Math.pow(array[n], n);

	}

	public static String replace(final String s) {

		return s.replaceAll("[aeiouAEIOU]", "!");

	}

	public static String noSpace(final String x) {
		return x.trim().replaceAll(" ", "");
	}

	public static int[] invert(int[] array) {

		return Arrays.stream(array).map(e -> e * -1).toArray();

	}

	public static int noBoringZeros(int n) {

		String strNum = String.valueOf(n);
		return strNum.endsWith("0")
				? Integer.parseInt(strNum.substring(0,
						strNum.length() - new StringBuilder(strNum).reverse().toString().indexOf("0")))
				: Integer.parseInt(strNum);

	}

	public static int getTheLongest(char[][] board) {

		System.out.println("printing the array");
		for (int i = 0; i < board.length; i++) {
			System.out.println(Arrays.toString(board[i]));
		}
		System.out.println("");

		int longest = 0;
		// first search rows
		for (int i = 0; i < board.length; i++) {
			char[] row = board[i];
			int cnt = 0;
			char prevChar = ' ';
			for (int j = 0; j < row.length; j++) {
				char currChar = row[j];
				if (currChar != prevChar && cnt > longest) {
					longest = cnt;
					prevChar = currChar;
					cnt = 1;
				} else if (currChar == prevChar) {
					cnt++;
				} else {
					cnt = 1;
					prevChar = currChar;
				}
			}
			if (cnt > longest) {
				longest = cnt;
			}
		}
		if (longest == board.length) {
			return longest;
		}
		// then search columns
		for (int i = 0; i < board.length; i++) {
			ArrayList<Character> col = new ArrayList<>();
			for (int j = 0; j < board.length; j++) {
				col.add(board[j][i]);
			}
			char prevChar = ' ';
			char currChar;
			int cnt = 0;
			for (int x = 0; x < col.size(); x++) {
				currChar = col.get(x);
				if (currChar != prevChar && cnt > longest) {
					longest = cnt;
					prevChar = currChar;
					cnt = 1;
				} else if (currChar == prevChar) {
					cnt++;
				} else {
					cnt = 1;
					prevChar = currChar;
				}
			}
			if (cnt > longest) {
				longest = cnt;
			}
			col.clear();
		}
		if (longest == board.length) {
			return longest;
		}
		// then search diagonals, start with left -> right
		for (int i = 0; i < board.length; i++) {
			char prevChar = ' ';
			char currChar;
			int cnt = 0;
			for (int j = i; j < board.length; j++) {
				currChar = board[j][j];
				if (currChar != prevChar && cnt > longest) {
					longest = cnt;
					prevChar = currChar;
					cnt = 1;
				} else if (currChar == prevChar) {
					cnt++;
				} else {
					cnt = 1;
					prevChar = currChar;
				}
			}
			if (cnt > longest) {
				longest = cnt;
			}
			prevChar = ' ';
			cnt = 0;
			for (int j = i, x = 0; x < board.length && j < board.length; j++, x++) {
				currChar = board[j][x];
				if (currChar != prevChar && cnt > longest) {
					longest = cnt;
					prevChar = currChar;
					cnt = 1;
				} else if (currChar == prevChar) {
					cnt++;
				} else {
					cnt = 1;
					prevChar = currChar;
				}
			}
			if (cnt > longest) {
				longest = cnt;
			}
		}
		if (longest == board.length) {
			return longest;
		}
		// then search diagonals, right -> left
		for (int i = 0; i < board.length; i++) {
			char prevChar = ' ';
			char currChar;
			int cnt = 0;
			for (int j = i; j >= 0; j--) {
				currChar = board[j][j];
				if (currChar != prevChar && cnt > longest) {
					longest = cnt;
					prevChar = currChar;
					cnt = 1;
				} else if (currChar == prevChar) {
					cnt++;
				} else {
					cnt = 1;
					prevChar = currChar;
				}
			}
			if (cnt > longest) {
				longest = cnt;
			}
			prevChar = ' ';
			cnt = 0;
			for (int j = i, x = 0; x >= 0 && j >= 0; j--, x--) {
				currChar = board[j][x];
				if (currChar != prevChar && cnt > longest) {
					longest = cnt;
					prevChar = currChar;
					cnt = 1;
				} else if (currChar == prevChar) {
					cnt++;
				} else {
					cnt = 1;
					prevChar = currChar;
				}
			}
			if (cnt > longest) {
				longest = cnt;
			}
		}
		return longest;
	}

	public static String Explode(String s) {

		return Arrays.stream(s.split("")).map(e -> String.valueOf(e).repeat(Integer.parseInt(e)))
				.collect(Collectors.joining(""));

	}

	class Node {
		public Object data;
		public Node next;
	}

	static int lastIndexOf(Node head, Object value) {
		if (head == null) {
			return -1;
		}
		ArrayList<Integer> indexes = new ArrayList<>();
		Node tempHead = head;
		int index = 0;
		while (tempHead != null) {
			if (tempHead.data.equals(value)) {
				indexes.add(index);
			}
			index++;
			tempHead = tempHead.next;
		}
		return indexes.size() > 0 ? indexes.get(indexes.size() - 1) : -1;
	}

	public static boolean isNice(Integer[] arr) {
		if (arr.length < 2) {
			return false;
		}
		Collection<Integer> intList = Arrays.stream(arr).collect(Collectors.toCollection(ArrayList<Integer>::new));
		return Arrays.stream(arr).allMatch(
				e -> Collections.frequency(intList, e - 1) >= 1 || Collections.frequency(intList, e + 1) >= 1);
	}

	public static boolean contain_all_rots(String str, String[] arr) {

		List<String> strList = Arrays.asList(arr);
		for (int i = 0; i < str.length(); i++) {
			String left = str.substring(0, i);
			String right = str.substring(i);
			if (!strList.contains(left + right)) {
				return false;
			}
		}
		return true;

	}

	// class Node<T> {
	// public T data;
	// public Node<T> next;

	// Node(T data, Node next) {
	// this.data = data;
	// this.next = next;
	// }

	// Node(T data) {
	// this(data, null);
	// }
	// }

	// static <T> T reduce(Node<T> head, BiFunction<T, T, T> f, T init) {
	// Node<T> tempHead = head;
	// T total = init;
	// while (tempHead != null) {
	// total = f.apply(total, tempHead.data);
	// tempHead = tempHead.next;
	// }
	// return total;
	// }

	public static String stringMerge(String s1, String s2, char letter) {

		return s1.substring(0, s1.indexOf(letter)) + s2.substring(s2.lastIndexOf(letter) + 1);

	}

	public static int binToDecimal(String inp) {

		long total = 0;
		int index = inp.length() - 1;
		int currentPower = 0;
		while (index >= 0) {
			if (inp.charAt(index) == '1') {
				System.out.printf("Current power before = %d\n", currentPower);
				total += (long) Math.pow(2, currentPower);
				System.out.printf("Total = %d -- and currPower = %d\n", total, currentPower);
			}
			index -= 1;
			currentPower += 1;
		}
		return (int) total;

	}

	public static int factorial(int n) {
		if (n < 0 || n > 12) {
			throw new IllegalArgumentException();
		} else {
			return n != 0 ? IntStream.range(1, n + 1).reduce((int a1, int a2) -> a1 * a2).getAsInt() : 1;
		}
	}

	public static int oppositeHouse(int house1, int streetLength) {

		return (2 * streetLength + 1 - house1);

	}

	public static int strCount(String str, char letter) {
		return Arrays.stream(str.split("")).mapToInt(e -> e.charAt(0) == letter ? 1 : 0).sum();
	}

	public static long thirt(long n) {

		ArrayList<Long> numbers = new ArrayList<>(List.of(n));
		int sequence[] = new int[] { 1, 10, 9, 12, 3, 4 };
		int ind = 0;
		long newNum = 0;
		while (numbers.indexOf(n) == numbers.lastIndexOf(n)) {
			String revNum = new StringBuilder(n + "").reverse().toString();
			int[] digits = Arrays.stream(revNum.split("")).mapToInt(e -> Integer.parseInt(e)).toArray();
			for (int i = 0; i < digits.length; i++) {
				newNum += digits[i] * sequence[ind];
				ind++;
				if (ind == 6) {
					ind = 0;
				}
			}
			ind = 0;
			numbers.add(newNum);
			n = newNum;
			newNum = 0;
		}
		return n;

	}

	public static int[] incrementer(int[] numbers) {
		return IntStream.range(0, numbers.length).map(e -> numbers[e] + e + 1).map(e -> e >= 10 ? e % 10 : e).toArray();
	}

	public static int hexToDec(final String hexString) {
		return Integer.parseInt(hexString, 10);
	}

	public static boolean isDigit(String s) {
		return Pattern.matches("\\d{1}", s);
	}

	public static int expressionsMatter(int a, int b, int c) {
		int[] results = new int[]{
			a + b + c,
			a * b + c,
			a + b * c
		};
		Arrays.sort(results);
		return results[2];
	}

	public static void main(String[] args) {

		long start = System.currentTimeMillis();

		System.out.println(Arrays.toString(incrementer(new int[]{5, 8, 0, 5, 8})));

		long elapsed = System.currentTimeMillis() - start;
		System.out.printf("\nElapsed time = %d seconds\n", elapsed / 1000);

	}

}
