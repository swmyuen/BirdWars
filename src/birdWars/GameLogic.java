package birdWars;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class to handle the main logic to handle how the events should be written to 
 * the EventBatch. This implementation assumes that there will ever only be one 
 * EventBatch.
 */
public class GameLogic {
	
	// Make these non-static assuming that this class will ever get invoked once
	EventBatch eventBatch; 
	private Timer timer = null;
	// To keep track of the most recent event date stamp in the form of 
	// YYYY-MM-DD
	private String lastDateStamp; 
	
	/**
	 * Constructor for the GameLogic class
	 */
	public GameLogic(){
		eventBatch = new EventBatch();
		lastDateStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		// Starts the 10 second timer.
		resetTimer();
	}

	/**
	 * Timer to handle the 10 second EventBatch lifetime. The flushEventBatch 
	 * method is scheduled to be called every 10 seconds. 
	 */
	private void resetTimer() {
		TimerTask timerTask = new TimerTask(){

			// Every 10 seconds call the flushEventBatch method if the other 
			// have not been reached yet
			@Override
			public void run() {
				flushEventBatch(eventBatch);
			}
			
		};
		
		// If the timer has already been created then reset the timer 
		if(timer != null){
			timer.cancel();
			timer.purge();
		}
		
		// Start a new timer and schedule the timer task to occur in 10 seconds
		timer = new Timer();
		timer.schedule(timerTask, 10000);
	}
	
	/**
	 * Writes the the event to the batch appropriately. If the date has 
	 * changed since the last event or the batch size has reached 20 then flush
	 * the EventBatch appropriately. Note that the 10 second event batch life 
	 * time is handled in the timer.
	 * 
	 * @param e Event to be written to the EventBatch
	 */
	public void write(Event e){
		
		/* If the day has changed since the last event, this could happen 
		 when an event batch starts on 23:59:55 and ends the next day 
		 on 00:00:05. This should be written on a different table and the 
		 EventBatch should be flushed 
		 
		 else if the batch size reaches 20 then print and flush eventBatch
		 
		 else just add the event into the current batch.
		 */
		if(!getDateStamp(new Date(e.getTimestamp())).equals(lastDateStamp)){
			flushEventBatch(this.eventBatch);
			
			eventBatch.addToBatch(e);
		} else if(eventBatch.getBatchSize() == 19){
			eventBatch.addToBatch(e);
			
			flushEventBatch(this.eventBatch);
		} else {
			eventBatch.addToBatch(e);
		}
	}
	
	/**
	 * Helper function to get the date timestamp from the UNIX time stamp that 
	 * was received.
	 * 
	 *  @param date Date formated as a UNIX timestamp.
	 *  
	 *  @return		The date in the format YYYY-MM-DD
	 */
	private String getDateStamp(Date date){
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	/**
	 * Helper function to flush the event batch. It calls a method to write the
	 * events to the database, clears the EventBatch, and resets the 10 second
	 * timer
	 * 
	 * @param e EventBatch to be flushed.
	 */
	private void flushEventBatch(EventBatch e){
		// Write the event data 
		VpsDB.writeBatch(e, getDateStamp(new Date()));
		
		// Clear the arraylist and set the size to zero
		e.clearBatch();

		// Reset the 10 second timer. It is effectively spawning a new batch of 
		// events so its 'life timer' should be reset.
		resetTimer();
	}

}
