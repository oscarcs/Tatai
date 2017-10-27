package question;

import java.util.HashMap;

/**
 * Used for processing whether the received pronunciation was correct.
 * @author szhu842, osim082
 */
public class Pronunciation {

	private String text;
	private HashMap<Integer, String> numberMap;

	/**
	 * constructor, put the 10 numbers in the hashmap
	 */
	public Pronunciation() {
		numberMap = new HashMap<>();

		// Set up hashMap to retrieve Maori number representations as strings.
		numberMap.put(1, "tahi");
		numberMap.put(2, "rua");
		numberMap.put(3, "toru");
		numberMap.put(4, "whaa");
		numberMap.put(5, "rima");
		numberMap.put(6, "ono");
		numberMap.put(7, "whitu");
		numberMap.put(8, "waru");
		numberMap.put(9, "iwa");
		numberMap.put(10, "tekau");
	}

	/**
	 * Returns the number written in Maori words.
	 * @param number Number that will be converted pronunciation representation.
	 * @return
	 */
	public String getPronunciation(int number) {
		// If is 10 or less we can just return the map value.
		if (number <= 10) {
			text = numberMap.get(number);
		} 
		// If number is from 10 - 19 we return "tekau maa" and the map value.
		else if (number <= 19) {
			text = "tekau maa " + numberMap.get(number % 10);
		} 
		// We split the number into digits and return an answer based off that.
		else {
			int[] num = this.splitNum(number);
			if (num[1] == 0) {
				text = numberMap.get(num[0]) + " tekau";
			} 
			else {
				text = numberMap.get(num[0]) + " tekau maa " + numberMap.get(num[1]);
			}
		}
		return text;
	}

	/**
	 * Gets the components of a split number.
	 * 
	 * @param number
	 * @return
	 */
	public int[] splitNum(int number) {
		if (number > 99) {
			throw new RuntimeException("Number over 100 cannot be parsed.");
		}
		int[] splitNumber = new int[2];
		splitNumber[0] = number / 10;
		splitNumber[1] = number % 10;

		return splitNumber;
	}
}
