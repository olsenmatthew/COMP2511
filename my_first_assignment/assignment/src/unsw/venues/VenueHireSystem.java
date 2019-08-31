/**
 * 
 */
package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Venue Hire System for COMP2511.
 * 
 * @author z5187767
 * Friends: {@link Venue} {@link Reservation}
 * {@link Venue}s are stored in the venueList.
 * {@link Reservation}s are stored in the reservationList.
 * 
 * Tests are located in the test folder, input#.json corresponds to output#.json.
 */

public class VenueHireSystem {
	private List<Reservation> reservationList;
	private List<Venue> venueList;

	public VenueHireSystem() {
		this.reservationList = new ArrayList<Reservation>();
		this.venueList = new ArrayList<Venue>();
	}

	/**
	 * @param json
	 */
	private void processCommand(JSONObject json) {
		JSONObject result = null;
		switch (json.getString("command")) {
		case "room":
			processCommandRoom(json);
			break;
		case "request":
			result = processCommandRequest(json);
			break;
		case "cancel":
			result = processCommandCancel(json);
			break;
		case "change":
			result = processCommandChange(json);
			break;
		case "list":
			JSONArray listResult = processCommandList(json);
			if (listResult != null) {
				System.out.println(listResult.toString(2));
			}
			break;
		default:
			break;
		}
		if (result != null) {
			System.out.println(result.toString(2));
		}
	}

	/**
	 * @param json (expected: { "command": "room", "venue": venue, "room": room, "size": size })
	 */
	private void processCommandRoom(JSONObject json) {
		String venue = json.getString("venue");
		String room = json.getString("room");
		String size = json.getString("size");
		if (!addRoom(venue, room, size)) {
			System.out.println(generateRejectedResponse().toString(2));
		}
	}

	/**
	 * @param json 
	 * 			expected: { 
	 * 						"command": "request", 
	 * 						"id": id, 
	 * 						"start": startdate, 
	 * 						"end": enddate, 
	 * 						"small": small, 
	 * 						"medium": medium, 
	 * 						"large": large 
	 * 					}
	 * @return JSONObject either success Request response or standard rejected response
	 */
	private JSONObject processCommandRequest(JSONObject json) {
		String id = json.getString("id");
		LocalDate start = LocalDate.parse(json.getString("start"));
		LocalDate end = LocalDate.parse(json.getString("end"));
		int small = json.getInt("small");
		int medium = json.getInt("medium");
		int large = json.getInt("large");

		Reservation reservation = getFirstAvailableReservation(id, start, end, small, medium, large);

		if (addToReservationList(reservation)) {
			return generateRequestResponse(reservation.getVenueID(), reservation.getRoomIDs());
		}

		return generateRejectedResponse();
	}

	/**
	 * @param json
	 * @return JSONObject either success request response or rejected response
	 */
	private JSONObject processCommandChange(JSONObject json) {

		String id = json.getString("id");
		LocalDate start = LocalDate.parse(json.getString("start"));
		LocalDate end = LocalDate.parse(json.getString("end"));
		int small = json.getInt("small");
		int medium = json.getInt("medium");
		int large = json.getInt("large");

		Reservation initialReservation = getReservationByID(id, getReservationList());
		if(initialReservation != null) {
			removeFromReservationList(id);
			Reservation newReservation = getFirstAvailableReservation(id, start, end, small, medium, large);
			if (addToReservationList(newReservation)) {
				return generateRequestResponse(newReservation.getVenueID(), newReservation.getRoomIDs());
			}
			
			addToReservationList(initialReservation);
		}

		return generateRejectedResponse();

	}

	/**
	 * @param json
	 * @return JSONObject {"status": "success"} or {"status": "rejected"}
	 */
	private JSONObject processCommandCancel(JSONObject json) {
		String id = json.getString("id");
		if (removeFromReservationList(id)) {
			return generateSuccessResponse();
		}

		return generateRejectedResponse();
	}

	/**
	 * @param json (expected: { "command": "list", "venue": venue })
	 * @return JSONArray ListResponse for given venueID and system's current reservations
	 */
	private JSONArray processCommandList(JSONObject json) {
		
		String venueID = json.getString("venue");
		
		if (!venueExists(venueID, getVenueList())) {
			return generateListResponse(new ArrayList<String>(), new ArrayList<Reservation>());
		}
		
		Venue venue = getVenueByID(venueID);
		
		List<Reservation> reservations = new ArrayList<Reservation>();
		for (Reservation r : getReservationList()) {
			if (r.getVenueID().equals(venueID)) {
				reservations.add(r);
			}
		}
		
		reservations.sort((x, y) -> {
			if (x.getStartDate().equals(y.getStartDate())) {
				return 0;
			}
			return (x.getStartDate().isBefore(y.getStartDate())) ? -1 : 1;
		});
		
		return generateListResponse(venue.getRoomListIDs(), reservations);
	}

	/**
	 * @param id
	 * @param vList
	 * @return true if a venue in the given venue list has a matching id as the provided, false otherwise.
	 */
	private boolean venueExists(String id, List<Venue> vList) {
		for (Venue venue : vList) {
			if (venue.getID().equals(id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param venueID
	 * @param roomID
	 * @param size
	 * @return true if either:
	 * 			a new venue is created and added to the system's venueList
	 * 			or an already existing venue in the venue list appends a new room
	 */
	private boolean addRoom(String venueID, String roomID, String size) {
		if (venueExists(venueID, getVenueList())) {
			for (Venue v : getVenueList()) {
				if (v.getID().equals(venueID)) {
					return v.appendRoom(roomID, size);
				}
			}
			return false;
		} else {
			Venue v = new Venue(venueID, roomID, size);
			return addToVenueList(v);
		}
	}
	
	/**
	 * @param venueID
	 * @param roomID
	 * @param start
	 * @param end
	 * @param rList
	 * @return true if the room in the venue is not reserved given the specified dates
	 */
	private boolean isRoomAvailable(String venueID, String roomID, LocalDate start, LocalDate end, List<Reservation> rList) {
		for (Reservation r : rList) {
			if (r.getVenueID().equals(venueID) && r.hasRoomID(roomID)
					&& !(r.getStartDate().isAfter(end) || r.getEndDate().isBefore(start))) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * @param venue
	 * @param start
	 * @param end
	 * @param size
	 * @param count
	 * @return true if the given venue has enough rooms available for the given dates, size and count per size, false otherwise.
	 */
	private boolean isVenueAvailable(Venue venue, LocalDate start, LocalDate end, String size, int count) {
		if (venue == null) {
			return false;
		}
		
		List<String> roomIDsList = new ArrayList<String>();
		for(String roomID: venue.getRoomListIDs()) {
			if (isRoomAvailable(venue.getID(), roomID, start, end, getReservationList()) 
					&& venue.isRoomSize(roomID, size)) {
				roomIDsList.add(roomID);
			}
		}
		
		return roomIDsList.size() >= count;
	}

	/**
	 * @param id
	 * @return {@link Venue} with matching id from venues list
	 */
	private Venue getVenueByID(String id) {
		for (Venue v : getVenueList()) {
			if (id.equals(v.getID())) {
				return v;
			}
		}
		return null;
	}

	/**
	 * @param id
	 * @param rList
	 * @return {@link Reservation} with matching ID or null if none match
	 */
	private Reservation getReservationByID(String id, List<Reservation> rList) {
		for (Reservation r : rList) {
			if (r.getReservationID().equals(id)) {
				return r;
			}
		}
		return null;
	}

	/**
	 * @param venue
	 * @param start
	 * @param end
	 * @param small
	 * @param medium
	 * @param large
	 * @return list of first available room ids (given venue, dates and room size quantities)
	 */
	private List<String> getFirstAvailableRoomsIDs(Venue venue, LocalDate start, LocalDate end, int small, int medium,
			int large) {

		if (venue == null) {
			return null;
		}

		List<String> roomIDsList = new ArrayList<String>();
		for (String roomID: venue.getRoomListIDs()) {
			if (isRoomAvailable(venue.getID(), roomID, start, end, getReservationList())) {
				roomIDsList.add(roomID);
			}
		}
		
		List<String> result = new ArrayList<String>();
		for (String roomID: roomIDsList) {
			if (small > 0 && venue.isRoomSize(roomID, "small")) {
				result.add(roomID);
				small--;
			} else if (medium > 0 && venue.isRoomSize(roomID, "medium")) {
				result.add(roomID);
				medium--;
			} else if (large > 0 && venue.isRoomSize(roomID, "large")) {
				result.add(roomID);
				large--;
			}
		}
		
		return result;

	}

	/**
	 * @param start
	 * @param end
	 * @param small
	 * @param medium
	 * @param large
	 * @param vList
	 * @return first available venue (given date, room size quantity and list of venue parameters)
	 */
	private Venue getFirstAvailableVenue(LocalDate start, LocalDate end, int small, int medium, int large, List<Venue> vList) {
		for (Venue v : vList) {
			if (isVenueAvailable(v, start, end, "small", small) && isVenueAvailable(v, start, end, "medium", medium)
					&& isVenueAvailable(v, start, end, "large", large)) {
				return v;
			}
		}
		return null;
	}

	/**
	 * @param id
	 * @param start
	 * @param end
	 * @param small
	 * @param medium
	 * @param large
	 * @return first {@link Reservation} that is available 
	 * 			(i.e. has sufficient vacant rooms in a single venue of certain size and date parameters)
	 */
	private Reservation getFirstAvailableReservation(String id, LocalDate start, LocalDate end, int small, int medium,
			int large) {
		Venue venue = getFirstAvailableVenue(start, end, small, medium, large, getVenueList());
		if (venue == null) {
			return null;
		}
		List<String> roomIDs = getFirstAvailableRoomsIDs(venue, start, end, small, medium, large);

		return new Reservation(id, start, end, venue.getID(), roomIDs);
	}

	/**
	 * @param reservation
	 * @return true if given {@link Reservation} is added to reservationList, false otherwise.
	 */
	private boolean addToReservationList(Reservation reservation) {
		if (reservation != null) {
			return this.reservationList.add(reservation);
		}
		return false;
	}
	
	/**
	 * @return list of {@link Venue} (this.venueList)
	 */
	private List<Venue> getVenueList() {
		return this.venueList;
	}
	
	/**
	 * @return list of {@link Reservation} (this.reservationList)
	 */
	private List<Reservation> getReservationList() {
		return this.reservationList;
	}
	
	/**
	 * @param venue
	 * @return true if the given {@link Venue} is added to the venue list, false otherwise.
	 */
	private boolean addToVenueList(Venue venue) {
		if (venue == null) {
			return false;
		}
		return this.venueList.add(venue);
	}
	
	/**
	 * @param id
	 * @return true if a {@link Reservation} with the given id is removed, otherwise false.
	 */
	private boolean removeFromReservationList(String id) {
		return this.reservationList.removeIf((r) -> (r.getReservationID().equals(id)));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VenueHireSystem system = new VenueHireSystem();

		Scanner sc = new Scanner(System.in);

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (!line.trim().equals("")) {
				JSONObject command = new JSONObject(line);
				system.processCommand(command);
			}
		}
		sc.close();
		
	}
	
	/**
	 * @return {"status": "rejected"}
	 */
	private static JSONObject generateRejectedResponse() {
		JSONObject result = new JSONObject();
		result.put("status", "rejected");
		return result;
	}

	/**
	 * @param venueID
	 * @param roomIDs
	 * @return JSONObject {"venue": "id", "rooms" : ["roomID"]}
	 */
	private static JSONObject generateRequestResponse(String venueID, List<String> roomIDs) {
		JSONObject result = generateSuccessResponse();
		result.put("venue", venueID);
		JSONArray rooms = new JSONArray();
		for (String rID : roomIDs) {
			rooms.put(rID);
		}
		result.put("rooms", rooms);
		return result;
	}

	/**
	 * @return JSONObject {"status": "success"}
	 */
	private static JSONObject generateSuccessResponse() {
		JSONObject result = new JSONObject();
		result.put("status", "success");
		return result;
	}

	/**
	 * @param roomIDsList
	 * @param reservations
	 * @return JSONArray generated from given parameters
	 */
	private static JSONArray generateListResponse(List<String> roomIDsList, List<Reservation> reservations) {
		JSONArray result = new JSONArray();
		
		for(String roomID: roomIDsList) {
			JSONObject roomJSON = new JSONObject();
			roomJSON.put("room", roomID);
			JSONArray reservationsJSON = new JSONArray();
			for (Reservation r : reservations) {
				for (String rID : r.getRoomIDs()) {
					if (roomID.equals(rID)) {
						JSONObject reservationInfo = new JSONObject();
						reservationInfo.put("id", r.getReservationID());
						reservationInfo.put("start", r.getStartDate().toString());
						reservationInfo.put("end", r.getEndDate().toString());
						reservationsJSON.put(reservationInfo);
					}
				}
			}
			roomJSON.put("reservations", reservationsJSON);
			result.put(roomJSON);
		}
		
		return result;
	}

}
