package unsw.venues;

import java.util.ArrayList;
import java.util.List;

/**
 * @author z5187767
 * 
 * {@link Venue} is the venue class with has an id and list of {@link Room}s it composes.
 * {@link Room}s are stored in the roomsList;
 * Friends: {@link Room} 
 *
 */
public class Venue {

	private String id;
	private List<Room> roomsList;

	/**
	 * @param id
	 * @param roomID
	 * @param size
	 */
	public Venue(String id, String roomID, String size) {
		this.roomsList = new ArrayList<Room>();
		this.id = id;
		appendRoom(roomID, size);
	}

	/**
	 * @return venue's id
	 */
	public String getID() {
		return id;
	}

	/**
	 * @return list of {@link Room}
	 */
	private List<Room> getRoomsList() {
		return roomsList;
	}
	
	/**
	 * @return list of strings where each element is a roomID
	 */
	public List<String> getRoomListIDs() {
		List<String> roomIDs = new ArrayList<String>();
		for (Room r : getRoomsList()) {
			roomIDs.add(r.getId());
		}
		return roomIDs;
	}
	
	/**
	 * @param roomID
	 * @param sizeValue (expected to be "small", "medium" or "large")
	 * @return true if room is appended to list, false otherwise
	 */
	public boolean appendRoom(String roomID, String sizeValue) {
		return this.roomsList.add(new Room(roomID, Room.roomSizeFromSizeValue(sizeValue)));
	}
	
	/**
	 * @param roomID
	 * @param sizeValue
	 * @return true if room of given id is of the given size, false otherwise
	 */
	public boolean isRoomSize(String roomID, String sizeValue) {
		for (Room room: getRoomsList()) {
			if (room.getId().equals(roomID)) {
				return room.isSize(sizeValue);
			}
		}
		return false;
	}

}
