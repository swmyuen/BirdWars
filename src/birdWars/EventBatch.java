package birdWars;

import java.util.ArrayList;

/**
 * Class to batch a set of events together. It contains an ArrayList of Events 
 * as a member. 
 * 
 * This class could implement a List interface and thus eliminating
 * the need for other places in the code to call the getBatch. However for the 
 * purposes of this coding challenge there is minimal functionality in the List 
 * interface that is actually used. For this reason I choose not to write this 
 * class to implement an arraylist because it would add a lot method stubs that 
 * would increase the code verbosity and make it difficult to read. 
 * 
 *
 */
public class EventBatch {

	private ArrayList<Event> batchQueue;
	private int size; 
	
	/**
	 * Constructor for the EventBatch.
	 */
	public EventBatch(){
		batchQueue = new ArrayList<Event>();
		size = 0;
	}
	
	/**
	 * Add to the array list storing the events in this EventBatach.
	 * 
	 * @param e Event to be placed into the EventBatch 
	 */
	protected void addToBatch(Event e){
		size++;
		batchQueue.add(e);
	}
	
	/**
	 * Reset the arraylist containing the batch of events.
	 */
	protected void clearBatch(){
		batchQueue.clear();
		
		size = 0;
	}

	/**
	 * Get the arraylist of events.
	 * 
	 * @return ArrayList<Event> represents the current EventBatch
	 */
	public ArrayList<Event> getBatch(){
		return batchQueue; 
	}
	
	/**
	 * Get the current size of the arraylist storing the events.
	 * 
	 * @return Current size of the EventBatch
	 */
	public int getBatchSize(){
		return size;
	}
	
}
