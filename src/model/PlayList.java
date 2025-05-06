// Danilo Malo-Molina and Kade Dean

package model;

import java.io.*;
import java.net.URI;
import java.util.*;
import javafx.collections.*;
import javafx.scene.media.*;

public class PlayList {

	public boolean songIsPlaying;
	private ArrayList<String> musicQueue;
	private MediaPlayer mediaPlayer;
	private ObservableList<String> playlist;

	/*
	 * Constructor for the PlayList object.
	 */
	public PlayList() {
		musicQueue = new ArrayList<String>();
		playlist = FXCollections.observableArrayList();
		songIsPlaying = false;
	}

	/*
	 * Adds a song to the music queue.
	 */
	public void queueUpNextSong(Song songToAdd) {
		playlist.add(songToAdd.getName() + "-" + songToAdd.getArtist());
		if (!songIsPlaying)
			play();
	}

	/*
	 * This function plays a song if the queue is not empty.
	 */
	public void play() {
		if (!playlist.isEmpty()) {
			String temp = playlist.get(0).split("-")[0].replace(" ", "");
			File song = new File("songfiles/" + temp + ".mp3");
			URI uri = song.toURI();
			mediaPlayer = new MediaPlayer(new Media(uri.toString()));
			mediaPlayer.play();
			songIsPlaying = true;
			mediaPlayer.setOnEndOfMedia(new Waiter());
		}
	}

	/*
	 * This function waits the required 2 seconds before playing the next song in
	 * the queue.
	 */
	private class Waiter implements Runnable {
		@Override
		public void run() {
			songIsPlaying = false;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			playlist.remove(0);
			play();
		}
	}

	public void savePlayList() {
		try {
			FileOutputStream bytesToDisk = new FileOutputStream("playlist.ser");
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			musicQueue.clear();
			musicQueue.addAll(playlist);
			outFile.writeObject(musicQueue);
			musicQueue.clear();
			outFile.close();
		} catch (Exception e) {
			System.out.println("Write failed");
		}
	}

	public void loadPlayList() {
		try {
			FileInputStream bytesFromDisk = new FileInputStream("playlist.ser");
			ObjectInputStream inFile = new ObjectInputStream(bytesFromDisk);
			musicQueue = (ArrayList<String>) inFile.readObject();
			playlist.clear();
			playlist.addAll(musicQueue);
			inFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ObservableList<String> getPlayList() {
		return playlist;
	}
}