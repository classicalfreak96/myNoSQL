package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import hw5.Document;

class DocumentTester {

	/*
	 * Things to consider testing:
	 * 
	 * Parsing embedded documents
	 * Parsing arrays
	 * 
	 * Object to primitive
	 * Object to embedded document
	 * Object to array
	 */
	@Test
	public void testParse() {
		String json = "{ \"key\": \"value\" }";
		JsonObject results = Document.parse(json);
		assertTrue(results.getAsJsonPrimitive("key").getAsString().equals("value"));
	}
	
	@Test
	public void testParseArrays() {
		String json = "{ \"numbers\": [{\"type\": \"fax\", \"number\": \"123 456-7890\"}] }";
		JsonObject results = Document.parse(json);
		assertTrue(results.getAsJsonArray("numbers").get(0)
				.getAsJsonObject().get("type").getAsString().equals("fax"));
		assertTrue(results.getAsJsonArray("numbers").get(0)
				.getAsJsonObject().get("number").getAsString().equals("123 456-7890"));
	}
	
	@Test
	public void testParseDoc() {
		String json = "{ \"numbers\": {\"type\": \"fax\", \"number\": \"123 456-7890\"} }";
		JsonObject results = Document.parse(json);
		assertTrue(results.getAsJsonObject("numbers").get("type").getAsString().equals("fax"));
		assertTrue(results.getAsJsonObject("numbers").get("number").getAsString().equals("123 456-7890"));
	}
	
	@Test
	public void testToJsonString() {
		JsonObject json = new JsonObject();
		json.add("key", new JsonPrimitive("value"));
		String results = Document.toJsonString(json);
		assertTrue(results.equals("{\"key\":\"value\"}"));
	}

}
