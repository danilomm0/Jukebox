package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.JukeboxAccount;

public class JukeboxAccountTest {

	@Test
	public void testLogin() {
		JukeboxAccount aJBA = new JukeboxAccount("Name", "password");
		assertFalse(aJBA.attemptLogin("test"));
		assertFalse(aJBA.attemptLogin(""));
		assertFalse(aJBA.attemptLogin("LOL"));
		assertTrue(aJBA.attemptLogin("password"));
	}
	
	@Test
	public void testUserId() {
		JukeboxAccount aJBA = new JukeboxAccount("Name", "password");
		assertEquals(aJBA.getUserId(), "Name");
	}
	
	@Test
	public void testSongPlays() {
		JukeboxAccount aJBA = new JukeboxAccount("Name", "password");
		assertTrue(aJBA.canPlaySong());
		aJBA.addSongPlay();
		assertTrue(aJBA.canPlaySong());
		aJBA.addSongPlay();
		assertTrue(aJBA.canPlaySong());
		aJBA.addSongPlay();
		assertFalse(aJBA.canPlaySong());
	}
	
	@Test
	public void testLoginDay() {
		JukeboxAccount aJBA = new JukeboxAccount("Name", "password");
		assertFalse(aJBA.attemptLogin("test"));
		assertFalse(aJBA.attemptLogin(""));
		assertFalse(aJBA.attemptLogin("LOL"));
	}

}
