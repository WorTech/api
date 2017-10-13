package application.api.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import application.api.constants.Limit;

/*
 * Helper class which obtains information about the header of an request. 
 * 
 * NOTES:
 * 		1) Instantiation this class with a resourceUrl uses one of the rate limits
 *		2) Calling refresh uses one of the rate limits 
 */

public class HeaderInfo {

	private RestTemplate restTemplate = new RestTemplate();
	
	private HttpHeaders httpHeaders;
	private String resourceurl;
	private int rateLimit;
	private int rateLimitUsed;
	private int rateLimitRemaining;
	
	public HeaderInfo(String resourceUrl) {
		refresh(resourceUrl);
	}

	public int getRateLimit() {
		return this.rateLimit;
	}

	public int getRateLimitUsed() {
		return this.rateLimitUsed;
	}

	public int getRateLimitRemaining() {
		return this.rateLimitRemaining;
	}

	public String getResourceurl() {
		return this.resourceurl;
	}
	
	public void refresh(String resourceUrl) {
		httpHeaders = restTemplate.headForHeaders(resourceUrl);
		this.resourceurl = resourceUrl;
		this.rateLimit = Integer.parseInt(httpHeaders.getFirst(Limit.RATELIMIT)) - 1; // minus one because the last
																						// ratelimit is an error page
		this.rateLimitUsed = Integer.parseInt(httpHeaders.getFirst(Limit.RATELIMIT_USED));
		this.rateLimitRemaining = Integer.parseInt(httpHeaders.getFirst(Limit.RATELIMIT_REMAINING));
	}

}