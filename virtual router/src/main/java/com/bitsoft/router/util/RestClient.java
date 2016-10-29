package com.bitsoft.router.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

@SuppressWarnings("deprecation")
public class RestClient {

	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;
	private String url;
	private int responseCode;
	private String message;
	private String username;
	private String password;
	private String response;
	
	public enum RequestMethod {
		GET, POST;
	}
	
	public String getResponse() {
		return response;
	}

	public String getErrorMessage() {
		return message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public RestClient() {
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}

	public void AddParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void AddHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	public void Execute(RequestMethod method) throws Exception {
		switch (method) {
		case GET: {
			try {
				String combinedParams = "";
				String paramString;
				if (!params.isEmpty()) {
					combinedParams += "?";
					for (NameValuePair p : params) {
						if (p.getName() == "accessToken") {
							paramString = p.getName() + "=" + p.getValue();
						} else {
							paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), "UTF-8");
						}
						if (combinedParams.length() > 1) {
							combinedParams += "&" + paramString;
						} else {
							combinedParams += paramString;
						}
					}
				}
				HttpGet request = new HttpGet(url + combinedParams);
				for (NameValuePair h : headers) {
					request.addHeader(h.getName(), h.getValue());
				}
				executeRequest(request, url);
				break;
			} catch (Exception e) {
				throw e;
			}
		}
		case POST: {
			try {
				HttpPost request = new HttpPost(url);
				//RequestConfig config = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).build();
				//request.setConfig(config);

				for (NameValuePair h : headers) {
					request.addHeader(h.getName(), h.getValue());
				}

				if (!params.isEmpty()) {
					JSONObject jsonObject = new JSONObject();
					for (NameValuePair p : params) {
						jsonObject.put(p.getName(), p.getValue());
					}
					String JSONobjectString = "";
					if (jsonObject.toString().contains("\"{")) {
						JSONobjectString = jsonObject.toString().replace("\"{", "{").replace("}\"", "}").replace("\\\"", "\"");
					} else {
						JSONobjectString = jsonObject.toString();
					}
					StringEntity stringEntity = new StringEntity(JSONobjectString);
					stringEntity.setContentEncoding(HTTP.UTF_8);
					request.setEntity(stringEntity);
				}
				executeRequest(request, url);
				break;
			} catch (Exception e) {
				throw e;
			}
		}
		}
	}

	private void executeRequest(HttpUriRequest request, String url) throws Exception {
		HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection = 1000*100;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		//HttpConnectionParams.setSoTimeout(httpParameters, timeoutConnection);
		HttpClient client;
		if (url.startsWith("https://")) {
			client = getSSLHttpClient(httpParameters);
		} else {
			client = new DefaultHttpClient(httpParameters);
		}
		((AbstractHttpClient) client).getCredentialsProvider().setCredentials(
				new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(username, password));
		HttpResponse httpResponse;

		try {
			HttpProtocolParams.setUseExpectContinue(client.getParams(), false);
			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				response = convertStreamToString(instream);
				instream.close();
			}
		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
			throw e;
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			throw e;
		}
	}

	public HttpClient getSSLHttpClient(HttpParams params) {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient(params);
		}
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
