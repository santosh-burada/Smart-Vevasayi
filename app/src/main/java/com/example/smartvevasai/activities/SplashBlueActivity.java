package com.example.smartvevasai.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;


import androidx.fragment.app.FragmentActivity;

import com.example.smartvevasai.utils.BlueConstants;
import com.example.smartvevasai.utils.BlueUtils;
import com.example.smartvevasai.utils.MyLog;
import com.example.smartvevasai.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SplashBlueActivity extends FragmentActivity {
	private static final String TAG = com.example.smartvevasai.activities.SplashBlueActivity.class.getSimpleName();
	private BluetoothAdapter mBluetoothAdapter = null;
	private static final int BLUE_ENABLE_CODE = 11;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_blue);
		initialize();
	}

	private void initialize() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			BlueUtils.showToast(BlueConstants.BLUETOOTH_NOT_SUPPORTED);
			finish();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Enable bluetooth if not enabled
		if (!mBluetoothAdapter.isEnabled()) {
			Intent blueIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(blueIntent, BLUE_ENABLE_CODE);
		} else {
			findPairedDevices();
		}
	}

	/*
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String msg = null;
		switch (resultCode) {
		case RESULT_OK:
			if (requestCode == BLUE_ENABLE_CODE) {
				msg = BlueConstants.BLUETOOTH_ENABLED;
				findPairedDevices();
			}
			break;
		case RESULT_CANCELED:
			if (requestCode == BLUE_ENABLE_CODE) {
				msg = BlueConstants.EXIT_APP;
				finish();
			}
			break;
		}
		if (msg != null) {
			BlueUtils.showToast(msg);
		}
	}

	/**
	 * Method to fetch the paired devices
	 */
	private void findPairedDevices() {
		MyLog.logText("Starting Finding Paired devices");
		BlueUtils.LogD(TAG, "Starting Finding Paired devices");
		Set<BluetoothDevice> mBlueDevices = mBluetoothAdapter
				.getBondedDevices();

		if (mBlueDevices != null
				&& mBlueDevices.size() > BlueConstants.INT_ZERO) {
			Iterator<BluetoothDevice> deviceIterator = mBlueDevices.iterator();
			List<BluetoothDevice> devicesList = new ArrayList<BluetoothDevice>(
					mBlueDevices.size());

			while (deviceIterator.hasNext()) {
				BluetoothDevice bluetoothDevice = (BluetoothDevice) deviceIterator
						.next();
				devicesList.add(bluetoothDevice);
			}

			com.example.smartvevasai.activities.ConnDeviceFragment frag = (com.example.smartvevasai.activities.ConnDeviceFragment) getSupportFragmentManager()
					.findFragmentByTag(com.example.smartvevasai.activities.ConnDeviceFragment.TAG);
			if (frag != null && frag.isVisible()) {
				// BlueUtils.showDebugToast("--- " + frag.isAdded() + "  ---  "
				// + frag.isVisible());
				frag.dismissAllowingStateLoss();
			} else {
				// Show a dialog with the list
				com.example.smartvevasai.activities.ConnDeviceFragment connDevFragment = new com.example.smartvevasai.activities.ConnDeviceFragment();
				connDevFragment.setmPairedDevices(devicesList);
				connDevFragment.show(getSupportFragmentManager(),
						com.example.smartvevasai.activities.ConnDeviceFragment.TAG);
			}

		} else {
			BlueUtils.showToast(BlueConstants.NO_PAIRED_DEVICE_ERROR);
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		BlueUtils.LogI(TAG, "Splash Blue Activity Finished.");
	}

}
