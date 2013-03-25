package com.example.cloudcontacts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.StrictMode;

public class URLHelper {
	
	public static int bufferSize =1024;
	
	public boolean retrieveTextData(String urlString)
	{
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		BufferedReader input = null;
		URL url = null;
		
		try
		{
			url = new URL(urlString);
			BufferedReader buff = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String line = buff.readLine();
			boolean newUser = false;
			boolean found = false;
			int count = 0;
			
			while(line != null && !found && count < 5){
				if(line.equals("true")) 
				{
					newUser = true;
					found = true;
				}
				if(line.equals("false"))
				{
					newUser = false;
					found = true;
				}
			}
			//returns true if it successfully saved
			return newUser;
		}
		catch(MalformedURLException e)
		{
		}
		catch(IOException e)
		{
		}
		finally
		{
			try
			{
				if(input != null) input.close();	
				
			}catch(IOException e){}
		}
		return false;
	}

}
