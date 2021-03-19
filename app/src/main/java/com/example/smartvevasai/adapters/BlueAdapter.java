package com.example.smartvevasai.adapters;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartvevasai.R;

import java.util.List;

/**
 * @author ravikumar
 */
public class BlueAdapter extends BaseAdapter {
	private Activity mActivity = null;
	private LayoutInflater mInflater = null;

	private List<BluetoothDevice> mDeviceList = null;

	public BlueAdapter(Activity iActivity, List<BluetoothDevice> iDeviceList) {
		// TODO Auto-generated constructor stub
		mActivity = iActivity;
		mInflater = LayoutInflater.from(mActivity);
		mDeviceList = iDeviceList;
	}

	@Override
	public int getCount() {
		return mDeviceList.size();// BlueConstants.sBlueDevices.size();
	}

	@Override
	public Object getItem(int iPosition) {
		return mDeviceList.get(iPosition);// BlueConstants.sBlueDevices.get(iPosition);
	}

	@Override
	public long getItemId(int iPosition) {
		return iPosition;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.device_list_item, null);
			holder.mTextView = (TextView) convertView
					.findViewById(R.id.device_name_txv);
			// holder.mToggleBtn = (ToggleButton)
			// convertView.findViewById(R.id.device_pair_toggle);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// Set Device Details
		BluetoothDevice device = mDeviceList.get(position);// BlueConstants.sBlueDevices.get(position);
		holder.mTextView.setText(device.getName());
		// boolean checkedState = false;
		// switch (device.getBondState())
		// {
		// case BluetoothDevice.BOND_BONDED:
		// checkedState= true;
		// break;
		// }
		// holder.mToggleBtn.setChecked(checkedState);

		return convertView;
	}

	private class ViewHolder {
		private TextView mTextView = null;
		// private ToggleButton mToggleBtn = null;
	}

}
