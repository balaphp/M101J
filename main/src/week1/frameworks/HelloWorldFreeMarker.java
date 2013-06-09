package week1.frameworks;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 */

/**
 * @author Thiago Freitas
 *
 */
public class HelloWorldFreeMarker {
 public static void main(String[] args) throws IOException, TemplateException {
	Configuration configuration = new Configuration();
	configuration.setClassForTemplateLoading(HelloWorldFreeMarker.class, "/");
	
	Template hellotemplate = configuration.getTemplate("hello.flt");
	StringWriter writer = new StringWriter();
	
	Map<String, Object> myMap = new HashMap<String, Object>();
	myMap.put("name", "FreeMarker");
	
	hellotemplate.process(myMap, writer);
	
	System.out.println(writer);
	
}
}
