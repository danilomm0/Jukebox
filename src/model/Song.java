package model;

import java.io.Serializable;

public class Song implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String artist;
	private String time;

	public Song(String name, String artist, String songLength) {
		this.name = name;
		this.artist = artist;
		this.time = songLength;
	}

	public String getName() {
		return name;
	}

	public String getArtist() {
		return artist;
	}

	public String getTime() {
		return time;
	}
}