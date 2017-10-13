package application.api.model.artist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a person that is member of an {@link Artist}.
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Member {

	private long id;
	private boolean active;
	private String name;
	
	@JsonProperty("resource_url")
	private String resourceUrl;

	/**
	 * Returns the id of this member
	 * @return The id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns whether or not the member is being considered an active member
	 * @return True if the member is active, false otherwise
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Returns the name of the member
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns a URL to the the entity
	 * @return The URL
	 */
	public String getResourceUrl() {
		return resourceUrl;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", active=" + active + ", name=" + name + ", resourceUrl=" + resourceUrl + "]";
	}
	
	
}