/**
 * 
 */
package week3;

import java.awt.ItemSelectable;
import java.net.UnknownHostException;

import com.mongodb.BasicDBList;
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
public class Week3Homework1 {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient();

		DB database = client.getDB("school");
		DBCollection collection = database.getCollection("students");

		DBCursor sort = collection.find().sort(new BasicDBObject("_id", 1));

		// Percorre todos os documentos
		while (sort.hasNext()) {
			DBObject student = sort.next();
			BasicDBList scoreList = (BasicDBList) student.get("scores");
			Double lowest = Double.MAX_VALUE;
			DBObject toExclude = null;
			System.out.println(scoreList);
			for (Object object : scoreList) {
				DBObject itemList = (DBObject) object;
				String type = (String) itemList.get("type");
				if (type.equals("homework")) {
					Double d = (Double) itemList.get("score");
					if (d < lowest) {
						lowest = d;
						toExclude = itemList;
					}
				}
			}

			scoreList.remove(toExclude);
			System.out.println("\r\n");

			System.out.println(scoreList);

			collection.update(new BasicDBObject("_id", student.get("_id")),
					new BasicDBObject("$set", new BasicDBObject("scores",
							scoreList)), false, false);

		}

		System.out.println("FIM!");

	}

}
