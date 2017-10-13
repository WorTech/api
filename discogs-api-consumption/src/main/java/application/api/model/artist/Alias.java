package application.api.model.artist;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an alias name for an {@link Artist}.
 * 
 */
public class Alias {

	private long id;

	@JsonProperty("resource_url")
	private String resourceUrl;
	private String name;

	/**
	 * Returns the id for that alias
	 * @return The id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Returns the resource URL for this alias
	 * @return The resource URL 
	 */
	public String getResourceUrl() {
		return resourceUrl;
	}
	
	/**
	 * Returns the name of this alias
	 * @return The actual alias name
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Alias [id=" + id + ", name=" + name + "]";
	}
}