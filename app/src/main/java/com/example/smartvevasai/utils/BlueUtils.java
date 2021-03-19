package com.example.smartvevasai.utils;

import android.util.Log;
import android.widget.Toast;

import com.example.smartvevasai.application.SilverlineBlueApplication;


public final class BlueUtils {
	private static final boolean DEBUG = true;

	private BlueUtils() {

	}

	/**
	 * Utility to show Toast messages based upon DEBUG boolean
	 * 
	 * @param iMessage
	 *            message to be shown in the Toast
	 */
	public static void showToast(String iMessage) {
		Toast.makeText(SilverlineBlueApplication.getsContext(), iMessage,
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * Utility to show Toast messages based upon DEBUG boolean
	 * 
	 * @param iMessage
	 *            message to be shown in the Toast
	 */
	public static void showDebugToast(String iMessage) {
		if (DEBUG)
			Toast.makeText(SilverlineBlueApplication.getsContext(), iMessage,
					Toast.LENGTH_SHORT).show();
	}

	/**
	 * Following are the methods for logging based upon a boolean that decides
	 * for DEBUG/RELEASE mode
	 */
	public static void LogV(String iTag, String iMessage) {
		if (DEBUG)
			Log.v(iTag, iMessage);
	}

	public static void LogE(String iTag, String iMessage) {
		if (DEBUG)
			Log.e(iTag, iMessage);
	}

	public static void LogD(String iTag, String iMessage) {
		if (DEBUG)
			Log.d(iTag, iMessage);
	}

	public static void LogW(String iTag, String iMessage) {
		if (DEBUG)
			Log.w(iTag, iMessage);
	}

	public static void LogI(String iTag, String iMessage) {
		if (DEBUG)
			Log.i(iTag, iMessage);
	}

}
