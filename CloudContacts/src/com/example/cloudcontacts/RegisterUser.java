package com.example.cloudcontacts;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class RegisterUser extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_user, menu);
		return true;
	}
	
	
	 public void regisButton(View v){
		 Toast.makeText(this, "Registration worked", Toast.LENGTH_SHORT).show();
	 }
}


