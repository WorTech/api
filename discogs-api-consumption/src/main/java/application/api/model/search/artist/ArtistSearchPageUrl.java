package application.api.model.search.artist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistSearchPageUrl {
	
	private String next;
	private String last;
	
	public String getNext() {
		return next;
	}
	public String getLast() {
		return last;
	}
}
