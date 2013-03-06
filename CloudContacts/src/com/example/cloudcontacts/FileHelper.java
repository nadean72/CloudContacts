package com.example.cloudcontacts;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

public class FileHelper {
	//These files are stored in /data/data/com.example.mycontacts/files/
	public void putFile(Context c, String fileName, byte[] fileContents){
		try {
			FileOutputStream fos = c.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write(fileContents);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
