package utils;

public class Utils {

	public static <T> T[][] clone(T[][] array) {
		T[][] clonedArray = array.clone();
		for (int i = 0; i < array.length; i++) {
			clonedArray[i] = array[i].clone();
		}
		return clonedArray;
	}

	private Utils() {}

}
