package application.api.operation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import application.api.constants.Limit;
import application.api.model.artist.Artist;
import application.api.model.search.artist.ArtistSearchPageArtist;
import application.api.utils.HeaderInfo;

/**
 * This script uses a QueryArtistSearchPage to obtain a list of artist metadata.
 * The resourceUrl for each of those artist is then used to generate an Artist
 * model
 * 
 * NOTES: 
 * 		1)	The QueryArtistSearchPage Script must be run before this script in
 * 			order to autowire the required items. 
 * 
 * 		2)	In the Discogs API, requests are throttled to 60 per minute for 
 * 			authenticated requests, and 25 per minute for unauthenticated requests. 
 *			This information is provided in the header of each request. 
 *
 *		3)	This script uses an unauthenticated request for each resourceUrl,therefore 
 *			we must wait for one minute once we have reached the request cap.
 */

@Component
@Order(value = 2)
public class GenerateArtistModels implements CommandLineRunner {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private QueryArtistSearchPage artistSearchPageQuery;

	private List<Artist> artistModels = new ArrayList<>();

	@Override
	public void run(String... arg0) throws Exception {
		ArtistSearchPageArtist[] artistSearchPageArtists = artistSearchPageQuery.getArtists();
		
		HeaderInfo headerInfo = new HeaderInfo(artistSearchPageArtists[0].getResourceUrl());

		int requestsUsed = headerInfo.getRateLimitUsed();
		for (ArtistSearchPageArtist artistSearchPageArtist : artistSearchPageArtists) {
			// Detect when the request limit has been reached and wait a minute
			if (requestsUsed == headerInfo.getRateLimit()) {
				try {
					System.out.println("REQUEST LIMIT REACHED");
					Thread.sleep(1000 * Limit.REQUESTS_WAIT_TIME);
					String lastResourceUrl = artistModels.get(artistModels.size() - 1).getResourceUrl();
					headerInfo.refresh(lastResourceUrl); // Obtain the new header information
					requestsUsed = headerInfo.getRateLimitUsed();
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				} catch (IndexOutOfBoundsException e) {
					/* TODO : error log instead of sout */System.out.println("Index out of bouund");
				}
			}

			Artist newArtistModel = createArtistModel(artistSearchPageArtist.getResourceUrl());
			artistModels.add(newArtistModel);
			requestsUsed++;
		}
	}

	public List<Artist> getArtistModels() {
		return this.artistModels;
	}

	/**
	 * Creates an artist from an artist resourceUrl. Sample resourceUrl:
	 * https://api.discogs.com/artists/1400476
	 * 
	 * @return The created artist
	 */
	private Artist createArtistModel(String artistResourceUrl) {
		Artist artist = restTemplate.getForObject(artistResourceUrl, Artist.class);
		return artist;
	}

}
