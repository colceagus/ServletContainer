import java.io.IOException;


/*
 *  HTTP Response = Status-Line 
 *  *(( general-header | response-header | entity-header ) CRLF) 
 *  CRLF
 *  [ message-body ] Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
 *   
 */

public interface ResponseBase {
	public void setRequest(Request request);
	public void sendStaticResponse() throws IOException;
	
	
}
