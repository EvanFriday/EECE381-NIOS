package com.NioSync.pathfinder;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Wifi extends Activity {

	TextView mainText;
	WifiManager mainWifi;
	//WifiReceiver receiverWifi;
	List<ScanResult> wifiList;
	StringBuilder sb = new StringBuilder();

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifi_connection);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//activate QR code connection button
		Button QRbutton = (Button) findViewById(R.id.QRconnection);
		QRbutton.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						startActivity(getPackageManager().getLaunchIntentForPackage("com.qrdroid.QRDroid"));
					}
		        	
		});
		
		
		//activate manual connection button
		Button MNLbutton = (Button) findViewById(R.id.MNLconnection);
		MNLbutton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//startActivity(getPackageManager().getLaunchIntentForPackage("com.qrdroid.QRDroid"));
				startActivity(new Intent(Wifi.this, ManualConnection.class));
			}
        	
        });
/*
		mainText = (TextView) findViewById(R.id.help_view_1);
		mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

		if (mainWifi.isWifiEnabled() == false)
		{   
			Toast.makeText(getApplicationContext(), "WiFi is currently disabled.  Please wait while it is enabled.", 
					Toast.LENGTH_LONG).show();
			mainWifi.setWifiEnabled(true);
			Toast.makeText(getApplicationContext(), "WiFi is has been enabled.", 
					Toast.LENGTH_LONG).show();
		} 

		receiverWifi = new WifiReceiver();

		registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

		mainWifi.startScan();
		mainText.setText("Scanning for WiFi networks...");
*/
		
		
	}
	
	
	
/*
	public boolean onCreateOptionsMenu(Menu menu) {
		//menu.add(0, 0, 0, "Refresh");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		mainWifi.startScan();
		mainText.setText("Scanning for WiFi networks...");
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onPause() {
		//unregisterReceiver(receiverWifi);
		super.onPause();
	}
	@Override
	protected void onResume() {
		//registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		super.onResume();
	}

	class WifiReceiver extends BroadcastReceiver {

		public void onReceive(Context c, Intent intent) {
			sb = new StringBuilder();
			wifiList = mainWifi.getScanResults(); 
			sb.append("\n These are the " + wifiList.size() + "WiFi networks around you:" + "\n\n\n");

			for(int i = 0; i < wifiList.size(); i++){
				sb.append("Network ");
				sb.append(new Integer(i+1).toString() + ".");
				sb.append("\n");
				sb.append((wifiList.get(i)).toString());
				sb.append("\n\n");
			} 
			mainText.setText(sb);  
		}
	}

	private void connectWifi(final int position) {

		final int value = wifiList.size()-1 - position;
		String Capabilities =  wifiList.get(value).capabilities;

		if(Capabilities.contains("WPA")) {

		}

		else if(Capabilities.contains("WEP")){

		}

		else {

		}

	}
*/

}