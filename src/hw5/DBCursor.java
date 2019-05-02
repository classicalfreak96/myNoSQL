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
		
		//set up query and projection fields
		ArrayList<String> keys = new ArrayList<>();
		if (fields != null) {
			keys = new ArrayList<>(collection.getDocument(0).keySet()); //arraylist of keys that are to be removed from final result
			for (String key : fields.keySet()) {		//remove keys that are not supposed to be removed 
				if (fields.get(key).getAsInt() == 1) {
					keys.remove(key);
				}
			}
		}
		
		for (long i = 0; i < collection.count(); i++) {
			JsonObject toAdd = collection.getDocument(i);
			
			//handle query
			if (query != null) {
				
				
			}
			
			//handle projection
			if (fields != null) {
				for (String key : keys) {
					toAdd.remove(key);
				}
			}
			
			//add toAdd to results 
			result.add(toAdd);
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
