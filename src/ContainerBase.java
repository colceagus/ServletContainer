
/**
 * The interface for the Container
 */

/**
 * Title: Servlet Container
 * Description: This servlet container will accept GET and POST requests
 * @author Colceag Mihai Daniel 342 A3
 * @version alpha0.1
 */
public interface ContainerBase {
	
	/*
	 * Initializes our container, set parameters, then start the Container
	 * @return void
	 */
	public void initialize(int PortNumber);
	
	/*
	 * Starts the Container as a Server for listening for requests 
	 * @return void
	 */
	public void start();
	
	/*
	 * Parses the request received and returns a type for further processing
	 * @return ResponseType Servlet or JSP
	 */
	public ResponseType parseRequest();
	
	/*
	 * Processes the current request based on type and returns the appropriate content: static, servlet(dynamic) or jsp
	 * @return Response StaticResponse or ServletResponse or JSPResponse
	 */
	public Response processResponse();
	
	/*
	 * Closes the whole server ( active connections, servlets, ...)  
	 * @return void 
	 */
	public void shutdown();
}
