import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class clientThread extends Thread{
	

	private InputStream input = null;
	private OutputStream output = null;
	
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
			try {
				if (request.getUri() != null){
					if (request.getUri().startsWith("/servlet/")){
						System.out.println("I am a servlet!");
						
					}else{
						
						response.sendStaticResponse();
					}
					this.setName(request.getUri());
				}
			}catch (NullPointerException e){
				System.out.println("Tried parsing a null string");
			}
			// write something to stream: it should be a http header+TEXT
			//output.write((byte)'h');
			
		}catch (IOException e){
			System.out.println(e);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (input != null)
					input.close();
				
				if (output != null){
					output.flush();
					output.close();
				}
				client.close();
			}catch(IOException e){
				System.out.println(e);
			}
		}
	}
	

}
