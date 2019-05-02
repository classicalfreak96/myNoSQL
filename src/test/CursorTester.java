package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import hw5.DB;
import hw5.DBCollection;
import hw5.DBCursor;
import hw5.Document;

class CursorTester {

	/*
	 *Queries:
	 * 	Find all (done?)
	 * 	Find with relational select
	 * 	Find with projection
	 * 	Conditional operators
	 * 	Embedded Documents and arrays
	 */

	@Test
	public void testFindAll() throws Exception {
		DB db = new DB("data");
		DBCollection test = db.getCollection("test");
		DBCursor results = test.find();
		assertTrue(results.count() == 3);
		assertTrue(results.hasNext());
		JsonObject d1 = results.next(); //verify contents?
		assertTrue(results.hasNext());
		JsonObject d2 = results.next();//verify contents?
		assertTrue(results.hasNext());
		JsonObject d3 = results.next();//verify contents?
		assertTrue(!results.hasNext());
		
		for (int i = 0; i < test.count(); i++) {
			JsonObject document = test.getDocument(i);
			System.out.println(Document.toJsonString(document));
		}
	}
	
//	@Test
//	public void testQuery() throws Exception {
//		DB db = new DB("data");
//		DBCollection test = db.getCollection("test");
//		JsonObject query = Document.parse("{key: value}");
//		DBCursor results = test.find(query);
//		assertTrue(results.count() == 1);
//		assertFalse(results.hasNext());
//	}
}
