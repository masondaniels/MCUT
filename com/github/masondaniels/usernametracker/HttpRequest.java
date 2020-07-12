package com.github.masondaniels.usernametracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpRequest {

	/*
	 * Super old class I only use for convenience. Wouldn't suggest actually using.
	 */

	private String agent = "Mozilla/5.0";
	private String url;
	private Proxy proxy;
	private int connectionTimeout = 7000;
	private HashMap<String, String> requestPropertyMap = new HashMap<String, String>();
	private String location;
	private int responseCode = 2000;
	private List<String> cookies;
	private String cookie;

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpRequest(String url) {
		this.url = url;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public void addRequestProperty(String key, String value) {
		requestPropertyMap.put(key, value);
	}

	public void clearRequestProperties() {
		requestPropertyMap.clear();
	}

	public String postRequest(String query) {
		try {
			HttpURLConnection con;
			URL obj = new URL(url);
			if (proxy != null) {
				con = (HttpURLConnection) obj.openConnection(proxy);
			} else {
				con = (HttpURLConnection) obj.openConnection();
			}
			con.setConnectTimeout(connectionTimeout);

			Iterator<?> it = requestPropertyMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				con.setRequestProperty((String) pair.getKey(), (String) pair.getValue());
				it.remove();
			}
			if (cookie != null)
				con.setRequestProperty("cookie", cookie);
			con.setRequestProperty("User-Agent", agent);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("Content-Length", Integer.toString(query.length()));
			con.setDoOutput(true);
			con.getOutputStream().write(query.getBytes("UTF8"));
			location = con.getHeaderField("Location");
			int status = con.getResponseCode();
			this.responseCode = status;
			cookies = con.getHeaderFields().get("Set-Cookie");
			if (status == 200) {
				return readResponse(con);
			}

		} catch (IOException e) {

		}
		return null;
	}

	public String getLocation() {
		return location;
	}

	private String readResponse(HttpURLConnection con) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}

	public String getRequest() {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = null;
			if (proxy != null) {
				con = (HttpURLConnection) obj.openConnection(proxy);
			} else {
				con = (HttpURLConnection) obj.openConnection();
			}
			con.setRequestMethod("GET");
			con.setConnectTimeout(connectionTimeout);
			if (cookie != null)
				con.setRequestProperty("cookie", cookie);

			Iterator<?> it = requestPropertyMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				con.setRequestProperty((String) pair.getKey(), (String) pair.getValue());
				it.remove();
			}

			con.setRequestProperty("User-Agent", agent);
			con.setRequestProperty("Content-Type", "application/json; charset=utf8");
			int status = con.getResponseCode();
			this.responseCode = status;
			cookies = con.getHeaderFields().get("Set-Cookie");
			if (status == 200) {
				return readResponse(con);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the cookies
	 */
	public List<String> getCookies() {
		return cookies;
	}

	/**
	 * @param cookies the cookies to set
	 */
	public void setCookies(List<String> cookies) {
		this.cookies = cookies;
	}

	/**
	 * @return the cookie
	 */
	public String getCookie() {
		return cookie;
	}

	/**
	 * @param cookie the cookie to set
	 */
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

}
