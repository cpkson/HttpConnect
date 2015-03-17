package test.com.travelplaces.httpConnector;

import static org.junit.Assert.*;
import main.com.travelplaces.httpConnector.HttpConnector;

import org.junit.Test;

/**
 * Unit test the HttpConnector Clase
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
		HttpConnector connection = new HttpConnector();
		connection.addGetParameter("testKey", "testValue");
		
		if(!connection.getGetParameters().equals("testKey=testValue"))
		{
			fail("getPostParameters() failed");
		}
		
		connection.addGetParameter("secondKey", "secondValue");
		
		if(!connection.getGetParameters().equals("testKey=testValue&secondKey=secondValue"))
		{
			fail("getPostParameters() failed");
		}
	}
}
