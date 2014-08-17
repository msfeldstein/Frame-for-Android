package co.extrastrength.frame;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.AsyncTask;

public class JSONFetcher {
	public interface JSONCallback {
		public void receivedJSON(JSONArray obj);
	}
	
	public static void getJson(final String urlString, final JSONCallback cb){
		AsyncTask<URL, Integer, JSONArray> asyncTask = new AsyncTask<URL, Integer, JSONArray>() {
			@Override
			protected JSONArray doInBackground(URL... params) {
				InputStream is = null;
				String result = "";
				JSONArray jsonObject = null;
				
				// HTTP
				try {	    	
					HttpClient httpclient = new DefaultHttpClient(); // for port 80 requests!
					HttpGet httppost = new HttpGet(urlString);
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity entity = response.getEntity();
					is = entity.getContent();
				} catch(Exception e) {
					return null;
				}
			    
				// Read response to string
				try {	    	
					BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
						System.out.println("LINE " + line);
					}
					is.close();
					result = sb.toString();	            
				} catch(Exception e) {
					return null;
				}
		 
				// Convert string to object
				try {
					jsonObject = new JSONArray(result);            
				} catch(JSONException e) {
					e.printStackTrace();
					return null;
				}
		    
				return jsonObject;
			}
			
			protected void onPostExecute(JSONArray result) {
				cb.receivedJSON(result);
			};
		};
		URL url;
		try {
			url = new URL(urlString);
			asyncTask.execute(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("airframe shiitttt" + e.getLocalizedMessage());
		}
		
	}
}
