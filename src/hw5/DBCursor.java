package hw5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DBCursor implements Iterator<JsonObject>{
	
//	DBCollection result;
	long count;
	int current = 0;
	ArrayList<JsonObject> result = new ArrayList<>();
	
	public DBCursor(DBCollection collection, JsonObject query, JsonObject fields) throws Exception {
		
		//find all
//		this.result = collection;
		
//		JsonObject toReturn = new JsonObject();
		for (long i = 0; i < collection.count(); i++) {
			result.add(collection.getDocument(i));
		}
		
		//handle the query
		if (query != null) {

			
		}
		//handle the projection 
		if (fields != null) {
			
		}
		this.count = this.result.size();
	}
	
	/**
	 * Returns true if there are more documents to be seen
	 */
	public boolean hasNext() {
		return current == count ? false : true;
	}

	/**
	 * Returns the next document
	 */
	public JsonObject next() {
		if (this.hasNext()) {
			this.current ++;
//			return this.result.getDocument(this.current - 1);
			return this.result.get(this.current - 1);
		}
		else return null;
	}

	
	/**
	 * Returns the total number of documents
	 */
	public long count() {
		return this.result.size();
	}

}
