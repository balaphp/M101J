package week1.frameworks;
import java.io.IOException;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * 
 */

/**
 * @author Thiago Freitas
 * 
 */
public class HelloWorldAllinOne {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient();

		DB database = client.getDB("course");
		final DBCollection collection = database.getCollection("hello");

		Spark.get(new Route("/") {
			@Override
			public Object handle(Request request, Response response) {
				Configuration configuration = new Configuration();
				configuration.setClassForTemplateLoading(
						HelloWorldFreeMarker.class, "/");

				Template hellotemplate = null;
				try {
					hellotemplate = configuration.getTemplate("hello.flt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				StringWriter writer = new StringWriter();

				DBObject findOne = collection.findOne();

				try {
					hellotemplate.process(findOne, writer);
				} catch (TemplateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return writer;
			}
		});
	}

}
