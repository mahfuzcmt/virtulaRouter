package com.bitsoft.router.util;

public class Response {
	
	private String requestID;
	private String tokenNo;
	private String createdBy;
	private String status;
	private String message;
	private String cause;
	private String  sessionID;
	private String  menuJSON;
	private String firstLogin;
	private String userType;
	private int fpq;
	private int autoLogoutTimeInSecond;
	private boolean conversionPNGTOWSQ;
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getTokenNo() {
		return tokenNo;
	}
	public void setTokenNo(String tokenNo) {
		this.tokenNo = tokenNo;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getMenuJSON() {
		return menuJSON;
	}
	public void setMenuJSON(String menuJSON) {
		this.menuJSON = menuJSON;
	}
	public String getFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(String firstLogin) {
		this.firstLogin = firstLogin;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public int getFpq() {
		return fpq;
	}
	public void setFpq(int fpq) {
		this.fpq = fpq;
	}
	public int getAutoLogoutTimeInSecond() {
		return autoLogoutTimeInSecond;
	}
	public void setAutoLogoutTimeInSecond(int autoLogoutTimeInSecond) {
		this.autoLogoutTimeInSecond = autoLogoutTimeInSecond;
	}
	public boolean isConversionPNGTOWSQ() {
		return conversionPNGTOWSQ;
	}
	public void setConversionPNGTOWSQ(boolean conversionPNGTOWSQ) {
		this.conversionPNGTOWSQ = conversionPNGTOWSQ;
	}
	
	
}

