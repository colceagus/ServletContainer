import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class clientThread extends Thread{
	

	private InputStream input;
	private OutputStream output;
	
	private final clientThread threads[]; // so that it does not get modified
	private Socket client;
	private int howManyClients;

	
	public clientThread(Socket client, clientThread[] threads){
		this.client = client;
		this.threads = threads;
		this.howManyClients = threads.length;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			input = client.getInputStream();
			output = client.getOutputStream();
			
			// clasa Request care primeste "input" ca stream de citire
			
			Request request = new Request(input);
			request.parse();
			
			// Should generate Response here
			// Create Response class and populate in request for processing
			
			Response response = new Response(output);
			response.setRequest(request);
			
			// TODO Determine if this is a static file (http://myserver.com:port/filename) or a servlet path (http://myserver.com:port/servlet/servletName)
			
			/* processRequest(request.getUri) -> Static | Servlet -> send appropriate response
			 * if (type == RequestType.Static)
			 * 		response.sendStaticResponse();
			 * else {
			 * 		  instantiate servlet class => a kind of Factory based on Servlet name 
			 * 		  something like: Class servlet = ServletFactory.getInstance('testServlet');
			 * 		  servlet.init();
			 *		  servlet.service(); -> doGet | doPost
			 *		  servlet.destroy();
			 * }
			 */
			response.sendStaticResponse();
			
			// write something to stream: it should be a http header+TEXT
			//output.write((byte)'h');
			client.close();
		}catch (IOException e){
			System.out.println(e);
		}
	}
	

}
