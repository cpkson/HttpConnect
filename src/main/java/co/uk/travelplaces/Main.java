package co.uk.travelplaces;

/*
 * 
 * Main Class File:  	Main.java
 * File:             	Main.java
 * Author:           	Craig Parkinson Copyright (2015). All rights reserved.
 *
 * Class Description: Create a HTTP connection and print a string from
 * the query response or return an HTTP error code and exit.
 */								

import java.io.IOException;
import java.net.MalformedURLException;

import co.uk.travelplaces.httpConnector.HttpConnector;
import co.uk.travelplaces.httpConnector.exception.ResponseException;

/**
 * Main Class
 * 
 * Instantiate a new HTTPConnector Object and
 * query using HTTPS and HTTP
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
		/*
		 * demonstrate httpConnector libary using POST
		 */
		HttpConnector connection = null;
		
		try
		{
			/*
			 * create a secure connection
			 */
			connection = new HttpConnector("https://www.eventsforce.net/travelplaces/frontend/xt/xtgetBookings.csp",true);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		/*
		 * auth params
		 */
		connection.addPostParameter("user", "auto@api.org");
		connection.addPostParameter("psw", "R0v3r1994!M3tr0");
		
		try
		{
			connection.createConnection();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}

		try 
		{
			System.out.println(connection.sendPOST());
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ResponseException e) 
		{
			e.printStackTrace();
		}
		
		
		/*
		 * demonstrate the same queries using GET
		 */
		try
		{
			/*
			 * secure connection
			 */
			connection = new HttpConnector("https://www.eventsforce.net/travelplaces/frontend/xt/xtgetBookings.csp",true);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		/*
		 * auth params
		 */
		connection.addGetParameter("user", "auto@api.org");
		connection.addGetParameter("psw", "R0v3r1994!M3tr0");

		/*
		 * print response from request
		 */
		try 
		{
			System.out.println(connection.sendGET());
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ResponseException e) 
		{
			e.printStackTrace();
		}
	}
}