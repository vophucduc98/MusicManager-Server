package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// Song Data Transfer Object

@Entity
@Table(name = "songmanager")
public class SongDTO{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "artist")
	private String artist;
	@Column(name = "duration")
	private int duration;
	
	
	public SongDTO() {
		super();
	}
	
	public SongDTO(int id, String name, String artist, int duration) {
		super();
		this.id = id;
		this.name = name;
		this.artist = artist;
		this.duration = duration;
	}

	public SongDTO(String name, String artist, int duration) {
		super();
		this.name = name;
		this.artist = artist;
		this.duration = duration;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
