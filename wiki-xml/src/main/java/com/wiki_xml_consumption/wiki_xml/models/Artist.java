package com.wiki_xml_consumption.wiki_xml.models;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Artist {

	@Indexed
	private String name;
	private String birthName;
	public String getBirthName() {
		return birthName;
	}

	public void setBirthName(String birthName) {
		this.birthName = birthName;
	}

	private int DOB;
	private List<String> instruments;
	private String placeOfBirth;
	private List<String> genre;
	
	private String label;
	
	public String getName() {
		return name;
	}
	
	public int getDOB () {
		return DOB;
	}
	
	public List<String> getInstruments(){
		return instruments;
	}
	
	public String getPlaceOfBirth() {
		return placeOfBirth;
}
	
	public String getLabel() {
		return label;
	}
	
	

	public List<String> getGenre(){
		return genre;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}

	public void setDOB(int dOB) {
		DOB = dOB;
	}

	public void setInstruments(List<String> instruments) {
		this.instruments = instruments;
	}
	
	public void addInstruments(String instrument) {
		this.instruments.add(instrument);
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public void setGenre(List<String> genre) {
		this.genre = genre;
	}
	
	public void addGenre(String genre) {
		this.genre.add(genre);
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
		return "Artist [name=" + name + ", birth name="+ birthName+ ", DOB=" + DOB + ", instruments=" + instruments + ", placeOfBirth="
				+ placeOfBirth + ", genre=" + genre + ", label=" + label + "]";
	}

}