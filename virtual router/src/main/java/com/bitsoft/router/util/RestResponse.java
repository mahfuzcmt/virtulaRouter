package com.bitsoft.router.util;

public class RestResponse {
	

    private int responseCode;

    private String message;
    
    
    public RestResponse() {
	}

	public RestResponse(int i, String message) {
		super();
		this.responseCode = i;
		this.message = message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
