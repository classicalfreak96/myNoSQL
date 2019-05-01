package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import hw5.DB;
import hw5.DBCollection;
import hw5.DBCursor;

class CollectionTester {

	/*
	 * Things to be tested:
	 * 
	 * Document access (done?)
	 * Document insert/update/delete
	 */
	@Test
	public void testGetDocument() {
		DB db = new DB("data");
		DBCollection test = db.getCollection("test");
		try {
			JsonObject primitive = test.getDocument(0);			
			assertTrue(primitive.getAsJsonPrimitive("key").getAsString().equals("value"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetEmbedded() {
		DB db = new DB("data");
		DBCollection test = db.getCollection("test");
		try {
			JsonObject embedded = test.getDocument(1);
			assertTrue(embedded.getAsJsonObject("embedded").toString().equals("{\"key2\":\"value2\"}"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetArray() {
		DB db = new DB("data");
		DBCollection test = db.getCollection("test");
		try {
			JsonObject array = test.getDocument(2);
			assertTrue(array.getAsJsonArray("array").get(0).getAsString().equals("one"));
			assertTrue(array.getAsJsonArray("array").get(1).getAsString().equals("two"));
			assertTrue(array.getAsJsonArray("array").get(2).getAsString().equals("three"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
