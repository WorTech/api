package com.wiki_xml_consumption.wiki_xml.models;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public final class MusicCruxArtist {
	@Id
	private BigInteger id;
	@Indexed
	private String label;
	

	public MusicCruxArtist() {

	}

	public MusicCruxArtist(BigInteger id, String label) {
		this.id = id;
		this.label = label;
		
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	

	@Override
	public String toString() {
		return "Artist [id=" + id + ", label=" + label + "]";
	}


}
