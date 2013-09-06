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
			config();
			start();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void config(){
		/* Load the server.xml file
		 * Start-up config for the server
		 */
		
		/* Load the web.xml file
		 * Load the servlets' config and start them up
		 * 
		 */
		//LOADER.load('web.xml');
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
			//InputStream input = null;
			//OutputStream output = null;
			int i = 0;
			try {
				// listen for a connection
				
				clientSocket = MyServer.accept(); 			
				// Start in a separate thread the current request
				synchronized(this){
					for (i = 0; i < maxConnections; i++ )
						if (threads[i] == null){
							(threads[i] = new clientThread(clientSocket, threads)).start();
							break;
						}
				}
				//synchronized(this){
				if (i == maxConnections) { 
					// show 503 Service Unavailable; provide Retry-After
					synchronized (this){
						OutputStream os = clientSocket.getOutputStream();
						String errorMessage = "HTTP/1.1 503 Service Unavailable\r\n" + 
								  "Accept-Ranges: bytes\r\n"+
								  "Content-Type: text/html\r\n" + 
								  //"Content-Length: 155\r\n" +
								  "\r\n" +
								  "<!DOCTYPE HTML>\r\n<html><head>\r\n"+
								  "<title>503 Service Unavailable</title>\r\n"+
								  "</head><body>\r\n"+
								  "<h1>Service Unavailable</h1>\r\n"+
								  "<p>Server is busy.</p>\r\n"+
								  "</body></html>"; 
					
						os.write(errorMessage.getBytes());
						
						os.flush();
					}
			        //os.close();
			        //clientSocket.shutdownOutput();
			        //clientSocket.shutdownInput();
			        //clientSocket.close();
				}
				System.gc();
				//}
				// ***
			}catch(IOException e){
				System.out.println(e);
				//System.exit(1);
			}finally{
				try {
					//threads[i-1] = null;
					
					//if (clientSocket.isClosed() == false && i == maxConnections)	
						//clientSocket.close(); 
				}catch(IndexOutOfBoundsException e){
					System.out.println(e);
				}/*catch(IOException e){
					System.out.println(e);
				}*/
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
		Container MyServletContainer = new Container(8090);
		MyServletContainer.start();
	}
}
