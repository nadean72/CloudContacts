package com.example.cloudcontacts;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class HTTPPostTask extends AsyncTask<String, Integer, Boolean> {

	@Override
	protected Boolean doInBackground(String... urlAndData){
		String url = urlAndData[0];
		String key = urlAndData[1];
		String filePath = urlAndData[2];
		
		
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		
		byte[] filebytes = FileHelper.readFile(filePath);
		
		httpPost.setEntity(new ByteArrayEntity(filebytes));
		try {
			HttpResponse response = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//TODO: Add error checking
		return true;
	}

	@Override
	protected void onPostExecute(Boolean successful){
		
	}
	
}
