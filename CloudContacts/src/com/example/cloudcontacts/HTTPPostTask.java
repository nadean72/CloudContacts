package com.example.cloudcontacts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.FROYO)
public class HTTPPostTask extends AsyncTask<String, Integer, Boolean> {

	@Override
	protected Boolean doInBackground(String... urlAndData){
		String url = urlAndData[0];
		String filePath = urlAndData[1];
		
		
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		
		byte[] filebytes = FileHelper.readFile(filePath);
		String fileString = Base64.encodeToString(filebytes, Base64.DEFAULT);
		
		List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("data",fileString));
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpClient.execute(httpPost);
			for(Header h : response.getAllHeaders()){
				String s = h.getName() + " => " + h.getValue();
				Log.d("CC", s);
			}
			
			
			byte[] readBytes = new byte[(int) response.getEntity().getContentLength()];
			int numRead = response.getEntity().getContent().read(readBytes);
			String s = new String(readBytes);
			Log.d("CCRES", s);
			
			
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
