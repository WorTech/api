package com.musiccrux.discogs_xml_consumption.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an actual artist. In most cases this are musicians, however
 * sound-engineers, producers, and bands etc. may also appear as artists.
 * 
 * 
 */

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Artist {

	@JsonProperty("id")
	private Long discogs_id;
	@Indexed
	private String name;
	private Members members;

	/**
	 * 
	 * @return The id of the Artist given by Discogs
	 */
	public long getDiscogsId() {
		return discogs_id;
	}

	/**
	 * 
	 * @return The name of the Artist
	 */
	public String getName() {
		return name;
	}

	/**
	 * Note that if an "Artist" has a member property then it is actually a Band and
	 * not an actual individual Artist.
	 * 
	 * @return The members of the
	 */
	public Members getMembers() {
		return members;
	}

	@Override
	public String toString() {
		return "Artist [discogs_id=" + discogs_id + ", name=" + name + ", members=" + members + "]";
	}

}