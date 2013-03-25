package com.example.cloudcontacts;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

public class DownloadDBTask extends AsyncTask<String, String, String> {

	@Override
	protected String doInBackground(String... params) {
		String urlString = params[0];
		String fileString = params[1];
		int count;

		try {

			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			conn.connect();

			int lenghtOfFile = conn.getContentLength();
			Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

			InputStream input = new BufferedInputStream(url.openStream());
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			byte data[] = new byte[1024];

			long total = 0;

			while ((count = input.read(data)) != -1) {
				total += count;
				publishProgress(""+(int)((total*100)/lenghtOfFile));
				output.write(data, 0, count);
			}

			output.flush();
			
			byte[] downloadedBytes = Base64.decode(output.toByteArray(), Base64.DEFAULT);
			
			OutputStream fileoutput = new FileOutputStream(fileString);
			fileoutput.write(downloadedBytes);
			
			output.close();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	protected void onPostExecute(String unused) {

	}

	@Override
	protected void onProgressUpdate(String... progress) {
		Log.d("ANDRO_ASYNC",progress[0]);
	}

}
