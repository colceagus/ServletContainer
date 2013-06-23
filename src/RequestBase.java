public interface RequestBase {
	 public void parse();
	 abstract String parseUri(String requestString);
	 public String getUri();	
}
