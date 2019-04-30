package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
		assertTrue(results.getAsJsonPrimitive("numbers").getAsJsonArray().get(0)
				.getAsJsonObject().get("type").getAsString().equals("fax"));
		assertTrue(results.getAsJsonPrimitive("numbers").getAsJsonArray().get(0)
				.getAsJsonObject().get("number").getAsString().equals("123 456-7890"));
	}
	
	@Test
	public void testParseDoc() {
		String json = "{ \"numbers\": {\"type\": \"fax\", \"number\": \"123 456-7890\"} }";
		JsonObject results = Document.parse(json);
		assertTrue(results.getAsJsonPrimitive("numbers").getAsJsonObject()
				.get("type").getAsString().equals("fax"));
		assertTrue(results.getAsJsonPrimitive("numbers").getAsJsonObject()
				.get("number").getAsString().equals("123 456-7890"));
	}

}
