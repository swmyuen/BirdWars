package birdWars;

import java.util.ArrayList;

public class EventBatch {

	private static ArrayList<Event> batchQueue;
	private int size; 
	
	public EventBatch(){
		batchQueue = new ArrayList<Event>();
		size = 0;
	}
	
	protected void addToBatch(Event e){
		size++;
		batchQueue.add(e);
	}
	
	protected void clearBatch(){
		batchQueue.clear();
		size = 0;
	}

	protected ArrayList<Event> getBatch(){
		return batchQueue; 
	}
	
	protected int getBatchSize(){
		return size;
	}
	
}
