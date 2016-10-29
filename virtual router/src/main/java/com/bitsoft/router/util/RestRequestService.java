package com.bitsoft.router.util;

import org.apache.log4j.Logger;

import com.bitsoft.router.util.RestClient.RequestMethod;

public class RestRequestService {
	
	private Logger _logger = Logger.getLogger(this.getClass());
	private RestClient restClient = null;
	
	private String restUserID = "admin";
	private String restPassword = "admin123";
	//private String BASE_URL = "http://192.168.2.201:8080/web-app/rest/education/getAttendanceInformation";
	private String BASE_URL = "http://localhost:8080/web-app/rest/education/getAttendanceInformation";
	
	public final String CONTENT_TYPE = "application/json";
	
	public RestResponse sendRestRequ() throws Exception {
		RestResponse restResponse = null;
		try {
			restClient = new RestClient();
			restClient.setUrl(BASE_URL);
			restClient.setUsername(restUserID);
			restClient.setPassword(restPassword);
			restClient.AddHeader("Content-Type", CONTENT_TYPE);
			restClient.AddParam("operation", "requestFromFingerprintDevice");
			restClient.Execute(RequestMethod.POST);
			restResponse = new RestResponse(restClient.getResponseCode(), restClient.getResponse());
		} catch (Exception e) {
			_logger.error("An Exception occured while sending attendance request", e);
		}
		return restResponse;
	}
}












