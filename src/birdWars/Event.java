package birdWars;

/**
 * Container for an Event. Stores ID, Timestamp and Message of the event
 */
public class Event {
	// These were made private because we should not let outer entities modify 
	// these fields because this will eventually be 'written' to the DB. However
	// there are getters to access and view these fields.
	private String ID;
	private Long timestamp;
	private String message; 
	
	/**
	 * Constructor for the event 
	 * @param ID id of the event, generally a UUID as a string.
	 * @param timestamp UNIX timestamp.
	 * @param message String message associated with the event. 
	 */
	public Event(String ID, Long timestamp, String message) {
		this.ID = ID;
		this.timestamp = timestamp;
		this.message = message;
	}

	/**
	 * Returns events id in the form of a string 
	 * @return String id 
	 */
	public String getID() {
		return ID;
	}

	/**
	 * Returns the timestamp of the event. It should be a UNIX timestamp
	 * @return Long representation of the UNIX timestamp.
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * Returns the message of the event. 
	 * @return String message associated with the event
	 */
	public String getMessage() {
		return message;
	}
}
