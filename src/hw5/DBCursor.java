package hw5;

import java.util.Iterator;

import com.google.gson.JsonObject;

public class DBCursor implements Iterator<JsonObject>{
	
	DBCollection result;
	long count;
	int current = 0;
	
	
	public DBCursor(DBCollection collection, JsonObject query, JsonObject fields) {
		//find all
		if (query == null && fields == null) {
			result = collection;
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
