package com.example.cloudcontacts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;

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
		LinkedList<Integer> intsRead = new LinkedList<Integer>();
		try{
			FileInputStream fis = new FileInputStream(fileName);
			int read = 0;
			while((read = fis.read()) != -1){
				intsRead.add(read);
			}
		}catch(Exception e){

		}

		for(int i : intsRead){
			//TODO: to bytes
		}

		return null;
	}
}
