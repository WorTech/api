package com.musiccrux.discogs_xml_consumption.models;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a person that is member of an {@link Artist}.
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Members {

	private long[] id;
	private String[] name;

	public long[] getId() {
		return id;
	}

	public String[] getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Members [id=" + Arrays.toString(id) + ", name=" + Arrays.toString(name) + "]";
	}

}