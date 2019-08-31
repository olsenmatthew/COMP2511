package unsw.venues;

/**
 * 
 * @author z5187767
 * 
 * {@link Size} is an enumeration which associated a size (S/M/L) with a
 * corresponding value ("small"/"medium"/"large").
 *
 */
public enum Size {

	S("small"), M("medium"), L("large");

	/**
	 * attribute which stores data pertaining to the String value associated with
	 * each enum.
	 */
	private String value;

	/**
	 * @param value
	 */
	Size(String value) {
		this.value = value;
	}

	/**
	 * 
	 * @param value
	 * @return Size of correct type if string is a known value, else null
	 */
	public static Size constructorFromValue(String value) {
		value = value.toLowerCase();
		for (Size size : Size.values()) {
			if (size.getValue().equals(value)) {
				return size;
			}
		}
		return null;
	}

	/**
	 * @return value
	 */
	public String getValue() {
		return this.value;
	}

}
