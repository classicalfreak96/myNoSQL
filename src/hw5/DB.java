package hw5;

import java.io.File;
import java.io.IOException;

public class DB {
	
	private String name;
	private File dir;

	/**
	 * Creates a database object with the given name.
	 * The name of the database will be used to locate
	 * where the collections for that database are stored.
	 * For example if my database is called "library",
	 * I would expect all collections for that database to
	 * be in a directory called "library".
	 * 
	 * If the given database does not exist, it should be
	 * created.
	 */
	public DB(String name) {
		this.name = name;
		this.dir = new File("testfiles/"+name);
		if(!this.dir.exists()) {
			// create DB in testfiles directory if the DB doesn't exist
			this.dir.mkdirs();
		}
		// not sure what to do if the DB exists...
	}
	
	/**
	 * Retrieves the collection with the given name
	 * from this database. The collection should be in
	 * a single file in the directory for this database.
	 * 
	 * Note that it is not necessary to read any data from
	 * disk at this time. Those methods are in DBCollection.
	 */
	public DBCollection getCollection(String name) {
		return new DBCollection(this, name);
	}
	
	/**
	 * Drops this database and all collections that it contains
	 */
	public void dropDatabase() {
		for(String f : this.dir.list()) {
			File temp = new File(this.dir.getPath(),f);
			temp.delete();
		}
		this.dir.delete();
	}
	
	
}
