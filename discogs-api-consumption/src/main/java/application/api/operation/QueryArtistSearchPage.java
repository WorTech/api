package application.api.operation;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import application.api.model.search.artist.ArtistSearchPage;
import application.api.model.search.artist.ArtistSearchPagePagination;
import application.api.model.search.artist.ArtistSearchPageArtist;

/**
 * 	This script queries the Discogs API's search end-point for type artist: "database/search?type=artist"
 *  Once the page has been queried, information about the page may be obtained. 
 * 
 * 	NOTES: 
 *		1)	In the Discogs API, requests are throttled to 60 per minute for authenticated requests, 
 *		and 25 per minute for unauthenticated requests.
 */


@Component
@Order(value=1)
public class QueryArtistSearchPage implements CommandLineRunner{
	
	@Autowired
	private RestTemplate restTemplate;
	
	private ArtistSearchPage artistSearchPage;	
	
	@Override
	public void run(String... args) throws Exception{
		/*
		 * TODO: 
		 * 	1) use the pagination and query all the pages.
		 */
		artistSearchPage = restTemplate
				.getForObject(
				"https://api.discogs.com/database/search?type=artist&token=yobjAdXbdlYjJEnPBxqcbRIbHopYtpJVLxEUqhcd", ArtistSearchPage.class);
	}
	
	public ArtistSearchPagePagination getPagination() {
		return artistSearchPage.getPagination();
	}
	
	public ArtistSearchPageArtist[] getArtists() {
		return artistSearchPage.getArtists();
	}
	
}
