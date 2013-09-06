import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;



public class ServletProcessor {
	public void process(Request request, Response response){
		// get configuration parameters: path, name
		String uri = request.getUri();
		
		String name = uri.substring(uri.lastIndexOf("/servlet/") + 9);
		String servletName = uri.substring(uri.lastIndexOf("/") + 1);
		String folderName = uri.substring(uri.lastIndexOf('/') + 1);
		folderName = name.replace(folderName, "").replace("/", "");
		
		name = name.replace('/', '\\');
		
		//String name = uri.substring(uri.lastIndexOf('/') + 1);
		URLClassLoader loader = null;
		String filePath = null;
		try{
			// load the uri
			filePath = Container.AppPath + File.separator + name;
				
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			
			File classPath = new File(Container.AppPath + File.separator + folderName + File.separator);//
			String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
			
			urls[0] = new URL(null, repository, streamHandler);
			loader = new URLClassLoader(urls);
			
		}catch (IOException e){
			System.out.println(e.toString());
		}
		
		// load the class
		Class myClass = null;
		try {
			myClass = loader.loadClass(servletName);
		}catch (ClassNotFoundException e){
			System.out.println(e.toString());
		}catch (Exception e){
			System.out.println(e.toString());
		}catch (NoClassDefFoundError e){
			System.out.println(e.toString());
		}
		
		// creates and loads the servlet in memory and uses it (service, destroy) 
		try {
			Servlet servlet = (Servlet) myClass.newInstance();
			//System.out.println(request.getUri());
			servlet.service( request, response);
			servlet.destroy();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
