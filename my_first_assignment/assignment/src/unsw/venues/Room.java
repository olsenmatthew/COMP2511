package unsw.venues;

/**
 * @author z5187767
 * 
 * {@link Size} is a friend of {@link Room}. Size is used to determine the sizes of the {@link Room}.
 *
 */
public class Room {

	private String id;
	private Size size;

	/**
	 * @param id
	 * @param size
	 */
	public Room(String id, Size size) {
		super();
		this.id = id;
		this.size = size;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param sizeValue
	 * @return true if the room size is of the given size
	 */
	public boolean isSize(String sizeValue) {
		return this.size.getValue().toLowerCase().equals(sizeValue.toLowerCase());
	}
	
	/**
	 * @param sizeValue (expected to be "small", "medium", or "large")
	 * @return Size of room
	 */
	public static Size roomSizeFromSizeValue(String sizeValue) {
		return Size.constructorFromValue(sizeValue);
	}

}
