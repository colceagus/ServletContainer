import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;





public class Container implements ContainerBase {
	
	public static String AppPath = System.getProperty("user.dir") + File.separator + "Apps"; // Get user working directory + Resources folder
	private static final int maxConnections = 10;
	private clientThread threads[] = new clientThread[maxConnections];
	
	private ServerSocket MyServer;
	private Socket clientSocket;
	private int PortNumber = 8025;
	private boolean shutdown = false;
	
	
	public Container (int port){
		this.PortNumber = port;
		System.out.println(AppPath);
	}
	@Override
	public void initialize(int port) {
		// other initializations for further settings
		this.PortNumber = port;
		
		try{
			start();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
	@Override
	public void start() {
		try{
			MyServer = new ServerSocket(PortNumber, 1, InetAddress.getByName("127.0.0.1"));
			//Socket client = new Socket("127.0.0.1", PortNumber);
		}catch (Exception e){
			System.out.println(e);
		}
		
		
		while (!shutdown){
			clientSocket = null;
			InputStream input = null;
			OutputStream output = null;
			int i = 0;
			try {
				// listen for a connection
				clientSocket = MyServer.accept(); 			
				// Start in a separate thread the current request
				
				for (i = 0; i < maxConnections; i++ )
					if (threads[i] == null){
						(threads[i] = new clientThread(clientSocket, threads)).start();
						break;
					}
				if (i == maxConnections) { 
					// show 503 Service Unavailable; provide Retry-After
					OutputStream os = clientSocket.getOutputStream();
					String errorMessage = "HTTP/1.1 503 Service Unavailable\r\n" + 
							  "Accept-Ranges: bytes\r\n"+
							  "Content-Type: text/html\r\n" + 
							  "Content-Length: 190\r\n" +
							  "\r\n" +
							  "<!DOCTYPE HTML PUBLIC '-//IETF//DTD HTML 2.0//EN'>\r\n<html><head>\r\n"+
							  "<title>503 Service Unavailable</title>\r\n"+
							  "</head><body>\r\n"+
							  "<h1>Service Unavailable</h1>\r\n"+
							  "<p>Server is busy.</p>\r\n"+
							  "</body></html>"; 
					os.write(errorMessage.getBytes());
					os.flush();
			        os.close();
			        //clientSocket.shutdownOutput();
			        //clientSocket.shutdownInput();
			        clientSocket.close();
				}
				/*	
				input = clientSocket.getInputStream();
				
				output = clientSocket.getOutputStream();
				
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
				 *
				response.sendStaticResponse();
				
				// write something to stream: it should be a http header+TEXT
				//output.write((byte)'h');
				clientSocket.close(); */
			}catch(IOException e){
				System.out.println(e);
				System.exit(1);
			}finally{
				try {
					//threads[i-1] = null;
				}catch(IndexOutOfBoundsException e){
					System.out.println(e);
				}
			}
		}
		
		
	}
	@Override
	public ResponseType parseRequest() {
		/*StringBuffer request = new StringBuffer(2048);
		byte[] buffer = new byte[2048];
		
		// the buffer index: until where it was written when the read method was appealed
		int index;
		
		try {
			index = input.read(buffer);
		}catch(IOException e){
			System.out.println(e);
			index = -1;
		}
		
		for (int j = 0; j < index; j++){
			request.append((char) buffer[j]);
		}
		int typeIndex = request.indexOf(' ',0);
		if (typeIndex != -1){
			System.out.println(request.substring(0, typeIndex));
			return ResponseType.Servlet;
		}
		System.out.println(request.toString());*/
		return null;
	}
	@Override
	public Response processResponse() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] Args){
		Container MyServletContainer = new Container(8025);
		MyServletContainer.start();
	}
}
