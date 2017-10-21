package com.musiccrux.discogs_xml_consumption.models;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an actual artist. In most cases this are musicians, however
 * sound-engineers, producers, and bands etc. may also appear as artists
 * 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {

	@Id
	private long id;
	private String name;
	@JsonProperty("releases_url")
	private String releasesUrl;
	@JsonProperty("resource_url")
	private String resourceUrl;
	private Members members;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getReleasesUrl() {
		return releasesUrl;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public Members getMembers() {
		return members;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Artist [id=" + id + ", name=" + name + ", members=" + members + "]";
	}
}