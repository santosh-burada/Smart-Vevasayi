package com.example.smartvevasai.utils;

import android.util.Log;

/**
 * class containing the various log types with the check whether the app is in
 * debug mode and would not display the log if the app is not in debug mode
 * 
 * @author TRAYAMBAK
 */
public class MyLog {

	private static boolean isAllowDebug = true;

	/**
	 * Following are the methods for logging based upon a boolean that decides
	 * for DEBUG/RELEASE mode
	 */
	public static void v(String iTag, String iMessage) {
		if (isAllowDebug)
			Log.v(iTag, "" + iMessage);
	}

	public static void e(String iTag, String iMessage) {
		if (isAllowDebug)
			Log.e(iTag, "" + iMessage);
	}

	public static void d(String iTag, String iMessage) {
		if (isAllowDebug)
			Log.d(iTag, "" + iMessage);
	}

	public static void w(String iTag, String iMessage) {
		if (isAllowDebug)
			Log.w(iTag, "" + iMessage);
	}

	public static void i(String iTag, String iMessage) {
		if (isAllowDebug)
			Log.i(iTag, "" + iMessage);
	}

	/**
	 * method to print the stack trace
	 * 
	 * @param exception
	 *            exception occured
	 */
	public static void printStack(Exception exception) {
		if (isAllowDebug) {
			exception.printStackTrace();
		} else {
			com.example.smartvevasai.utils.RTMExceptionLogger.getLoggerInstance().writeToLogFile(exception);
		}
	}

	/**
	 * method to write content in a file
	 * 
	 * @param iText
	 */
	public static void logText(String iText) {
		com.example.smartvevasai.utils.RTMExceptionLogger.getLoggerInstance().writeDataToFile(iText);
	}

	/**
	 * method to print the stack trace
	 * 
	 * @param error
	 *            error occured
	 */
	public static void printError(Error error) {
		if (isAllowDebug)
			error.printStackTrace();
	}
}
