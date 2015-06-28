package birdWars;

import java.util.ArrayList;

/**
 * Stubbed out VpsDB class which contains a stubbed out writeBatch method which 
 * currently just prints out the event information.
 *
 */
public class VpsDB {
	/**
	 * Stubbed-out method which is supposed to 'write to the database'. However
	 * this coding challenge it only prints out the EventIds 
	 * 
	 * @param eventBatch EventBatch with an arraylist that contains the Events 
	 * 					 to be written to the DB
	 * @param table_name The name of the table in the form YYYY-MM-DD 
	 */
	public static void writeBatch(EventBatch eventBatch, String table_name){
		ArrayList<Event> batch = eventBatch.getBatch();
		
		// If the 10 second life time timer has run out, the arraylist could be 
		// empty. Check for empty arraylist for safety.
		if(batch.isEmpty()){
			return;
		}
	
		// Printed the timestamp and message as well as the UUID for testing 
		// purposes. It's easier to tell the difference between messages than 
		// comparing UUIDs.
		for(Event e : batch){
			System.out.println("UUID: " + e.getID() + 
					" timestamp: " + e.getTimestamp() + 
					" message: " + e.getMessage());
		}
		
	}

}
