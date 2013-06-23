import java.io.IOException;
import java.io.OutputStream;


public class Response implements ResponseBase {
	
	private OutputStream output;
	private Request request;
	
	public Response(OutputStream output) {
		this.output = output;
	}

	@Override
	public void setRequest(Request request) {
		this.request = request;
	}

	@Override
	public void sendStaticResponse() throws IOException {
		request.parse();
		String uri = request.getUri();
		System.out.println(uri);
	}

}
