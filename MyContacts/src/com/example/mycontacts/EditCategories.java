package com.example.mycontacts;

import com.example.mycontacts.R;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class EditCategories extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_categories);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		TextView[] categoryArr = {
				(TextView) findViewById(R.id.editCat1), 
				(TextView) findViewById(R.id.editCat2),
				(TextView) findViewById(R.id.editCat3),
				(TextView) findViewById(R.id.editCat4),
				(TextView) findViewById(R.id.editCat5)
				};
		
		DatabaseConnector db = new DatabaseConnector(this);
		db.open();
		Cursor categories = db.getAllCategories();
		categories.moveToFirst();
		int nameIdx = categories.getColumnIndex("name");
		for(int i = 0; i < categoryArr.length; i++){
			categoryArr[i].setText(categories.getString(nameIdx));
			categories.moveToNext();
		}
		categories.close();
		db.close();
	}
	
	public void saveButton(View view){
		TextView[] categoryArr = {
				(TextView) findViewById(R.id.editCat1), 
				(TextView) findViewById(R.id.editCat2),
				(TextView) findViewById(R.id.editCat3),
				(TextView) findViewById(R.id.editCat4),
				(TextView) findViewById(R.id.editCat5)
				};
		

		DatabaseConnector db = new DatabaseConnector(this);
		db.open();
		
		for(int i = 0; i < categoryArr.length; i++){
			db.updateCategory(i, categoryArr[i].getText().toString());
		}
		
		db.close();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit_categories, menu);
		return true;
	}

}
