package si.merljak.magistrska.client.handler;

/**
 * Handler for page change request.
 * 
 * @author Jakob Merljak
 * 
 */
public interface PagingHandler {
	/**
	 * Handles page change request.
	 * 
	 * @param page new page
	 */
	void onPageChange(int page);
}
