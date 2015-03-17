package main.com.travelplaces.httpConnector;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import main.com.travelplaces.httpConnector.exception.ResponseException;

/**
 * A class representing HttpConnections
 * @author craigp
 * @since 27/2/2015
 * @version 0.1
 */
public class HttpConnector 
{
	private String url;
	private URL urlObj;
	private HttpURLConnection conn;
	private String parameters;
	private String userAgent;
	
	/**
	 * Constructor
	 * @param url
	 * @throws MalformedURLException 
	 */
	public HttpConnector(String url) throws MalformedURLException
	{
		this.url = url;
		urlObj = new URL(url);
		conn = null;
		parameters = "";
		userAgent = "Mozilla/5.0";
	}
	
	/**
	 * 
	 */
	public HttpConnector()
	{
		this.url = "";
		urlObj = null;
		conn = null;
		parameters = "";
		userAgent = "Mozilla/5.0";
	}
	
	/**
	 * Set the url
	 * @param url
	 * @throws MalformedURLException 
	 */
	public void setUrl(String url) throws MalformedURLException
	{
		this.url = url;
		urlObj = new URL(this.url);
	}
	
	/**
	 * Add parameters to the url
	 * 
	 * @param key
	 * @param value
	 */
	public void addGetParameter(String key, String value)
	{
		if(this.parameters.equalsIgnoreCase(""))
		{
			this.parameters = key + "=" + value;
		}
		else
		{
			this.parameters += "&" + key + "=" + value; 
		}
	}
	
	/**
	 * Return the POST parameters
	 * @return parameters
	 */
	public String getGetParameters()
	{
		return this.parameters;
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	public void createConnection() throws IOException
	{
		conn = (HttpURLConnection) urlObj.openConnection();
	}
	
	/**
	 * Set the user agent, default is Mozilla/5.0
	 * @param userAgent
	 */
	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}
	
	/**
	 * Return the information from a request
	 * 
	 * @return response
	 * @throws ProtocolException
	 * @throws IOException
	 * @throws ResponseException
	 */
	public String sendGET() throws ProtocolException, IOException, ResponseException
	{
		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", this.userAgent);
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		conn.setDoOutput(true);
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.writeBytes(this.parameters);
		out.flush();
		out.close();
		
		int responseCode = conn.getResponseCode();
		if(responseCode < 200 || responseCode > 204)
		{
			throw new ResponseException(String.valueOf(responseCode));
		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		String input;
		StringBuffer response = new StringBuffer();
		
		while((input = in.readLine()) != null)
		{
			response.append(input);
		}
		in.close();
		
		return response.toString();
	}
}