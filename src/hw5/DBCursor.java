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
	
	DBCollection result;
	long count;
	int current = 0;
	
	
	public DBCursor(DBCollection collection, JsonObject query, JsonObject fields) throws Exception {
		//find all
		this.result = collection;
		
//		JsonObject toReturn = new JsonObject();
//		for (long i = 0; i < collection.count(); i++) {
//			JsonObject toAdd = collection.getDocument(i);
//			for (String key : toAdd.keySet()) {
//				toReturn.add(key, toAdd.get(key));
//			}
//		}
		
		//handle the query
		if (query != null) {
			this.result = collection;
//			Set<Entry<String, JsonElement>> querySet = query.entrySet();
//			ArrayList<String> toRemove = new ArrayList<>();
//			for (long i = 0; i < this.result.count(); i++) {									  									//for each row
//				HashMap<String, JsonElement> document = new Gson().fromJson(this.result.getDocument(i).toString(), HashMap.class);	//row to hashmap for better access time
//				for (Entry<String, JsonElement> entry : querySet) {				//for each query
//					if (document.get(entry.getKey()) != entry.getValue()) {
//						toRemove.add(entry.getKey());
//					}
//				}
//			}
			
//			ArrayList<String> toRemove = new ArrayList<>();
//			Set<String> queryKeys= query.keySet();
//			for (long i = 0; i < this.result.count(); i++) { //iterate through rows in results to compare with query 
//				JsonObject row = this.result.getDocument(i);
//				for (String key : queryKeys) {
//					JsonElement element = row.get(key);
//					if (element != query.get(key)) {
//						toRemove.add(row.get(key).toString());
//						this.result.drop();
//					}
//				}
//				
//			}
			
		}
		//handle the projection 
		if (fields != null) {
			
		}
		this.count = this.count();
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
			return this.result.getDocument(this.current - 1);
		}
		else return null;
	}

	
	/**
	 * Returns the total number of documents
	 */
	public long count() {
		return this.result.count();
	}

}
