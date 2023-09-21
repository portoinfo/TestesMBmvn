package mb.news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONObject;

//import com.google.gson.Gson;


public class NotificateCrm {

	final String URL_DEVELOP = "https://crm-api.builderall.io"; 	// develop
	final String URL_HOMOLOG = "https://crm-api.builderall.info"; 	// homolog 
	final String URL_PRODUCTION = "https://crm-api.eb4us.com"; 	// production
	final String client_secret = "Ob8L4chWGQQSNyuLfDDrnf0huhJEiu6LkNJA7X3I";
	final String grant_type = "client_credentials";
	final String client_id = "2";
	final String scope = "*";

	private static String access_token = null, uuid = null;
	private static JSONObject jsonObj = null; //  httpRequest
	private static long expires_in = 0, expireMillis = 0 ;
	
	public NotificateCrm() {
		long actualMillis = 0;
		long diffMillis = 0;
		if (access_token == null) {
			Map<String,Object> params = new LinkedHashMap<String, Object>();
			params.put("grant_type", grant_type);
		    params.put("client_id", client_id);
	    	params.put("scope", scope);
	    	params.put("client_secret", client_secret);
			jsonObj = getToken();
			showKeyValueOfResponse(jsonObj);
			
		}
		Calendar c1 = Calendar.getInstance();
		actualMillis = c1.getTimeInMillis();
		System.out.println("Data atual: " + c1.getTime() + " - millis: " + actualMillis);
		diffMillis = expireMillis - c1.getTimeInMillis();
		System.out.println("Falta para expirar: " + diffMillis);
		if (diffMillis < 10000) {
			access_token = null;
			System.out.println("Token has expired! Necessary get access_token again!");
			jsonObj = getToken();
			showKeyValueOfResponse(jsonObj);
			if (access_token == null) {
				System.out.println("Error trying get access token!");
			}
		}
	}
	
	public JSONObject getToken() {

		Map<String,Object> params = new LinkedHashMap<String, Object>();
		params.put("grant_type", grant_type);
	    params.put("client_id", client_id);
    	params.put("scope", scope);
    	params.put("client_secret", client_secret);
    	String route = "/oauth/token";
		jsonObj = postRequest(route, params);
		showKeyValueOfResponse(jsonObj);
		if (jsonObj != null) {
			access_token = null;
			jsonObj.keySet().forEach(keyStr ->
			{
				if (keyStr.equals("access_token")) {
					access_token = jsonObj.get(keyStr).toString();
					System.out.println("key: "+ keyStr + " value: " + access_token);
				} else if (keyStr.equals("expires_in")) {
					expires_in = Long.parseLong(jsonObj.get(keyStr).toString());
					System.out.println("key: "+ keyStr + " value: " + expires_in);
					expires_in *= 1000;
					Calendar c = Calendar.getInstance();
					long actualMillis = c.getTimeInMillis();
					System.out.println("Data atual: " + c.getTime() + " - millis: " + actualMillis);
					expireMillis = actualMillis + expires_in;
					Date expireDate = new Date(expireMillis); 
					System.out.println("Date expiration: " + expireDate);
				}
			});
		}
		return jsonObj;
	}
	
	public JSONObject sendNotificatication(String route, Map<String,Object> params, String authorization, String uuid) {
		
		String resp = executePost(
			"https://crm-api.builderall.info/api/v2/journey/event",
			(new JSONObject(params)).toString(),
			authorization, uuid
		);
	
		System.out.println("Retorno de executePost() : " + resp);

		jsonObj = new JSONObject(resp);

		return jsonObj;
	}

	public JSONObject postRequest(String route, Map<String,Object> params) {
		jsonObj = null;
		try {
			URL apiURL = new URL(URL_HOMOLOG + route);
	    	
		    StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String,Object> param : params.entrySet()) {
	            if (postData.length() != 0) postData.append('&');
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
            	postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
		    byte[] postDataBytes;
			postDataBytes = postData.toString().getBytes("UTF-8");

		    HttpURLConnection conn = (HttpURLConnection) apiURL.openConnection();
		    conn.setRequestMethod("POST");
	    	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(postDataBytes);
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			StringBuffer response = new StringBuffer("");
			while ((line = rd.readLine()) != null) {
				response.append(line);
			}
			rd.close();
			
			jsonObj = new JSONObject(response.toString().trim());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
	
	private void showKeyValueOfResponse(JSONObject jsonObj) {

		jsonObj.keySet().forEach(keyStr ->
		{
			Object keyvalue = jsonObj.get(keyStr);
			System.out.println("key: "+ keyStr + " value: " + keyvalue);
		});

	}
	
    public String executePost(String targetURL, String jsonInputString, String authorization, String uuid) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", 
                "application/json");
            
            connection.setRequestProperty("uuid", uuid);
            
            connection.setRequestProperty("authorization", authorization);

            connection.setRequestProperty("Content-Length", 
                Integer.toString(jsonInputString.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");  

            connection.setUseCaches(false);
            connection.setDoOutput(true);
            
            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);			
            }

            //Get Response  
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
            connection.disconnect();
            }
        }
    }
	public static void main(String[] args) {

		NotificateCrm notif = new NotificateCrm();
		
		//jsonObj = notif.getToken();
		//notif.showKeyValueOfResponse(jsonObj);
		if (access_token != null) {
/*			
			// verifying token expiration time
			Calendar c1 = Calendar.getInstance();
			actualMillis = c1.getTimeInMillis();
			System.out.println("Data atual: " + c1.getTime() + " - miilis: " + actualMillis);
			diffMillis = expireMillis - c1.getTimeInMillis();
			System.out.println("Falta para expirar: " + diffMillis);
			if (diffMillis < 10000) {
				// get token again
			}
*/
			String uuid = "4d6a0d5b-4e32-4c85-b0a9-ede272040d58";

			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			sdf.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
			String date = sdf.format( new Date() );
			//System.out.println("Date UTC formatted: " + date );

			Map<String,Object> params = new LinkedHashMap<String, Object>();
			params = new LinkedHashMap<String, Object>();
    		params.put("slug", "mailingboss_join_workflow");
    		params.put("name", "Test Adelcio");
    		params.put("type", "end_user");
    		params.put("performed_at", date); 

			Map<String,Object> paramExtra = new LinkedHashMap<String, Object>();
			paramExtra.put("id", "12345");
			paramExtra.put("subject", "Hello World!");
			params.put("extra", paramExtra);
			
			Map<String,Object> paramLead = new LinkedHashMap<String, Object>();
			paramLead.put("email", "adelcio@mail.com");
			paramLead.put("lead", "Lead Adelcio");
			params.put("lead", paramLead);
		    System.out.println("params: " + params);

			jsonObj = notif.sendNotificatication("/api/v2/journey/event", params, "Bearer " + access_token, uuid);
			if (jsonObj != null) {
				notif.showKeyValueOfResponse(jsonObj);						
			} else {
				System.out.println("sendNotificatication() returns NULL");
			}	

		} else {
			System.out.println("Access_token returns NULL");
		}

	}

}
