package cp.systems.httpConnect;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import org.junit.Test;

import uk.co.parknet.httpConnect.HttpConnector;

/**
 * Unit test the HttpConnector Class
 *
 * @author craigp
 * @since 11/03/2015
 * @version 0.1
 */
public class HttpConnectorTest
{

	/**
	 * Check the logic of the addPostParameter
	 * method by checking the result returned
	 * 
	 */
	@Test
	public void addGetParameterTest() 
	{
		HttpConnector connection = null;
		try
		{
			connection = new HttpConnector("https://www.google.com");
		}
		catch (MalformedURLException e)
		{
		
			e.printStackTrace();
		}
		connection.addGetParameter("testKey", "testValue");
		
		if(!connection.getGetParameters().equals("testKey=testValue"))
		{
			fail("getGetParameters() failed");
		}
		
		connection.addGetParameter("secondKey", "secondValue");
		
		if(!connection.getGetParameters().equals("testKey=testValue&secondKey=secondValue"))
		{
			fail("getGetParameters() failed");
		}
	}
	
	@Test
	public void addPostParameterTest()
	{
		HttpConnector connection = null;
		try 
		{
			connection = new HttpConnector("https://www.google.com");
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		connection.addPostParameter("testKey", "testValue");
		
		if(!connection.getPostParameters().equals("testKey=testValue"))
		{
			fail("getPostParameters() failed");
		}
		
		connection.addPostParameter("secondKey", "secondValue");
		
		if(!connection.getPostParameters().equals("testKey=testValue&secondKey=secondValue"))
		{
			fail("getPostParameters() failed");
		}
	}
}