package hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class DBCollection {
	
	private String name;
	private DB database;
	private File jsonFile;
	
	/**
	 * Constructs a collection for the given database
	 * with the given name. If that collection doesn't exist
	 * it will be created.
	 */
	public DBCollection(DB database, String name) {
		this.database = database;
		this.name = name;
		this.jsonFile = new File(database.dir.getPath(), name+".json");
		if(!this.jsonFile.exists()) {
			// if the collection does not exist, create a new json for it
			try {
				this.jsonFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns a cursor for all of the documents in
	 * this collection.
	 */
	public DBCursor find() {
		return null;
	}
	
	/**
	 * Finds documents that match the given query parameters.
	 * 
	 * @param query relational select
	 * @return
	 */
	public DBCursor find(JsonObject query) {
		return null;
	}
	
	/**
	 * Finds documents that match the given query parameters.
	 * 
	 * @param query relational select
	 * @param projection relational project
	 * @return
	 */
	public DBCursor find(JsonObject query, JsonObject projection) {
		return null;
	}
	
	/**
	 * Inserts documents into the collection
	 * Must create and set a proper id before insertion
	 * When this method is completed, the documents
	 * should be permanently stored on disk.
	 * @param documents
	 */
	public void insert(JsonObject... documents) {
		for(JsonObject doc : documents) {
			doc.add("mongoid", new JsonPrimitive(this.count()+1));
			String docString = "";
			if(this.count() != 0) {
				docString = "\t\n";
			}
			docString += doc.toString() + "\n";
			try {
				// last parameter is append: set to true so it writes to end of file instead of beginning
				FileOutputStream outputStream = new FileOutputStream(this.jsonFile, true);
				byte[] docStringToBytes = docString.getBytes();
				outputStream.write(docStringToBytes);
				outputStream.close();	
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Locates one or more documents and replaces them
	 * with the update document.
	 * @param query relational select for documents to be updated
	 * @param update the document to be used for the update
	 * @param multi true if all matching documents should be updated
	 * 				false if only the first matching document should be updated
	 */
	public void update(JsonObject query, JsonObject update, boolean multi) {
		boolean updatedOne = false;
		for(int i = 0; i < this.count(); i++) {
			JsonObject doc = this.getDocument(i);
			Set<Entry<String, JsonElement>> entrySet = doc.entrySet();
			for (Map.Entry<String, JsonElement> entry: entrySet) {
//			    System.out.println(entry.getKey());
			    if(doc.get(entry.getKey()) != null) {
			    	System.out.println(i);
			    	// then write to the file
			    	doc = update;
			    	updatedOne = true;
			    	break;
			    }
			}
			if(updatedOne && !multi) {
				break;
			}
		}
	}
	
	/**
	 * Removes one or more documents that match the given
	 * query parameters
	 * @param query relational select for documents to be removed
	 * @param multi true if all matching documents should be updated
	 * 				false if only the first matching document should be updated
	 */
	public void remove(JsonObject query, boolean multi) {
		
	}
	
	/**
	 * Returns the number of documents in this collection
	 */
	public long count() {
		try {
			Scanner sc = new Scanner(this.jsonFile);
			sc.useDelimiter("(?m)^\t*$");
			long count = 0;
			while(sc.hasNext()) {
				count++;
				sc.next();
			}
			sc.close();
			return count;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the ith document in the collection.
	 * Documents are separated by a line that contains only a single tab (\t)
	 * Use the parse function from the document class to create the document object
	 * @throws FileNotFoundException
	 */
	public JsonObject getDocument(int i) {
		try {
			Scanner sc = new Scanner(this.jsonFile);
			sc.useDelimiter("(?m)^\t*$");
			for(int j = 0; j < i; j++) {
				sc.next();
			}
			String doc = sc.next();
			sc.close();
			return Document.parse(doc);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Drops this collection, removing all of the documents it contains from the DB
	 */
	public void drop() {
		try {
			FileOutputStream outputStream = new FileOutputStream(this.jsonFile);
			byte[] docStringToBytes = "".getBytes();
			outputStream.write(docStringToBytes);
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
