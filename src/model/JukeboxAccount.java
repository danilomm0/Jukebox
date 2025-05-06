// Kade Dean
package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a jukebox account.
 */
public class JukeboxAccount implements Serializable {

	/**
	 * The user's password.
	 */
	private String password;

	/**
	 * The user's ID.
	 */
	private String userId;

	/**
	 * The number of songs the user has played today.
	 */
	private int songsPlayed;

	/**
	 * The date the user last logged in.
	 */
	private LocalDate lastLoginDate;

	/**
	 * Creates a new jukebox account.
	 *
	 * @param userId   the user's ID
	 * @param password the user's password
	 * @return if the account can be successfully created
	 */
	public JukeboxAccount(String userId, String password) {
		lastLoginDate = LocalDate.now();
		this.userId = userId;
		this.password = password;
	}

	/**
	 * Adds a song play to the user's account.
	 */
	public void addSongPlay() {
		// Handles the edge case of if a new day begins while a user is already logged
		// in
		if (isUserLoggedInOnNewDay()) {
			lastLoginDate = LocalDate.now();
			songsPlayed = 0;
		}
		songsPlayed++;
	}

	/**
	 * Checks if the user can play a song.
	 *
	 * @return true if the user can play a song, false otherwise
	 */
	public boolean canPlaySong() {
		if (songsPlayed < 3 || isUserLoggedInOnNewDay()) {
			return true;
		}
		return false;
	}

	/**
	 * Attempts to log the user in.
	 *
	 * @param inputPassword the password the user entered
	 * @return true if the user was successfully logged in, false otherwise
	 */
	public boolean attemptLogin(String inputPassword) {
		if (inputPassword.equals(password)) {
			if (isUserLoggedInOnNewDay()) {
				songsPlayed = 0;
			}
			lastLoginDate = LocalDate.now();
			return true;
		}
		return false;
	}

	/**
	 * Gets the user's ID.
	 *
	 * @return the user's ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Gets the number of songs the user has played today.
	 *
	 * @return the number of songs the user has played today
	 */
	public int getSongsPlayed() {
		return songsPlayed;
	}

	/**
	 * Checks if it is a new day for the user.
	 *
	 * @return true if it is a new day for the user, false otherwise
	 */
	private boolean isUserLoggedInOnNewDay() {
		boolean newDay = lastLoginDate.getDayOfYear() < LocalDate.now().getDayOfYear();
		boolean newYear = lastLoginDate.getYear() < LocalDate.now().getYear();
		return newDay || newYear;
	}

}
