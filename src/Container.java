import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;



public class Container implements ContainerBase {
	
	private ServerSocket MyServer;
	private int PortNumber;
	private boolean shutdown = false;
	
	
	public Container (int port){
		this.PortNumber = port;
	}
	@Override
	public void initialize(int port) {
		// other initializations for further settings
		this.PortNumber = port;
		
		try{
			start();
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
	
	
	
	@Override
	public void start() {
		try{
			MyServer = new ServerSocket(PortNumber, 1, InetAddress.getByName("127.0.0.1"));
			//Socket client = new Socket("127.0.0.1", PortNumber);
		}catch (Exception e){
			e.printStackTrace();
		}
		
		
		while (!shutdown){
			Socket client = null;
			InputStream input = null;
			OutputStream output = null;
			
			try {
				client = MyServer.accept();
				input = client.getInputStream();
				
				output = client.getOutputStream();
				
				// clasa Request care primeste "input" ca stream de citire
				
				Request request = new Request(input);
				request.parse();
				
				// Should generate Response here
				// Create Response class and populate in request for processing
				
				Response response = new Response(output);
				response.setRequest(request);
				response.sendStaticResponse();
				
				// write something to stream: it should be a http header+TEXT
				//output.write((byte)'h');
				
			}catch(IOException e){
				System.out.println(e);
				System.exit(1);
			}/*finally{
				try {
					input.close();
					output.close();
					client.close();
				}catch (Exception e){
					e.printStackTrace();
				}
					
			}*/
		}
		
		
	}
	
	public static void main(String[] Args){
		Container MyServletContainer = new Container(8025);
		MyServletContainer.start();
	}
}
