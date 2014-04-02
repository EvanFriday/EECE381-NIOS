package com.NioSync.pathfinder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import com.example.ece381.MyApplication;
import com.example.ece381.R;
import com.example.ece381.MainActivity.SocketConnect;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DE2_Link extends Activity {

	public void openSocket(View view) {
		DE2_Class app = (DE2_Class) getApplication();
		TextView msgbox = (TextView) findViewById(R.id.Transfer_Progress);

		// Make sure the socket is not already opened

		if (app.sock != null && app.sock.isConnected() && !app.sock.isClosed()) {
			msgbox.setText("Socket already open");
			return;
		}

		// open the socket. SocketConnect is a new subclass
		// (defined below). This creates an instance of the subclass
		// and executes the code in it.

		new SocketConnect().execute((Void) null);
	}

	public void OnReceive(View view) {

	}

	void getMapNodes() {

	}

	void getCurrentLocation() {

	}

}
