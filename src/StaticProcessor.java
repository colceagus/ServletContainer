import java.io.IOException;


public class StaticProcessor {
	public void process(Request request, Response response){
		try {
			response.sendStaticResponse();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
