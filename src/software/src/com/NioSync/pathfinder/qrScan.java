package com.NioSync.pathfinder;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Button;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.widget.TextView;
import android.graphics.ImageFormat;

import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import net.sourceforge.zbar.Config;

public class qrScan extends Activity
{
    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    
    TextView scanText;
    Button scanButton;
    Button wifiButton;
    Button locButton;
    String networkSSID;
    String networkType;
    String networkPass;
    String scannedLocation;
    ImageScanner scanner;
    private boolean wifiInfo = false;
    private boolean barcodeScanned = false;
    private boolean previewing = true;

    static {
        System.loadLibrary("iconv");
    } 

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.qr_layout);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        // Instance barcode scanner
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        scanText = (TextView)findViewById(R.id.scanText);

        scanButton = (Button)findViewById(R.id.ScanButton);
        wifiButton = (Button)findViewById(R.id.WifiButton);
        locButton = (Button)findViewById(R.id.LocationButton);
        locButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (barcodeScanned) {
					Intent i = new Intent(qrScan.this, PathFinder.class);
					i.putExtra("currentLocation", scannedLocation);
					startActivity(i);
				}
			}
        });
        scanButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (barcodeScanned) {
                        barcodeScanned = false;
                        scanText.setText("Scanning...Again");
                        mCamera.setPreviewCallback(previewCb);
                        mCamera.startPreview();
                        previewing = true;
                        mCamera.autoFocus(autoFocusCB);
                        scanButton.setText("Scan Another QR Code");
                    }
                }
            });
        wifiButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (wifiInfo) {
                   WifiConfiguration config = new WifiConfiguration();
                   config.SSID = "\"" + networkSSID + "\"";
                   if(networkType == "WPA"){
                   config.preSharedKey = "\""+networkPass+"\"";
                   }
                   else if(networkType == "WEP"){
                	   config.wepKeys[0] = "\"" + networkPass + "\""; 
                	   config.wepTxKeyIndex = 0;
                	   config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                	   config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40); 
                   }
                   else{
                	   config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                   }
                   WifiManager wifiManager = (WifiManager)qrScan.this.getSystemService(Context.WIFI_SERVICE); 
                   if(wifiManager.getWifiState()!=WifiManager.WIFI_STATE_ENABLED){
                	   wifiManager.setWifiEnabled(true);
                   }
                   wifiManager.addNetwork(config);
                   List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
                   for( WifiConfiguration i : list ) {
                       if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                            wifiManager.disconnect();
                            wifiManager.enableNetwork(i.networkId, true);
                            wifiManager.reconnect();               

                            break;
                       }           
                    }
                   
                }
            }
        });
        
    }

    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
        	if(Camera.getNumberOfCameras()!=0)
            c = Camera.open(0);
        } catch (Exception e){
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
            public void run() {
                if (previewing)
                    mCamera.autoFocus(autoFocusCB);
            }
        };

    PreviewCallback previewCb = new PreviewCallback() {
            public void onPreviewFrame(byte[] data, Camera camera) {
                Camera.Parameters parameters = camera.getParameters();
                Size size = parameters.getPreviewSize();

                Image barcode = new Image(size.width, size.height, "Y800");
                barcode.setData(data);

                int result = scanner.scanImage(barcode);
                
                if (result != 0) {
                    previewing = false;
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();
                  
                    SymbolSet syms = scanner.getResults();
                   
                    for(Symbol sym : syms){
                    	String ntwkInfo =sym.getData();
                    	if(ntwkInfo.contains("WIFI:")){
                    		String[] parts = ntwkInfo.split(":");
                    		for(int i = 0;i<parts.length;i++){
                    		System.out.println(parts[i]);
                    		}
                    	networkSSID = parts[1];
                    	networkType = parts[2];
                    	networkPass = parts[3];
                    	
                    	
                    	
                 		scanText.setText("Wifi network information: SSID= "+parts[1]+" Type= "+parts[2]+" Pass= "+parts[3]);
                 		barcodeScanned=true;
                 		wifiInfo=true;
                    	}
                    	else if (ntwkInfo.contains("NODE:")){
                    		String[] parts = ntwkInfo.split(":");
                    		scannedLocation = parts[1];
                    		barcodeScanned=true;
                    	}
                    	
                    }
                }
            }
        };

    // Mimic continuous auto-focusing
    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                autoFocusHandler.postDelayed(doAutoFocus, 1000);
            }
        };
}