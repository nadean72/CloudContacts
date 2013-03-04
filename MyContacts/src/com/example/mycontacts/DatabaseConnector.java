package com.example.mycontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;



public class DatabaseConnector {

	private SQLiteDatabase database;
	
	private DatabaseOpenHelper databaseOpenHelper;
	
	public DatabaseConnector(Context context){
		databaseOpenHelper = new DatabaseOpenHelper(context, "MyContacts", null, 1);
	}
	
	public void open() throws SQLException{
		database = databaseOpenHelper.getReadableDatabase();
	}
	
	public void close(){
		if(database != null)
			database.close();
	}
	
	//add contact
	public long insertContact(String name, String address, String cell, String altcell, String email, String comments, int category){
		ContentValues newRow = new ContentValues();
		newRow.put("name", name);
		newRow.put("address", address);
		newRow.put("cell", cell);
		newRow.put("altcell", altcell);
		newRow.put("email", email);
		newRow.put("comments", comments);
		newRow.put("category", category);
		
		open();
		long id = database.insert("Contacts", null, newRow);
		close();
		return id;
	}
	public void insertCategory(int id, String name){
		ContentValues newRow = new ContentValues();
		newRow.put("_id", id);
		newRow.put("name", name);
		
		open();
		database.insert("Categories", null, newRow);
		close();
	}
	
	//delete contact
	public void deleteContact(int id){
		open();
		database.delete("Contacts", "_id=" + id, null);
		close();
	}
	
	//modify contact
	public void updateContact(int id, String name, String address, String cell, String altcell, String email, String comments, int category){
		ContentValues editRow = new ContentValues();
		editRow.put("name", name);
		editRow.put("address", address);
		editRow.put("cell", cell);
		editRow.put("altcell", altcell);
		editRow.put("email", email);
		editRow.put("comments", comments);
		editRow.put("category", category);
		
		open();
		database.update("Contacts", editRow, "_id=" + id, null);
		close();
	}

	//modify category
	public void updateCategory(int id, String name){
		ContentValues editRow = new ContentValues();
		editRow.put("name", name);
		
		open();
		database.update("Categories", editRow, "_id=" + id, null);
		close();
	}
	
	//list of contacts
	public Cursor getAllContacts(){
		return database.query("Contacts", new String[] {"_id", "name", "address", "cell", "altcell", "email", "comments", "category"}, null, null, null, null, "name");
	}
	
	public Cursor getAllCategories(){
		return database.query("Categories", new String[] {"_id", "name"}, null, null, null, null, "_id");
		
	}
	
	//individual contact
	public Cursor getOneContact(long id){
		return database.query("Contacts", null, "_id=" + id, null, null, null, null);
	}
}
