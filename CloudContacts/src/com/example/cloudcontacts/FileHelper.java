
package com.example.cloudcontacts;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
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

	public static byte[] readFile(String fileName){
		int bufferSize = 1024;
		byte[] buf = new byte[bufferSize];
		
		FileInputStream input = null;
		try {
			input = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		try{
	    int bytesRead = input.read(buf);
	    
	    while (bytesRead != -1) {
	      output.write(buf, 0, bytesRead);
	      bytesRead = input.read(buf);
	    }
	    output.flush();
		}catch(IOException e){
			
		}
		
		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output.toByteArray();
	}
}


/*
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

*/