package com.example.smartvevasai.utils;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;

public class RTMExceptionLogger {
	private static com.example.smartvevasai.utils.RTMExceptionLogger sInstance;
	private static String mFileName = Environment.getExternalStorageDirectory()
			+ "/YBlueLogs.txt";

	public static com.example.smartvevasai.utils.RTMExceptionLogger getLoggerInstance() {
		if (sInstance == null) {
			sInstance = new com.example.smartvevasai.utils.RTMExceptionLogger();
			return sInstance;
		}
		return sInstance;
	}

	/**
	 * 
	 * @param exception
	 */
	public void writeToLogFile(Exception exception) {
		StackTraceElement[] temp = exception.getStackTrace();
		StringBuilder sb = new StringBuilder();
		sb.append("\nTime : " + new Date().toString());
		sb.append("\nException Message: " + exception.getMessage());
		for (int i = 0; i < temp.length; i++) {
			sb.append("\n\nFile Name : " + temp[i].getFileName());
			sb.append("\nClass Name: " + temp[i].getClassName());
			sb.append("\nMethodName: " + temp[i].getMethodName());
			sb.append("\nLineNumber: " + temp[i].getLineNumber());
		}
		sb.append("\n===============================================================================\n");
		try {
			File f = new File(mFileName);

			long bytesLength = f.length();

			int mbLength = (int) (bytesLength / (1000 * 1000));
			if (mbLength > 2) {
				f.delete();

				f = new File(mFileName);

				f.createNewFile();
			}

			System.out.println("SIZEEEEEEEEEEEEEE************" + f.length());

			if (!f.exists())
				f.createNewFile();
			FileWriter fstream = new FileWriter(mFileName, true);
			BufferedWriter out = new BufferedWriter(fstream);

			out.write(sb.toString());
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @param iData
	 */
	public void writeDataToFile(String iData) {
		try {
			File f = new File(mFileName);

			long bytesLength = f.length();

			int mbLength = (int) (bytesLength / (1000 * 1000));
			if (mbLength > 2) {
				f.delete();

				f = new File(mFileName);

				f.createNewFile();
			}

			System.out.println("SIZEEEEEEEEEEEEEE************" + f.length());

			if (!f.exists())
				f.createNewFile();
			FileWriter fstream = new FileWriter(mFileName, true);
			BufferedWriter out = new BufferedWriter(fstream);

			out.write(iData);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
