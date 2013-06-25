import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Response implements ResponseBase {
	
	private OutputStream output;
	private Request request;
	private static final int BUFF_LENGTH = 1024;
	
	public Response(OutputStream output) {
		this.output = output;
	}

	@Override
	public void setRequest(Request request) {
		this.request = request;
	}

	@Override
	public void sendStaticResponse() throws IOException {
		String uri = request.getUri();
		// Retrieve file from AppFolder
		byte[] buffer = new byte[1024];
		FileInputStream fis = null;
		
		try {
			 File file = new File(Container.AppPath+ uri);
			 System.out.println(file.getPath());
			 if (file.exists()){
				 fis = new FileInputStream(file);
				 int index = fis.read(buffer, 0, BUFF_LENGTH);
				 // Write to client the page requested
				 while (index != -1){
					 output.write(buffer, 0, index);
					 index = fis.read(buffer, 0, BUFF_LENGTH);
				 }
			 }
			 else { // file not found 
				 String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + 
						  "Content-Type: text/html\r\n" + "Content-Length: 23\r\n" +
						  "\r\n" +
						  "<h1>File Not Found</h1>"; 
				 output.write(errorMessage.getBytes()); 
			 }
			
		}catch (IOException e){
			System.out.println(e);
		}finally { 
			if (fis!=null) 
				fis.close(); 
		}
		
		//System.out.println(uri);
	}

}
