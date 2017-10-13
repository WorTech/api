package application.api.model.artist;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an actual artist. In most cases this are musicians, however
 * sound-engineers, producers, and bands etc. may also appear as artists
 * 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {

	private long id;
	private String name;
	@JsonProperty("releases_url")
	private String releasesUrl;
	@JsonProperty("resource_url")
	private String resourceUrl;
	private List<Member> members;

	/**
	 * Returns the id for this artist
	 * 
	 * @return The id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns the artists official name
	 * 
	 * @return The name of the artist
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns an URL from where all {@link Release}es of a artist can be retrieved
	 * 
	 * @return The URL
	 */
	public String getReleasesUrl() {
		return releasesUrl;
	}

	/**
	 * Returns the URL to this entity
	 * 
	 * @return The URL
	 */
	public String getResourceUrl() {
		return resourceUrl;
	}

	/**
	 * If the artist is a band, returns a list of {@link Member}s.
	 * 
	 * @return The list of band {@link Member}s or null if the artist is not
	 *         considered a band
	 */
	public List<Member> getMembers() {
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