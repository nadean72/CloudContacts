package com.example.cloudcontacts;

import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ContactList extends Activity {

	private ArrayList<String> contactNames;
	private ArrayList<Long> contactIds;
	
    private CharSequence searchText = "";
    
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        DatabaseConnector databaseConnector = new DatabaseConnector(this);
        
        databaseConnector.open();
        Cursor categories = databaseConnector.getAllCategories();
        if (categories.getCount() == 0){
        	databaseConnector.insertCategory(0, "Category1");
        	databaseConnector.insertCategory(1, "Category2");
        	databaseConnector.insertCategory(2, "Category3");
        	databaseConnector.insertCategory(3, "Category4");
        	databaseConnector.insertCategory(4, "Category5");
        	
        }
        categories.close();
        databaseConnector.close();
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact_list);
        

        TextView search = (TextView) findViewById(R.id.searchText);
        search.addTextChangedListener(new TextWatcher(){
        	@Override
        	public void onTextChanged(CharSequence s, int start, int before, int out){
	        	searchText = s;
	        	new PopulateContactListTask().execute((Object[]) null);
        	}
        	
        	public void afterTextChanged(Editable s){
        		
        	}
        	
        	public void beforeTextChanged(CharSequence s, int start, int before, int out){
        		
        	}
        });
        

        String url = "http://softeng.cs.uwosh.edu/students/willic82/test.php";
        String key = "what";
        String file = "/data/data/com.example.cloudcontacts/databases/MyContacts";
        new HTTPPostTask().execute(url, key, file);

    }
    
    protected void onResume(){
    	super.onResume();
        new PopulateContactListTask().execute((Object[]) null);
        populateCategorySpinner();
    }
    
    protected void onStop(){
    	//stuff to write
    	super.onStop();
    }
    
    protected void populateCategorySpinner(){
    	ArrayList<String> catArr = new ArrayList<String>();
    	catArr.add("All");
        Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);
    	DatabaseConnector db = new DatabaseConnector(this);
    	db.open();
        Cursor categories = db.getAllCategories();
        categories.moveToFirst();
        int nameIdx = categories.getColumnIndex("name");
        for(int i = 0; i < 5; i++){
        	catArr.add(categories.getString(nameIdx));
        	categories.moveToNext();
        }
        catArr.add("Edit");

        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, catArr.toArray(new String[catArr.size()]));
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(catAdapter);
        
        categories.close();
        db.close();
        
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView parent, View child, int pos, long id){
        		if(parent.getSelectedItemPosition() == 6){
        			parent.setSelection(0);
        			Intent intent = new Intent(getApplicationContext(), EditCategories.class);
        			startActivity(intent);
        		}else{
        	        new PopulateContactListTask().execute((Object[]) null);
        		}
        	}
        	
        	@Override
        	public void onNothingSelected(AdapterView parent){
        		
        	}
		});
    	
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	if (item.getItemId() == R.id.menu_add_contact){
    		Intent intent = new Intent(getApplicationContext(), ContactView.class);
    		DatabaseConnector database = new DatabaseConnector(this);
    		long id = database.insertContact("New Contact", "", "", "", "", "", 0);
    		intent.putExtra("ID", id);
    		startActivity(intent);
    		return true;
    	}else 
    		if(item.getItemId() == R.id.menu_login_cloud){
    		final AlertDialog.Builder  input = new AlertDialog.Builder(this);
    		LayoutInflater inflater = this.getLayoutInflater();
    		final View view = (inflater.inflate(R.layout.login_cloud,  null));
    		input.setView(view);
    		input.setPositiveButton("Login", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					EditText loginName = (EditText) view.findViewById(R.id.loginName);
					EditText password = (EditText) view.findViewById(R.id.password);
					String login = loginName.getText().toString();
					String pass = password.getText().toString();
					
					
					
					URLHelper helper = new URLHelper();
			    	String dir = "http://softeng.cs.uwosh.edu/students/nadean72/login.php?user="+login+"&pass="+pass;
					if(helper.retrieveTextData(dir))
					{
						DatabaseConnector db = new DatabaseConnector(ContactList.this);
					    db.insertUser(login, pass);
					    Toast.makeText(ContactList.this, "Login Successful", Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}else
					{
						Toast.makeText(ContactList.this, "Username or password is incorrect", Toast.LENGTH_SHORT).show();
					    loginName.setText("");
					    password.setText("");
					}
					
				}
			});//end of dialogInterface.OnclickListener for positiveButton
    		
    		input.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
    			
    		});// end of dialoginterface.OnClickListener for negative Button
    		
    		input.show();
    		return super.onOptionsItemSelected(item);
    	} else
    		if(item.getItemId() == R.id.menu_register_cloud){
    			Intent intent = new Intent(getApplicationContext(), RegisterUser.class);
    			startActivity(intent);
    		}
    	return super.onOptionsItemSelected(item);
    }
   
    
    /*
    public boolean login(String login, String pass)
    {
    	boolean success = false;
    	URLHelper helper = new URLHelper();
    	String dir = "http://softeng.cs.uwosh.edu/students/nadean72/login.php?user="+login+"&pass="+pass;
    	if(helper.retrieveTextData(dir)){
    		Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
    		success=true;
    		//upload the data
    	}
    	else
    		Toast.makeText(this, "Login or password is incorrect", Toast.LENGTH_SHORT).show();
    	
    	return success;
    }
    */
    
    public void clear(){
    	EditText login = (EditText) findViewById(R.id.loginName);
    	EditText pass = (EditText) findViewById(R.id.password);
    	login.setText("");
    	pass.setText("");
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_contact_list, menu);
        return true;
    }

    //Different Thread
    private class PopulateContactListTask extends AsyncTask<Object, Object, Cursor>{
    	protected DatabaseConnector db;
    	
    	@Override
    	protected Cursor doInBackground(Object... params){
	    	db = new DatabaseConnector(ContactList.this);
	    	db.open();
	    	Cursor contacts = db.getAllContacts();
	    	return contacts;
    	}
    	
    	@Override
    	protected void onPostExecute(Cursor contacts){
	    	contactNames = new ArrayList<String>();
	    	contactIds = new ArrayList<Long>();
	        Spinner catSpinner = (Spinner) findViewById(R.id.categorySpinner);
		    if(contacts.getCount() > 0){
		    	contacts.moveToFirst();
		    	boolean pastEnd = false;
		    	do{
		            
		            int nameIndex = contacts.getColumnIndex("name");
		            int idIndex = contacts.getColumnIndex("_id");
		            int catIndex = contacts.getColumnIndex("category");
		            if(catSpinner.getSelectedItemPosition() == 0 || catSpinner.getSelectedItemPosition() - 1 == contacts.getInt(catIndex)){
			            if(contacts.getString(nameIndex).contains(searchText)){
			            	contactNames.add(contacts.getString(nameIndex));
			            	contactIds.add(Long.parseLong(contacts.getString(idIndex)));
			            }
		            }
		            contacts.moveToNext();
		    		if(contacts.getPosition() == contacts.getCount())
		    			pastEnd = true;
		    	}while(!pastEnd);
		        ListView list = (ListView) findViewById(R.id.contactList);
		        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ContactList.this, android.R.layout.simple_list_item_1, android.R.id.text1, contactNames.toArray(new String[contactNames.size()]));
		        list.setAdapter(adapter);
		        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		        	@Override
		        	public void onItemClick(AdapterView parent, View child, int pos, long id){
		        		System.out.println(id);
		        		Intent intent = new Intent(getApplicationContext(), ContactView.class);
		        		intent.putExtra("ID", contactIds.get((int)id));
		        		startActivity(intent);
		        	}
				});
		    }else{
		    	ListView list = (ListView) findViewById(R.id.contactList);
		    	list.setAdapter(null);
		    }
		    contacts.close();
		    db.close();
	    }
    }
    
}
