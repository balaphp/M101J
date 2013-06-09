/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package week3.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * 
 * @author Thiago Freitas
 * 
 */
public class BlogPostDAO {
	DBCollection postsCollection;

	/**
	 * 
	 * @param blogDatabase
	 */
	public BlogPostDAO(final DB blogDatabase) {
		postsCollection = blogDatabase.getCollection("posts");
	}

	/**
	 * Return a single post corresponding to a permalink
	 * 
	 * @param permalink
	 * @return post
	 */
	public DBObject findByPermalink(String permalink) {

		DBObject post = null;

		post = postsCollection
				.findOne(new BasicDBObject("permalink", permalink));

		return post;
	}

	/**
	 * Return a list of posts in descending order. Limit determines how many
	 * posts are returned.
	 * 
	 * @param limit
	 * @return Lista de posts
	 */
	public List<DBObject> findByDateDescending(int limit) {

		List<DBObject> posts = new ArrayList<DBObject>();

		DBCursor cursor = postsCollection.find()
				.sort(new BasicDBObject("date", -1)).limit(limit);

		while (cursor.hasNext()) {
			posts.add(cursor.next());
		}

		return posts;
	}

	/**
	 * @param title
	 * @param body
	 * @param tags
	 * @param username
	 * @return permalink
	 */
	public String addPost(String title, String body, List<String> tags,
			String username) {

		System.out.println("inserting blog entry " + title + " " + body);

		String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
		permalink = permalink.replaceAll("\\W", ""); // get rid of non
														// alphanumeric
		permalink = permalink.toLowerCase();

		BasicDBObject post = new BasicDBObject();
		post.append("title", title).append("author", username)
				.append("body", body).append("permalink", permalink)
				.append("tags", tags)
				.append("comments", new ArrayList<String>())
				.append("date", new Date());

		postsCollection.insert(post);

		return permalink;
	}

	// White space to protect the innocent

	// Append a comment to a blog post
	public void addPostComment(final String name, final String email,
			final String body, final String permalink) {

		BasicDBObject postComment = new BasicDBObject("author", name).append(
				"body", body);

		if (email != null && !email.trim().isEmpty()) {
			postComment.append("email", email);
		}

		postsCollection.update(new BasicDBObject("permalink", permalink),
				new BasicDBObject("$addToSet", new BasicDBObject("comments",postComment)));

		// Hints:
		// - email is optional and may come in NULL. Check for that.
		// - best solution uses an update command to the database and a suitable
		// operator to append the comment on to any existing list of comments

	}
}
