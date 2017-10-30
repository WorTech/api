package com.musiccrux.discogs_xml_consumption.models;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a person that is member of an {@link Artist}.
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Members {
	
	@JsonProperty("id")
	private long[] discogs_ids;
	@JsonProperty("name")
	private String[] names;

	public long[] getDiscogs_Ids() {
		return discogs_ids;
	}

	public String[] getNames() {
		return names;
	}

	@Override
	public String toString() {
		return "Members [discogs_ids=" + Arrays.toString(discogs_ids) + ", names=" + Arrays.toString(names) + "]";
	}

}