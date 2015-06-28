package birdWars;

public class Event {
	private String ID;
	private Long timestamp;
	private String message; 
	
	public Event(String ID, Long timestamp, String message) {
		this.ID = ID;
		this.timestamp = timestamp;
		this.message = message;
	}

	public String getID() {
		return ID;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}
}
