
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.*;

public class Request implements RequestBase{
	private InputStream input;
	private String uri;
	private String httpMethod; // GET, POST
	
	public Request(InputStream input){
		this.input = input;
	}
	
	@Override
	public void parse() {
		
		
		StringBuffer request = new StringBuffer(2048);
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
		
		System.out.println(request.toString());
		// Parse the request and get the URL
		parseUri(request.toString());
	}
	
	@Override
	public String parseUri(String requestString) {
		
		int index1, index2;
		index1 = requestString.indexOf(' ');
		
		if (index1 != -1){
			httpMethod = requestString.substring(0, index1 + 1);
			
			index2 = requestString.indexOf(' ', index1 + 1);
			if (index2 > index1)
				return requestString.substring(index1 + 1, index2);
		}
		
		return null;
	}

	@Override
	public String getUri() {
		return uri;
	} 
	
	
}
