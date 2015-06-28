package birdWarsTest;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import birdWars.Event;
import birdWars.GameLogic;

public class Tests {
	GameLogic game;
	
	@Before
	public void setup(){
		game = new GameLogic();
	}

	
	@Test
	public void testTimerTimeout() throws InterruptedException{
		Event e1 = new Event(genUuid(), getCurrentTime(), genMessage());
		Event e2 = new Event(genUuid(), getCurrentTime(), genMessage());
		
		game.resetTimer();
		game.write(e1);
		
		// Check to see that an event was added correctly.
		assertEquals(1, game.getEventBatch().getBatchSize());
		
		// Wait for life time timer to time out 
		Thread.sleep(10500);
		
		// Check to see that an event has been flushed out.
		assertEquals(0, game.getEventBatch().getBatchSize());
		
		game.write(e2);
		
		// Check to see that we can still write to the EventBatch
		assertEquals(1, game.getEventBatch().getBatchSize());
		
		// Wait another 10  seconds to see if the event batch has been
		// successfully flushed again
		Thread.sleep(10500);
		
		assertEquals(0, game.getEventBatch().getBatchSize());
	}
	
	@Test 
	public void testTwentyLimit() {
		// Reset the game
		game.resetTimer();
		
		// Write in 20 events and make sure that it doesn't flush until it 
		// reaches 20
		for(int i = 0; i < 20; i++){
			assertEquals(i , game.getEventBatch().getBatchSize());
			
			game.write(new Event(genUuid(), getCurrentTime(), genMessage()));
		}
		
		// Check to make sure that there are no events left in the batch
		assertEquals(0, game.getEventBatch().getBatchSize());
	}
	
	@Test 
	public void testDateChange() throws InterruptedException{
		// Events with the current date 
		Event e1 = new Event(genUuid(), getCurrentTime(), genMessage());
		Event e2 = new Event(genUuid(), getCurrentTime(), genMessage());
		
		// Event occurring on the next day
		Date date = new Date(new Date().getTime() + 24 * 60 * 60 * 1000);
		Event e3 = new Event(genUuid(),  date.getTime() , genMessage());
		
		// Reset game timer
		game.resetTimer();
		
		// Check to make sure that theres nothing currently in the Event Batch
		assertEquals(0, game.getEventBatch().getBatchSize());
		
		game.write(e1);
		game.write(e2);
		
		// Check that there are only 2 events in the batch
		assertEquals(2, game.getEventBatch().getBatchSize());
		
		// Write in the new event with a different timestamp. This means the 
		// events from before should be flushed out and written to the DB and 
		// e3 should be the only one left in the batch.
		game.write(e3); 
		
		// Check that there is indeed only one event left in the event batch
		assertEquals(1, game.getEventBatch().getBatchSize());
		
		// Wait 10 seconds to ensure that this last event still gets flushed, 
		// this also checks to make sure that the timer has reset correctly.
		Thread.sleep(10500);
		
		// Check that at the end there are no more events left in the batch
		assertEquals(0, game.getEventBatch().getBatchSize());
	}
	
	private String genUuid(){
		return UUID.randomUUID().toString();
	}
	
	private Long getCurrentTime(){
		return System.currentTimeMillis(); 
	}
	
	private String genMessage() {
		Random rand = new Random();
		
		int randint1 = rand.nextInt((100 - 1) + 1) + 1;
		int randint2 = rand.nextInt((100 - 1) + 1) + 1;
	
		return "Player " + randint1 + " shot down Player " + randint2;
	}
}
