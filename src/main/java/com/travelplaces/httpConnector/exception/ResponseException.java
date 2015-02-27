package com.travelplaces.httpConnector.exception;

/**
 * HTTPResponse code exception
 * 
 * @author craigp
 * @since 27/2/2015
 * @version 0.1
 */
public class ResponseException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1315961115205775785L;

	/**
	 * 
	 * @param responseCode
	 */
	public ResponseException(String message)
	{
		super(message);
	}
	
	/**
	 * 
	 * @param message
	 * @param throwable
	 */
	public ResponseException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
	
	/**
	 * 
	 * @param throwable
	 */
	public ResponseException(Throwable throwable)
	{
		super(throwable);
	}
}