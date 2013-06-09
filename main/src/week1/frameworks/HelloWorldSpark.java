package week1.frameworks;
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
public class HelloWorldSpark {
	
	public static void main(String[] args) {
		Spark.get(new Route("/hello") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello World!";
            }
        });
	}

}
