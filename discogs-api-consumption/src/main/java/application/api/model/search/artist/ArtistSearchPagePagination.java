package application.api.model.search.artist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Encapsulates data about the current page that is retrieved in any pageaple API request.
 *
 * @author M. Fischboeck
 */

//@JsonIgnoreProperties({"urls"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistSearchPagePagination {

	@JsonProperty("per_page")
	private int itemsPerPage;

	@JsonProperty("items")
	private int numberOfItems;
	
	@JsonProperty("page")
	private int currentPage;
	
	@JsonProperty("pages")
	private int totalPages;
	
	@JsonProperty("urls")
	private ArtistSearchPageUrl url;

	/**
	 * Returns the amount of items on the current page
	 * @return The amount of items
	 */
	public int getItemsPerPage() {
		return itemsPerPage;
	}

	/**
	 * Returns the amount of objects that can be retrieved for the given request
	 * @return The amount of total items
	 */
	public int getNumberOfItems() {
		return numberOfItems;
	}

	/**
	 * Returns the current page
	 * @return The current page
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * Returns the total amount of pages that can be retrieved using the
	 * page size that yielded this PageInfo
	 * @return The maximum amount of pages
	 */
	public int getTotalPages() {
		return totalPages;
	}
	
	/**
	 * Returns the pagination url object
	 * @return The url object of the pagination
	 */
	public ArtistSearchPageUrl getUrl() {
		return url;
	}
	
	
}