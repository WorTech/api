package application.api.model.search.artist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Representation of the page returned from the endPoint: "/database/search?type=artist"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistSearchPage {
	
	@JsonProperty("pagination")
	private ArtistSearchPagePagination pagination;
	
	@JsonProperty("results")
	private ArtistSearchPageArtist[] artists;

	public ArtistSearchPageArtist[] getArtists() {
		return artists;
	}
	
	public ArtistSearchPagePagination getPagination() {
		return pagination;
	}
	
	public boolean hasNextPage() {
		return pagination.getCurrentPage() < pagination.getTotalPages();
	}
	
	public String getNextPage() {
		return pagination.getUrl().getNext();
	}
}
