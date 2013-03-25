package com.example.cloudcontacts;
import android.content.Context;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
public class DatabaseOpenHelper extends SQLiteOpenHelper{

	public DatabaseOpenHelper(Context context, String name, CursorFactory factory, int version){
		super(context, name, factory, version);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		String createContacts = "CREATE TABLE Contacts " +
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"name TEXT, " +
				"address TEXT, " +
				"cell TEXT, " +
				"altcell TEXT, " +
				"email TEXT, " +
				"comments TEXT, " +
				"category INTEGER, " +
				"FOREIGN KEY(category) REFERENCES Categories(id)); ";
		String createCategories = "CREATE TABLE Categories" +
				"(_id INTEGER PRIMARY KEY NOT NULL," +
				"name TEXT);";
		String createUsers= "CREATE TABLE User "+
				"(user TEXT PRIMARY KEY NOT NULL, "+
				"pass TEXT NOT NULL);";
		String createTheme ="CREATE TABLE Theme " +
				"(themeId INTEGER PRIMARY KEY NOT NULL);";
		
		db.execSQL(createCategories);
		db.execSQL(createContacts);
		db.execSQL(createUsers);
		db.execSQL(createTheme);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		
	}
}
