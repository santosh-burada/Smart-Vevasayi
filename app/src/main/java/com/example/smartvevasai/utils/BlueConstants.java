package com.example.smartvevasai.utils;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;

public class BlueConstants {
	public static final String BLUETOOTH_NOT_SUPPORTED = "Bluetooth not supported by device.";
	public static final String BLUETOOTH_ENABLED = "Bluetooth enabled.";
	public static final String DEVICE_NAME = "YOUR_DEVICE_NAME";
	public static final ArrayList<BluetoothDevice> sBlueDevices = new ArrayList<BluetoothDevice>();
	public static BluetoothDevice sArduinoBlueDevice = null;
	// Universal id for all serial devices
	public static final String BLUE_UUID = "YOUR_DEVICE_UUID";

	// A sample uuid for devices to communicate
	public static final String SAMPLE_UUID = "SAMPLE_UUID";
	public static final int INT_ZERO = 0;
	public static final String EXIT_APP = "Bluetooth not enabled. Leaving app.";
	public static final String NO_PAIRED_DEVICE_ERROR = "No paired device found. Pair with serial device.";

	// Yuktix Blue Activity
	public static final String KEY_VARIABLE = "variable";
	public static final String KEY_VARIABLE_VALUE = "value_variable";

	public static final String KEY_LAST_MAX_TEMP = "max_temp";
	public static final String KEY_LAST_MIN_TEMP = "min_temp";

	public static final String KEY_LAST_MAX_HUMIDITY = "max_humidity";
	public static final String KEY_LAST_MIN_HUMIDITY = "min_humidity";

	public static final String KEY_LAST_MAX_LUMINOUS = "max_luminous";
	public static final String KEY_LAST_MIN_LUMINOUS = "min_luminous";

	public static final String[] VARIABLE_NAMES = { "TEMPERATURE", "HUMIDITY",
			"LUMINOUS INTENSITY" };

}
