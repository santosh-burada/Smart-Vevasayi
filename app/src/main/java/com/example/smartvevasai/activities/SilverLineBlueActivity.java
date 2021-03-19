package com.example.smartvevasai.activities;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.example.smartvevasai.R;
import androidx.fragment.app.FragmentActivity;

import com.example.smartvevasai.utils.BlueConstants;
import com.example.smartvevasai.utils.BlueUtils;
import com.example.smartvevasai.utils.MyLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Arrays;

public class SilverLineBlueActivity extends FragmentActivity implements
		OnClickListener {
	private static final String TAG = com.example.smartvevasai.activities.SilverLineBlueActivity.class
			.getSimpleName();

	private ToggleButton mBlueSwitchBtn = null;
	private TextView mReceivedTxv = null;
	private Button mConnectBtn = null;
	private Button mMoistureBtn = null;
	// private ProgressBar mProgressBar = null;
	private TextView mDataLoggerTxv = null;

	private Thread mClientThread = null;
	private Thread mServerThread = null;
	private BluetoothSocket mBlueSocket = null;
	private OutputStream mBlueOutStream = null;
	private InputStream mBlueInputStream = null;

	private StringBuffer mMainStringBuff = null;
	private String mToastMsg = null;
	private boolean mConnCheck = true;
//	private static final int MAX_MOISTURE = 1024;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control_blue);
		initializeView();
	}

	private void initializeView() {
		mReceivedTxv = (TextView) findViewById(R.id.received_txv);
		// mProgressBar = (ProgressBar) findViewById(R.id.moisture_pb);
		mDataLoggerTxv = (TextView) findViewById(R.id.data_logger_txv);
		mMoistureBtn = (Button) findViewById(R.id.moisture_btn);
		mBlueSwitchBtn = (ToggleButton) findViewById(R.id.switch_btn);
		mConnectBtn = (Button) findViewById(R.id.connect_btn);

		// mProgressBar.setMax(MAX_MOISTURE);

		mBlueSwitchBtn.setOnClickListener(this);
		mConnectBtn.setOnClickListener(this);
		mMoistureBtn.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		BlueUtils.LogD(TAG, "OnStart connecting with the device.");
		startClientThread();
	}

	@Override
	protected void onStop() {
		super.onStop();
		closeStreams();
	}

	@Override
	public void onClick(View v) {
		String msg = null;
		switch (v.getId()) {
		case R.id.switch_btn:
			if (mBlueSwitchBtn.isChecked())
				msg = "$ON@";
			else
				msg = "$OFF@";
			break;
		case R.id.moisture_btn:
			msg = "$read_sensor@";// "$Read Sensor @";
			break;

		case R.id.connect_btn:
			startClientThread();
			break;
		}

		try {
			if (msg != null) {
				BlueUtils.LogW(TAG, "details :: " + msg);
				mBlueOutStream.write(msg.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startClientThread() {
		if (BlueConstants.sArduinoBlueDevice != null) {
			mToastMsg = null;
			mConnectBtn.setEnabled(false);
			disableButtons();

			mClientThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// FIXME Removing this code and using reflection to
						// avoid IOException : Service Discovery Failed
						// and Unable to start service discovery

						// mBlueSocket =
						// BlueConstants.sArduinoBlueDevice.createRfcommSocketToServiceRecord(UUID
						// .fromString(BlueConstants.SAMPLE_UUID));

						Method m = BlueConstants.sArduinoBlueDevice.getClass()
								.getMethod("createRfcommSocket",
										new Class[] { int.class });
						mBlueSocket = (BluetoothSocket) m.invoke(
								BlueConstants.sArduinoBlueDevice, 1);
						mBlueSocket.connect();
						mBlueOutStream = mBlueSocket.getOutputStream();
						BlueUtils.LogE(TAG,
								"Connected succesfuly to serial device "
										+ mBlueOutStream);

						mToastMsg = "Connected Successfully.";
						MyLog.logText("*********Connected Successfully");

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								BlueUtils.showToast(mToastMsg);
								enableButtons();
							}
						});

						mConnCheck = false;
						startServerThread();
					} catch (Exception e) {
						MyLog.logText("********Error while Connecting. Try to reconnect.");
						e.printStackTrace();
						mToastMsg = "Error while Connecting. Try to reconnect.";
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								BlueUtils.showToast(mToastMsg);
								mConnectBtn.setEnabled(true);
							}
						});

					}
				}
			});
			mClientThread.start();
		} else {
			BlueUtils.showToast(BlueConstants.NO_PAIRED_DEVICE_ERROR);
			finish();
		}
	}

	private void startServerThread() {
		mServerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// Continue checking for inputstream
				try {
					mBlueInputStream = mBlueSocket.getInputStream();
					mMainStringBuff = new StringBuffer();
					byte[] buffer = new byte[512];
					String line = null;
					while (!mConnCheck) {
						if (mBlueInputStream != null) {
							BlueUtils.LogV(TAG,
									"The blue stream ready to send details "
											+ mBlueInputStream.available());
							mBlueInputStream.read(buffer);
							line = new String(buffer, "UTF-8").replaceAll(
									"\u0000.*", "");
							BlueUtils.LogE(TAG, "The new string read from "
									+ line);

							MyLog.logText("%%%%% Line is : " + line + "\n");

							mMainStringBuff.append(line);

							if (mMainStringBuff.toString().contains("@")) {
								// Found a delimiter

								// posting to UI thread, the data to the
								// textview
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										BlueUtils
												.LogV(TAG,
														"Setting read data to TextViews");
										try {

											int aIndex = mMainStringBuff
													.indexOf("@");

											String mainString = mMainStringBuff
													.toString();

											String toSet = mainString
													.substring(0, aIndex);
											String remainStr = mainString
													.substring(aIndex + 1);

											mReceivedTxv.setText(toSet);
											mDataLoggerTxv.setText(toSet);
											
											MyLog.logText("%%%%% Buffer is : "
													+ toSet + "\n");

											MyLog.logText("!!!!!!!!!!Reaminaing string adding to reste buffer is "
													+ remainStr + "\n");

											// Reset the buffer
											mMainStringBuff.setLength(0);
											mMainStringBuff.append(remainStr);
										} catch (Exception e) {
											MyLog.logText("Error while setting text following this log");
										}
									}
								});

							}

							Arrays.fill(buffer, (byte) 0);
						} else {
							BlueUtils
							.LogV(TAG, "Blue Stream finished null. "
									+ mBlueInputStream);
						}
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
					BlueUtils.LogE(TAG, "Illegal data format");
					MyLog.printStack(e);
				} catch (IOException e) {
					e.printStackTrace();
					BlueUtils.LogE(TAG, "Exception while reading");
					MyLog.printStack(e);
				}

			}
		});
		mServerThread.start();
	}

	private void enableButtons() {
		mBlueSwitchBtn.setEnabled(true);
		mMoistureBtn.setEnabled(true);
	}

	private void disableButtons() {
		mBlueSwitchBtn.setEnabled(false);
		mMoistureBtn.setEnabled(false);
	}

	private void closeStreams() {
		try {
			mConnCheck = true;
			if (mBlueInputStream != null) {
				mBlueInputStream.close();
				mBlueInputStream = null;
			}
			if (mBlueOutStream != null) {
				mBlueOutStream.close();
				mBlueOutStream = null;
			}
			if (mBlueSocket != null) {
				mBlueSocket.close();
				mBlueSocket = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			BlueUtils.LogI(TAG, "onStop Error closing streams");
		}
	}

}
