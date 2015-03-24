package cp.systems.httpConnect;

/*
 * 
 * Main Class File:  	Main.java
 * File:             	HttpConnector.java
 * Author:           	Craig Parkinson Copyright (2015). All rights reserved.
 *
 * Class Description: Create a HTTP/S connection object and return
 * the result of queries on the connection obj.
 */	
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import cp.systems.httpConnect.exception.ResponseException;

/**
 * A class for creating HTTP connections to sites
 * It allows us to query and retrieve responses from API
 * 
 * @author craigp
 * @since 27/2/2015
 * @version 0.1
 */
public class HttpConnector 
{
	private static final String defaultUserAgent = "mozilla/5.0";
	private static final String defaultAcceptLanguage = "en-gb,en-us;q=0.8,en;q=0.5";
	private String url;
	private URL urlObj;
	private HttpURLConnection conn;
	private String getParameters;
	private String postParameters;
	private String userAgent;
	private boolean secure;
	private String acceptLanguage;
	
	/**
	 * Constructor
	 * 
	 * @param url website url
	 * @throws MalformedURLException 
	 */
	public HttpConnector(String url) throws MalformedURLException
	{
		this(url,false,defaultUserAgent, defaultAcceptLanguage);
	}
	
	/**
	 * Allows for the definition of HTTPS vs HTTP
	 * 
	 * @param url website url
	 * @param secure true/false
	 * @throws MalformedURLException 
	 */
	public HttpConnector(String url, boolean secure) throws MalformedURLException
	{
		this(url,secure,defaultUserAgent, defaultAcceptLanguage);
	}
	
	/**
	 * Allows for the definition of the accept language and user agent
	 * the default user agent is mozilla/5.0 and the accept language is english-gb and english-us
	 * 
	 * @param url website url
	 * @param secure true/false
	 * @param userAgent browser
	 * @param acceptLanguage languages to accept
	 * @throws MalformedURLException
	 */
	public HttpConnector(String url, boolean secure, String userAgent, String acceptLanguage) throws MalformedURLException
	{
		this.url = url;
		this.urlObj = new URL(url);
		this.conn = null;
		this.getParameters = "";
		this.postParameters = "";
		this.userAgent = userAgent;
		this.secure = secure;
	}
	
	/**
	 * Set the url
	 * @param url website url
	 * @throws MalformedURLException 
	 */
	public void setUrl(String url) throws MalformedURLException
	{
		this.url = url;
		this.urlObj = new URL(this.url);
	}
	
	
	/**
	 * Add parameters to the url
	 * 
	 * @param key
	 * @param value
	 */
	public void addGetParameter(String key, String value)
	{
		this.getParameters = addParameter(key, value, this.getParameters);
	}
	
	/**
	 * add post parameters
	 * 
	 * @param key
	 * @param value
	 */
	public void addPostParameter(String key, String value)
	{
		this.postParameters = addParameter(key, value, this.postParameters);
	}
	
	/**
	 * generic add parameters method
	 * 
	 * @param key
	 * @param value
	 * @param parameterString
	 * @return
	 */
	private String addParameter(String key, String value, String parameterString)
	{
		if(parameterString.equals(""))
		{
			parameterString = key + "=" + value;
		}
		else
		{
			parameterString += "&" + key + "=" + value; 
		}
		return parameterString;
	}
	
	/**
	 * Return the POST parameters
	 * 
	 * @return parameters
	 */
	public String getGetParameters()
	{
		return this.getParameters;
	}
	
	/**
	 * create a un/secure connection, not required if using GET
	 * 
	 * @throws IOException
	 */
	public void createConnection() throws IOException
	{
		if(this.secure)
		{
			this.conn = (HttpsURLConnection) this.urlObj.openConnection();
		}
		else
		{
			this.conn = (HttpURLConnection) this.urlObj.openConnection();
		}
	}
	
	/**
	 * Lets send a post message and get a response
	 * 
	 * @return String response
	 * @throws IOException 
	 * @throws ResponseException 
	 */
	public String sendPOST() throws IOException, ResponseException
	{
		this.conn.setRequestMethod("POST");
		this.conn.setRequestProperty("User-Agent", this.userAgent);
		this.conn.setRequestProperty("Accept-Language", this.acceptLanguage);
		this.conn.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(this.conn.getOutputStream());
		
		/*
		 * write the parameters as the message body
		 */
		if(this.postParameters != "")
		{
			wr.writeBytes(this.postParameters);
			wr.flush();
		}
		wr.close();
		
		/*
		 * return and check the response code
		 * throw an exception if it isn't a 
		 * 200 error code
		 */
		checkResponseCode(this.conn.getResponseCode());
		
		/*
		 * create an inputStream from the connection
		 * and process it, we return the result as a string
		 */
		return getReturnedMessage(new InputStreamReader(this.conn.getInputStream()));
	}
	
	/**
	 * Return the information from a request
	 * 
	 * @return message
	 * @throws IOException
	 * @throws ResponseException
	 */
	public String sendGET() throws IOException, ResponseException
	{
		if(this.getParameters != "")
		{
			this.urlObj = new URL(this.url + "?" + this.getParameters);
		}

		createConnection();
		
		this.conn.setRequestMethod("GET");
		this.conn.setRequestProperty("User-Agent", this.userAgent);
		checkResponseCode(this.conn.getResponseCode());
		return getReturnedMessage(new InputStreamReader(this.conn.getInputStream()));
	}

	/**
	 * build a string from an inputStreamReader
	 * 
	 * @param inputStreamReader
	 * @return message
	 * @throws IOException
	 */
	private String getReturnedMessage(InputStreamReader inputStreamReader) throws IOException
	{
		/*
		 * Take the input stream from the connection
		 * and build a string line by line. return
		 * the final string when no more data is found
		 * on the connection
		 */
		BufferedReader in = new BufferedReader(inputStreamReader);
		
		String input;
		StringBuffer response = new StringBuffer();
		
		while((input = in.readLine()) != null)
		{
			response.append(input);
		}
		in.close();
		
		return response.toString();
	}

	/**
	 * @param responseCode
	 * @throws ResponseException
	 */
	private void checkResponseCode(int responseCode) throws ResponseException 
	{
		/*
		 * 2XX codes represent successful results
		 * 200 OK
		 * 201 Created
		 * 202 Accepted
		 * 203 Non-Authoritative Information
		 * 204 No Content
		 */
		if(responseCode < 200 || responseCode > 204)
		{
			throw new ResponseException(String.valueOf(responseCode));
		}
	}

	/**
	 * 
	 * @return post parameters
	 */
	public String getPostParameters() 
	{
		return this.postParameters;
	}

	/**
	 * 
	 * @param property
	 * @param value
	 */
	public void setRequestProperty(String property, String value)
	{
		this.conn.setRequestProperty(property, value);
	}
}