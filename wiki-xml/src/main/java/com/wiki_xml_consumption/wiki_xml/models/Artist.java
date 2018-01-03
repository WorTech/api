package com.wiki_xml_consumption.wiki_xml.models;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public final class Artist {
	@Id
	private BigInteger id;
	
	@Indexed
	private String name;
	private String birthName;
	private List<String> genre;
	
	private int dateOfBirth;
	private List<String> instruments;
	private String placeOfBirth;
//	private List<String> associatedActs;
	

	
	public Artist() {

	}

	public Artist(BigInteger id, String name, String birthName, List<String> genre, int dateOfBirth,
		List<String> instruments, String placeOfBirth) {
		super();
		this.id = id;
		this.name = name;
		this.birthName = birthName;
		this.genre = genre;
		this.dateOfBirth = dateOfBirth;
		this.instruments = instruments;
		this.placeOfBirth = placeOfBirth;
	}

	public String getBirthName() {
		return birthName;
	}
	
	public void setBirthName(String birthName) {
		this.birthName = birthName;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getDateOfBirth () {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(int dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public List<String> getInstruments(){
		return instruments;
	}
	
	public void setInstruments(List<String> instruments) {
		this.instruments = instruments;
	}
	
	public void addInstruments(String instrument) {
		this.instruments.add(instrument);
	}
	
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}		
	
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public List<String> getGenre(){
		return genre;
	}
	
	public void setGenre(List<String> genre) {
		this.genre = genre;
	}
	
	public void addGenre(String genre) {
		this.genre.add(genre);
	}
	
	
	@Override
	public String toString() {
		return "Artist [name=" + name + ", birth name="+ birthName+ ", DOB=" + dateOfBirth + ", instruments=" + instruments + ", placeOfBirth="
				+ placeOfBirth + ", genre=" + genre + "]";
	}

}