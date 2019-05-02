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
		System.out.println("TESTING FIND ALL------------------------");
		DB db = new DB("data");
		DBCollection test = db.getCollection("test");
		DBCursor results = test.find();
		assertTrue(results.count() == 3, "Count was " + results.count());
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
	
	@Test
	public void testQuery() throws Exception {
		System.out.println("TESTING QUERY------------------------");
		DB db = new DB("data");
		DBCollection test = db.getCollection("test");
		JsonObject query = Document.parse("{key: value}");
		DBCursor results = test.find(query);
		assertTrue(results.count() == 1, "count was : " + results.count());
		assertTrue(results.hasNext());
		JsonObject d1 = results.next();
		assertTrue(!results.hasNext());
	}
	
	@Test
	public void testEmbeddedQuery() throws Exception {
		System.out.println("TEST EMBEDDED QUERY--------------------");
		DB db = new DB("data");
		DBCollection test = db.getCollection("test");
		JsonObject query = Document.parse("{embedded.key2 : value2}");
		DBCursor results = test.find(query);
		assertTrue(results.count() == 1, "count was : " + results.count());
		assertTrue(results.hasNext());
		JsonObject d1 = results.next();
		assertTrue(!results.hasNext());
	}
	
	@Test
	public void testArrayQuery() throws Exception {
		System.out.println("TEST ARRAY QUERY--------------------");
		DB db = new DB("data");
		DBCollection test = db.getCollection("test");
		JsonObject query = Document.parse("{array : [one, two, three]}");
		DBCursor results = test.find(query);
		assertTrue(results.count() == 1, "count was : " + results.count());
		assertTrue(results.hasNext());
		JsonObject d1 = results.next();
		assertTrue(!results.hasNext());
	}
	
	@Test
	public void testQueryII() throws Exception {
		System.out.println("TEST QUERY II--------------------");
		DB db = new DB("data");
		DBCollection test = db.getCollection("test2");
		JsonObject query = Document.parse("{value : 1}");
		DBCursor results = test.find(query);
		assertTrue(results.count() == 1, "count was : " + results.count());
		assertTrue(results.hasNext());
		JsonObject d1 = results.next();
		assertTrue(!results.hasNext());
	}
	
	@Test
	public void testArrayQueryFail() throws Exception {
		System.out.println("TEST ARRAY QUERY FAIL--------------------");
		DB db = new DB("data");
		DBCollection test = db.getCollection("test");
		JsonObject query = Document.parse("{array : [one, two]}");
		DBCursor results = test.find(query);
		assertTrue(results.count() == 0, "count was : " + results.count());
		assertFalse(results.hasNext());
	}
	
	@Test
	public void testEqComparator() throws Exception {
		System.out.println("TEST EQUAL COMPARATOR--------------------");
		DB db = new DB("data");
		DBCollection test = db.getCollection("test2");
		JsonObject query = Document.parse("{value : {$eq : 2}}");
		DBCursor results = test.find(query);
		assertTrue(results.count() == 1, "count was : " + results.count() + ", expected 1");
		assertTrue(results.hasNext());
		JsonObject d1 = results.next();
		assertTrue(!results.hasNext());
	}
	
	@Test
	public void testGtComparator() throws Exception {
		System.out.println("TEST GREATER THAN COMPARATOR--------------------");
		DB db = new DB("data");
		DBCollection test = db.getCollection("test2");
		JsonObject query = Document.parse("{value : {$gt : 2}}");
		DBCursor results = test.find(query);
		assertTrue(results.count() == 3, "count was : " + results.count() + ", expected 3");
		assertTrue(results.hasNext());
		JsonObject d1 = results.next();
		assertTrue(results.hasNext());
		JsonObject d2 = results.next();
		assertTrue(results.hasNext());
		JsonObject d3 = results.next();
		assertTrue(!results.hasNext());
	}
	
	@Test
	public void testLteComparator() throws Exception {
		System.out.println("TEST LESS THAN EQUALS COMPARATOR--------------------");
		DB db = new DB("data");
		DBCollection test = db.getCollection("test2");
		JsonObject query = Document.parse("{value : {$lte : 2}}");
		DBCursor results = test.find(query);
		assertTrue(results.count() == 2, "count was : " + results.count() + ", expected 2");
		assertTrue(results.hasNext());
		JsonObject d1 = results.next();
		assertTrue(results.hasNext());
		JsonObject d2 = results.next();
		assertTrue(!results.hasNext());
	}
	
	@Test
	public void testInComparator() throws Exception {
		System.out.println("TEST IN COMPARATOR--------------------");
		DB db = new DB("data");
		DBCollection test = db.getCollection("test2");
		JsonObject query = Document.parse("{value : {$in : [2,3,4]}}");
		DBCursor results = test.find(query);
		assertTrue(results.count() == 3, "count was : " + results.count() + ", expected 3");
		assertTrue(results.hasNext());
		JsonObject d1 = results.next();
		assertTrue(results.hasNext());
		JsonObject d2 = results.next();
		assertTrue(results.hasNext());
		JsonObject d3 = results.next();
		assertTrue(!results.hasNext());
	}
}
