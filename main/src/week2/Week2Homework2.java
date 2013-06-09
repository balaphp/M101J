/**
 * 
 */
package week2;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * @author Thiago Freitas
 * 
 */
public class Week2Homework2 {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient();

		DB database = client.getDB("students");
		DBCollection collection = database.getCollection("grades");

		DBCursor sort = collection.find().sort(
				new BasicDBObject("student_id", 1).append("score", 1));

		Integer student_id = -1;
		while (sort.hasNext()) {
			DBObject student = sort.next();
			Integer thisStudentID = Integer.parseInt(String.valueOf(student
					.get("student_id")));
			String type = String.valueOf(student.get("type"));
			
			System.out.println(student);

			if (!thisStudentID.equals(student_id) && type.equals("homework")) {
				collection.remove(student);
				student_id = thisStudentID;
			}
			
			

		}
		
		System.out.println("FIM!");

	}

}
