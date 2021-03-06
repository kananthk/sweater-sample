package org.grayleaves.sweater;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HealthResponse {

	public static String INSTANCE_KEY = "CF_INSTANCE_INDEX";
	protected static final String YARN_SERVICE_URL = "YARN_SERVICE_URL";
	public static String INSTANCE_VALUE = "-1";
	private static String status = "UP";
	private static  String targetURL = "no url provided"; 

	//public String getStatus() {
		//return status;
//	}
	public HealthResponse() {
		buildURL();
		try {
			getStatus() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setStatus(String status) {
	}

	public String getCfInstanceIndex() {
		return INSTANCE_VALUE;
	}

	public void setCfInstanceIndex(String cfInstanceIndex) {
	}
	
	private void buildURL() {
		String url = System.getenv(YARN_SERVICE_URL); 
		if (url != null) {
			targetURL = url;
		}
		System.out.println("WebYarnService target url: "+targetURL);
	}
	
	public String getTargetURL() {
		return targetURL;
	}
	
	
	public  String getStatus() throws IOException {
		 
        try {
            URL siteURL = new URL(targetURL);
            HttpURLConnection connection = (HttpURLConnection) siteURL
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
 
            int code = connection.getResponseCode();
		 System.out.println("code :"+code);
            if (code == 200) {
                status = "UP" ;
		    System.out.println("inside if for ***UP** "+ status);
            }
            else {
            	
            	 status = "DOWN" ;
            	
            }
        } catch (Exception e) {
            status = "DOWN";
        }
        return status;
    }

	
}
