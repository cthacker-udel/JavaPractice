import java.util.*;
import java.util.stream.*;

public class PhoneNumberMerger {

	public static final String OLD_NUMBER_HEADER = "--------------- OLD NUMBERS ---------------";
	public static final String NEW_NUMBER_HEADER = "=============== NEW NUMBERS ===============";

	public static void printPhone(PhoneNumber number) {
		System.out.println(String.format("TYPE : %s | NUMBER : %s", number.type, number.number));
	}

	List<PhoneNumber> merge(List<PhoneNumber> oldNumbers, List<PhoneNumber> newNumbers) {
		System.out.println(OLD_NUMBER_HEADER);
		oldNumbers.forEach(e -> printPhone(e));
		System.out.println(NEW_NUMBER_HEADER);
		newNumbers.forEach(e -> printPhone(e));
		List<PhoneNumber> mergedList = new ArrayList<>();
		ArrayList<String> types = new ArrayList<>();
		for (PhoneNumber eachNumber : oldNumbers) {
			if (types.contains(eachNumber.type)) {
				continue;
			} else {
				mergedList.add(eachNumber);
				types.add(eachNumber.type);
			}
		}
		for (PhoneNumber eachNumber: newNumbers) {
			if (types.contains(eachNumber.type)) {
				// find index
				PhoneNumber[] phoneNumbers = mergedList.toArray(PhoneNumber[]::new);
				int ind = IntStream.range(0,phoneNumbers.length).filter(e -> phoneNumbers[e].type == eachNumber.type).findFirst().getAsInt();
				phoneNumbers[ind] = eachNumber;
				mergedList = List.of(phoneNumbers);
			} else {
				types.add(eachNumber.type);
				mergedList.add(eachNumber);
			}
		}
		return mergedList;
	}

	
    static class PhoneNumber {

        final String type;
        final String number;

        PhoneNumber(String type, String number) {
            this.type = type;
            this.number = number;
        }
    }


}
