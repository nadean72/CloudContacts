package com.example.cloudcontacts;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterUser extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int theme = getIntent().getIntExtra("theme", 0);
        setTheme(theme);
		setContentView(R.layout.activity_register_user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_user, menu);
		return true;
	}
	
	
	 public void regisButton(View v){
		 EditText username = (EditText) findViewById(R.id.loginName);
		 EditText passwordOne = (EditText) findViewById(R.id.passwordOne);
		 EditText passwordTwo = (EditText) findViewById(R.id.passwordTwo);
		 
		 boolean match = passwordOne.getText().toString().equals(passwordTwo.getText().toString());
		 
		 
		 if(match){
			 URLHelper info = new URLHelper();
			 String url ="http://softeng.cs.uwosh.edu/students/nadean72/register.php?user=" +username.getText().toString()+ "&pass="+passwordOne.getText().toString();
			 boolean success = info.retrieveTextData(url);
			 
			// boolean success=true;
			if(success){
				DatabaseConnector db = new DatabaseConnector(this);
				db.open();
			    db.insertUser(username.getText().toString(), passwordOne.getText().toString());
		        Cursor login = db.getUser();
		        login.moveToFirst();
		       	int userIdx = login.getColumnIndex("user");
		       	String url2 = "http://softeng.cs.uwosh.edu/students/nadean72/upload.php?user=" + login.getString(userIdx);
		       	String file = "/data/data/com.example.cloudcontacts/databases/MyContacts";
		        db.close();
		       	new HTTPPostTask().execute(url2, file);
			    Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
			    this.finish();
			}
			else
			{
				Toast.makeText(this, "User Name Taken", Toast.LENGTH_SHORT).show();
			}
		 }
		 else{
			 Toast.makeText(this, "Passwords Don't Match", Toast.LENGTH_SHORT).show();
		 }
		 
		 
	 }
}


