package application.api.model.search.artist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Search Result for endPoint "database/search?type=artist"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistSearchPageArtist {

	private String title;
	@JsonProperty("resource_url")
	private String resourceUrl;
	private long id;

	public String getTitle() {
		return title;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ArtistSearchPageArtist [title=" + title + ", resourceUrl=" + resourceUrl + ", id=" + id + "]";
	}
	
	
}