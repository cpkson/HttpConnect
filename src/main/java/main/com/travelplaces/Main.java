package main.com.travelplaces;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import main.com.travelplaces.httpConnector.HttpConnector;
import main.com.travelplaces.httpConnector.exception.ResponseException;

/**
 * This is the entry point to the program
 * 
 * @author craigp
 * @version 0.1
 * @since 27/2/2015
 */
public class Main 
{
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		HttpConnector connection = null;
		
		try
		{
			connection = new HttpConnector("https://www.eventsforce.net/travelplaces/frontend/xt/xtgetBookings.csp");
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		try
		{
			connection.createConnection();
		} 
		catch (IOException e1)
		{
			e1.printStackTrace();
			System.exit(1);
		}
		
		connection.addGetParameter("user", "testing");
		connection.addGetParameter("psw", "testing");
		
		try
		{
			System.out.print(connection.sendGET());
		}
		catch (ProtocolException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (ResponseException e) 
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}