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
//		ArrayList<String> keys = new ArrayList<>();
//		if (!fields.isJsonNull()) {
//			for (long i = 0; i < collection.count(); i++) {
//				keys.add(collection.getDocument(i))
//			}
//		}
		
		ArrayList<String> keys = new ArrayList<>(query.keySet());
		for (long i = 0; i < collection.count(); i++) {
			Boolean add = true;
			JsonObject toAdd = collection.getDocument(i);
			ArrayList<String> toAddKeys = new ArrayList<>(toAdd.keySet());
			
			//iterate through each key in the query, test against values in document
			for (int j = 0; j < keys.size(); j++) {
				String key = keys.get(j);
				//for embedded documents
				if (key.contains(".")) {
					System.out.println("HERE!");
					System.out.println(key);
					String[] embedded = key.split("\\.");
					if (! toAddKeys.contains(embedded[0])) {
						System.out.println("continuing");
						if (j == keys.size() - 1) add = false;
						continue;
						}
//					for (String embed : embedded) {
//						System.out.println(embed);
//					}
					JsonObject toTraverse = toAdd.deepCopy();
					for (int k = 0; k < embedded.length - 1; k++) {
						String toAccess = embedded[k];
						toTraverse = (JsonObject) toTraverse.get(toAccess);
					}
//					System.out.println("toTraverse to string: " + toTraverse.toString());
//					System.out.println("embedded length: " + embedded.length);
					if (toTraverse.get(embedded[embedded.length - 1]).toString().compareTo(query.get(key).toString()) != 0) add = false;
				}
				
				//for un-embedded documents
				else if (toAddKeys.contains(key)) {
					System.out.println("HERE2!");
//					System.out.println("here asdfasdfasdfasdf");
					//if it's an array
					if (query.get(key).isJsonArray()) {
						ArrayList<String> toAddArray = new ArrayList<>();
						ArrayList<String> queryArray = new ArrayList<>();
						for (JsonElement element : toAdd.get(key).getAsJsonArray()) toAddArray.add(element.toString());
						for (JsonElement element : query.get(key).getAsJsonArray()) queryArray.add(element.toString());
						if (toAddArray.size() == queryArray.size()) {
							for (String element : toAddArray) {
								if (!queryArray.contains(element)) add = false;
							}
						}
						else add = false;
					}
					if (toAdd.get(key).toString().compareTo(query.get(key).toString()) != 0) add = false;
				}
				else {
					System.out.println("HERE3!");

					add = false;
				}
			}
			
			//handle projection
			if (fields.size() > 0 && toAdd.size() > 0 && add) {
				for (String key : keys) {
					toAdd.remove(key);
				}
			}
			
			//add toAdd to results 
			if (add) {
				System.out.println("adding: " + toAdd.toString());
				result.add(toAdd);
				}
		}
		System.out.println("Size is: " + this.result.size());
		this.count = this.result.size();
	}
	
	/**
	 * Returns true if there are more documents to be seen
	 */
	public boolean hasNext() {
		return current >= count ? false : true;
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
