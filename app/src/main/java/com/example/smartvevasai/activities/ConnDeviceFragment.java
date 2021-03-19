package com.example.smartvevasai.activities;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.example.smartvevasai.R;
import androidx.fragment.app.DialogFragment;

import com.example.smartvevasai.adapters.BlueAdapter;
import com.example.smartvevasai.utils.BlueConstants;

import java.util.List;

/**
 * Sets the default device from the list of number of devices
 * 
 * @author ravikumar
 */
public class ConnDeviceFragment extends DialogFragment implements
		OnItemClickListener {
	public static final String TAG = com.example.smartvevasai.activities.ConnDeviceFragment.class.getSimpleName();
	private ListView mPairedDevLsv = null;
	private BlueAdapter mBlueDeviceAdapter = null;

	private List<BluetoothDevice> mPairedDevices = null;

	public void setmPairedDevices(List<BluetoothDevice> mPairedDevices) {
		this.mPairedDevices = mPairedDevices;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, BlueConstants.INT_ZERO);
	}

	/*
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.paired_dev, container, false);
		initialize(view);
		return view;
	}

	/**
	 * @param iView
	 */
	private void initialize(View iView) {
		mPairedDevLsv = (ListView) iView.findViewById(R.id.paired_devices_lsv);
		mBlueDeviceAdapter = new BlueAdapter(getActivity(), mPairedDevices);
		mPairedDevLsv.setAdapter(mBlueDeviceAdapter);

		mPairedDevLsv.setOnItemClickListener(this);
	}

	/*
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// Start activity to connect and communicate with the paired serial
		// device
		BlueConstants.sArduinoBlueDevice = (BluetoothDevice) parent
				.getItemAtPosition(position);

		Intent intent = new Intent(getActivity(), SilverLineBlueActivity.class);
		startActivity(intent);
		getActivity().finish();
	}

}
