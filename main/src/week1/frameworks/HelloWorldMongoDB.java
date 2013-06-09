package week1.frameworks;
import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * 
 */

/**
 * @author Thiago Freitas
 *
 */
public class HelloWorldMongoDB {
	
	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient();
		
		DB database = client.getDB("course");
		DBCollection collection = database.getCollection("hello");
		
		DBObject findOne = collection.findOne();
		
		System.out.println(findOne);
	}

}
