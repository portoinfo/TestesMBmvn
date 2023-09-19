package mb.news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;


public class NotificateCrm {

	final String URL_DEVELOP = "https://crm-api.builderall.io"; 	// develop
	final String URL_HOMOLOG = "https://crm-api.builderall.info"; 	// homolog 
	final String URL_PRODUCTION = "https://crm-api.eb4us.com"; 	// production
	private static String access_token = null, uuid = null;
	private static JSONObject jsonObj = null; //  httpRequest
	
	public JSONObject getToken(String route, Map<String,Object> params, String authorization, String uuid) {
/*
{
	"grant_type": "client_credentials",
	"client_id": 2,
	"scope": "*",
	"client_secret": Ob8L4chWGQQSNyuLfDDrnf0huhJEiu6LkNJA7X3I 
}
 */
		jsonObj = postRequest(route, params, authorization, uuid);
		return jsonObj;
	}
	
	public JSONObject sendNotificatication(String route, Map<String,Object> params, String token, String uuid) {
		jsonObj = postRequest(route, params, token, uuid);

		return jsonObj;
	}

	public JSONObject postRequest(String route, Map<String,Object> params, String authorization, String uuid) {
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
	        //System.out.println("PostData: " + postData.toString());
		    byte[] postDataBytes;
				postDataBytes = postData.toString().getBytes("UTF-8");
		    HttpURLConnection conn = (HttpURLConnection) apiURL.openConnection();
		    conn.setRequestMethod("POST");
		    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			conn.setRequestProperty("authorization", authorization);
			conn.setRequestProperty("uuid", uuid);
		    conn.setDoOutput(true);
		    conn.getOutputStream().write(postDataBytes);
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line = "";
			StringBuffer response = new StringBuffer("");
		    boolean ret = false;
		    while ((line = rd.readLine()) != null) {
		    	 //System.out.println("Result: " + line);
		    	 response.append(line);
		    }
		    rd.close();
		    //System.out.println("Result: " + response);
			
			jsonObj = new JSONObject(response.toString().trim());
// just a example of response
			//String response = "{\"token_type\":\"Bearer\",\"expires_in\":31622400,\"access_token\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiZTA2NGFhZTExYjI1OGQ1ZTRlNGY0NDNlYmJmM2NiZDUzNmFlMjAxOTE1MDMxODA1NTIxNjI5YWU3YTdlNzQ3ZGZhZDhlOGRiZmFhZTg0OWUiLCJpYXQiOjE2OTQ3MTI3NzIsIm5iZiI6MTY5NDcxMjc3MiwiZXhwIjoxNzI2MzM1MTcyLCJzdWIiOiIiLCJzY29wZXMiOlsiKiJdfQ.KGFJtt4BJ21TdIOnGgg83jz_X9TQ3nUAvazkDu8XrFGLhA3SenbXTXUgC8pelqUduBSAxBr9tmn80KOO8vJX6eLKdS35nXLD_Ng-CY6B6CZ4kzUMP_Q3b-b-74S38AOsPHIw1hbD6gnJJAOH-psHeHz6gCGRx5bdOixNk3-LMjpjccnI66WrKo3BwRjbxEXgTg-PNpX-WWokBx7VOJGqvBic2yx2GUZxOxIrWo_t-uxBVntPOQWZ9CwhbTlCXW5dV1fyGz9k8PYwzjUi6bKkkwyHH_nUWKDOHF5Jkw3P7zHMGvD6TWg07Ovb2Hl3dPGG9nXTTH4lCB5rPPmEzd6szYV2gpXnrBJq2VcRDxMYW--g-IPsjOqXTCTah8Dwgb0d8TwJSJqpCeuguB7sGNaqueaNSzE_aL6J80Qwcay9wBJv5ldtup6_ARjU6OKu86Z6va-hz2DyoLU9SPLYwL2_-Lsgo-5eweZsfb5j4WnSVRiChHMaVmbnLqxQX9GuJ2fjpMpYGzKoBMkLS5cx_6bZo8DZXGvGoHJXndgaIaNm8Oc2iNuVpCmYgBCmPkAAGFlKzWfURoML4s4q4PLDGJFlcKKs-HLA23i-YmAjaaxspM1HZOMN_77SEVP7ISU1kppCiPbRucYhNdOjh6kPNvHwytv3LfZ8hCSxhH1P6MkfINk\"}";

		} catch (IOException e) {
			//e.getLocalizedMessage();
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
	
	public static void main(String[] args) {
		NotificateCrm notif = new NotificateCrm();
		Map<String,Object> params = new LinkedHashMap<String, Object>();
		params.put("grant_type", "client_credentials");
	    params.put("client_id", "2");
    	params.put("scope", "*");
    	params.put("client_secret", "Ob8L4chWGQQSNyuLfDDrnf0huhJEiu6LkNJA7X3I");
		jsonObj = notif.getToken("/oauth/token", params, null, null);
		notif.showKeyValueOfResponse(jsonObj);
		if (jsonObj != null) {
			access_token = null;
			jsonObj.keySet().forEach(keyStr ->
			{
				if (keyStr.equals("access_token")) {
					access_token = jsonObj.get(keyStr).toString();
					//System.out.println("key: "+ keyStr + " value: " + access_token);
				}
			});
			if (access_token != null) {
				String uuid = "4d6a0d5b-4e32-4c85-b0a9-ede272040d58";

				String pattern = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				
				params = new LinkedHashMap<String, Object>();
	    		params.put("slug", "mailingboss_join_workflow");
	    		params.put("name", "Test Adelcio");
	    		params.put("type", "end_user");
	    		params.put("performed_at", date); 

				CrmExtra mapExtra = new CrmExtra("12345", "Hello World!");
				Gson gson = new Gson();
				String jsonStr = gson.toJson(mapExtra);			    
				//System.out.println("mapExtra: ");
			    //System.out.println(jsonStr);
				params.put("extra", jsonStr);
				
				CrmLead mapLead = new CrmLead("adelcio@mail.com", "Lead Adelcio");
				jsonStr = gson.toJson(mapLead);
				//System.out.println("mapLead: ");
			    //System.out.println(jsonStr);
				params.put("lead", jsonStr);
				//System.out.println("Params: ");
			    //System.out.println(params);

				jsonObj = notif.sendNotificatication("/api/v2/journey/event", params, "Bearer " + access_token, uuid);
				if (jsonObj != null) {
					notif.showKeyValueOfResponse(jsonObj);						
				} else {
					System.out.println("sendNotificatication() retorns NULL");
				}	
							
			} else {
				System.out.println("Access_token retorns NULL");
			}
		}

	}

}
class CrmLead {
	//mapLead.put("\"email\"", "\"adelcio@mail.com\"");
	//mapLead.put("\"name\"", "\"Lead Adelcio\"");
	String email;
	String name;
	
	public CrmLead(String email, String name) {
		this.email = email;
		this.name = name;
	}
	
}

class CrmExtra {
	//mapExtra.put("\"id\"", "\"12345\"");
	//mapExtra.put("\"subject\"", "\"Hello World!\"");
	String id;
	String subject;
	
	public CrmExtra(String id, String subject) {
		this.id = id;
		this.subject = subject;
	}
	
	public String getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

}
