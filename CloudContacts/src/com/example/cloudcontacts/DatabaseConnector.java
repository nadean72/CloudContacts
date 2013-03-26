package com.example.cloudcontacts;

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
		
		long id = database.insert("Contacts", null, newRow);
		return id;
	}
	
	
	public void insertCategory(int id, String name){
		ContentValues newRow = new ContentValues();
		newRow.put("_id", id);
		newRow.put("name", name);
		
		database.insert("Categories", null, newRow);
	}
	
	//insert Cloud User
	public void insertUser(String userName, String password)
	{
		
		ContentValues users = new ContentValues();
		users.put("user", userName);
		users.put("pass", password);
		
		
		database.delete("User", null, null);
		database.insert("User", null, users);
	}

	public void logoutUser(){

		database.delete("User", null, null);
		
	}
	
	
	public void insertTheme(int theme)
	{
		
		ContentValues themes = new ContentValues();
		themes.put("themeId", theme);
	
		
		database.delete("Theme", null, null);
		database.insert("Theme", null, themes);
	}
	
	//
	public Cursor getUser(){
		return database.query("User", new String[] {"user", "pass"}, null, null, null, null, null);
	}
	
	public Cursor getTheme(){
		return database.query("Theme", new String[]{"themeId"}, null, null, null, null, null);
		
	}
	
	//delete contact
	public void deleteContact(int id){
		database.delete("Contacts", "_id=" + id, null);
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
		
		database.update("Contacts", editRow, "_id=" + id, null);
	}
	

	//modify category
	public void updateCategory(int id, String name){
		ContentValues editRow = new ContentValues();
		editRow.put("name", name);
		
		database.update("Categories", editRow, "_id=" + id, null);
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
