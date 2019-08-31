package unsw.venues;

import java.time.LocalDate;
import java.util.List;

/**
 * @author z5187767
 * 
 * {@link Reservation} has:
 * 		its own reservationID, 
 * 		start and end dates, 
 * 		an associated venueID 
 * 		and a list of associated roomIDs.
 * 
 * {@link Reservation} does not have any friend classes.
 * 
 */
public class Reservation {

	private String reservationID;
	private LocalDate startDate;
	private LocalDate endDate;
	private String venueID;
	private List<String> roomIDs;

	/**
	 * @param reservationID
	 * @param startDate
	 * @param endDate
	 * @param venueID
	 * @param roomIDs
	 */
	public Reservation(String reservationID, LocalDate startDate, LocalDate endDate, String venueID,
			List<String> roomIDs) {
		this.reservationID = reservationID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.venueID = venueID;
		this.roomIDs = roomIDs;
	}

	/**
	 * @return id of the reservation instance
	 */
	public String getReservationID() {
		return reservationID;
	}

	/**
	 * @return endDate associated with the reservation instance
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * @return venueID associated with the reservation instance
	 */
	public String getVenueID() {
		return venueID;
	}

	/**
	 * @return list of strings where each element is a roomID associated with the reservation instance
	 */
	public List<String> getRoomIDs() {
		return roomIDs;
	}

	/**
	 * @param id
	 * @return true if reservation is associated with given roomID, false otherwise
	 */
	public boolean hasRoomID(String id) {
		return this.getRoomIDs().contains(id);
	}

	/**
	 * @return startDate of reservation
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

}
