package com.example.mycontacts;

import java.util.ArrayList;

import com.example.mycontacts.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ContactView extends Activity {

	private long contactId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_contact_view);
	}
	
	protected void onResume(){
		super.onResume();
        String[] cats = new String[] { "Cat1", "Cat2", "Cat3", "Cat4", "Cat5"};
        Spinner spinner = (Spinner) findViewById(R.id.contactCat);
        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cats);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(catAdapter);
        contactId = getIntent().getLongExtra("ID", 0);
        DatabaseConnector databaseConnector = new DatabaseConnector(this);
        
        databaseConnector.open();
        Cursor cursor = databaseConnector.getOneContact(contactId);
        cursor.moveToFirst();
        int nameIdx = cursor.getColumnIndex("name");
        int numIdx = cursor.getColumnIndex("cell");
        int altNumIdx = cursor.getColumnIndex("altcell");
        int addrIdx = cursor.getColumnIndex("address");
        int emailIdx = cursor.getColumnIndex("email");
        int commIdx = cursor.getColumnIndex("comments");
        int catIdx = cursor.getColumnIndex("category");
        
        
        
        TextView name = (TextView) findViewById(R.id.contactName);
        TextView number = (TextView) findViewById(R.id.primNumber);
        TextView altNumber = (TextView) findViewById(R.id.secNumber);
        TextView addr = (TextView) findViewById(R.id.contactAddr);
        TextView email = (TextView) findViewById(R.id.contactEmail);
        TextView comments = (TextView) findViewById(R.id.contactComments);
        populateCategorySpinner();
        Spinner category = (Spinner) findViewById(R.id.contactCat);
        
        name.setText(cursor.getString(nameIdx));
        number.setText(cursor.getString(numIdx));
        altNumber.setText(cursor.getString(altNumIdx));
        addr.setText(cursor.getString(addrIdx));
        email.setText(cursor.getString(emailIdx));
        comments.setText(cursor.getString(commIdx));
        category.setSelection(cursor.getInt(catIdx));
        
        cursor.close();
        databaseConnector.close();
		
	}

    protected void populateCategorySpinner(){
    	ArrayList<String> catArr = new ArrayList<String>();
        Spinner spinner = (Spinner) findViewById(R.id.contactCat);
    	DatabaseConnector db = new DatabaseConnector(this);
    	db.open();
        Cursor categories = db.getAllCategories();
        categories.moveToFirst();
        int nameIdx = categories.getColumnIndex("name");
        for(int i = 0; i < 5; i++){
        	catArr.add(categories.getString(nameIdx));
        	categories.moveToNext();
        }

        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, catArr.toArray(new String[catArr.size()]));
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(catAdapter);
        
        categories.close();
        db.close();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	if (item.getItemId() == R.id.menu_delete_contact){
    		TextView name = (TextView)findViewById(R.id.contactName);
    		
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder
        	.setTitle("Delete Contact")
        	.setMessage("Are you sure you want to delete " + name.getText() + "?")
        	.setIcon(android.R.drawable.ic_dialog_alert)
        	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        	    public void onClick(DialogInterface dialog, int which) {	
            		DatabaseConnector database = new DatabaseConnector(ContactView.this);
            		database.deleteContact((int)contactId);
            		database.close();
            		ContactView.this.finish();
        	    }
        	})
        	.setNegativeButton("No", null)		
        	.show();
    		return true;
    	}else if (item.getItemId() == R.id.menu_save_contact){
    		DatabaseConnector database = new DatabaseConnector(this);
            TextView name = (TextView) findViewById(R.id.contactName);
            TextView number = (TextView) findViewById(R.id.primNumber);
            TextView altNumber = (TextView) findViewById(R.id.secNumber);
            TextView addr = (TextView) findViewById(R.id.contactAddr);
            TextView email = (TextView) findViewById(R.id.contactEmail);
            TextView comments = (TextView) findViewById(R.id.contactComments);
            Spinner category = (Spinner) findViewById(R.id.contactCat);
            
            database.updateContact(
            		(int)contactId, 
            		name.getText().toString(), 
            		addr.getText().toString(), 
            		number.getText().toString(), 
            		altNumber.getText().toString(), 
            		email.getText().toString(), 
            		comments.getText().toString(), 
            		category.getSelectedItemPosition());
            database.close();
            
    		return true;
    	}else
    		return super.onOptionsItemSelected(item);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_contact_view, menu);
		return true;
	}

}
