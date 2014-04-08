package com.NioSync.pathfinder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DE2_Link extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// This call will result in better error messages if you
		// try to do things in the wrong thread.

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_map);

		EditText et = (EditText) findViewById(R.id.Transfer_Progress);
		et.setKeyListener(null);
		ProgressBar progress_bar = (ProgressBar) findViewById(R.id.Map_DL_Progress_Bar);

		// Set up a timer task. We will use the timer to check the
		// input queue every 500 ms

		TCPReadTimerTask tcp_task = new TCPReadTimerTask();
		Timer tcp_timer = new Timer();
		tcp_timer.schedule(tcp_task, 3000, 500);
	}

	// called when user click download

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

	// Called when the user wants to download a map, this sends a signal to DE2
	// to upload the map to middleman

	public void sendMessage(View view) {
		MyApplication app = (MyApplication) getApplication();

		String msg = "1";

		// Create an array of bytes. First byte will be the
		// message length, and the next ones will be the message

		byte buf[] = new byte[msg.length()];
		buf[0] = (byte) msg.length();
		System.arraycopy(msg.getBytes(), 0, buf, 1, msg.length());

		// Now send through the output stream of the socket

		OutputStream out;
		try {
			out = app.sock.getOutputStream();
			try {
				out.write(buf, 0, msg.length() + 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// called to connect to a socket, called when user clicked download

	public class SocketConnect extends AsyncTask<Void, Void, Socket> {

		// The main parcel of work for this thread. Opens a socket
		// to connect to the specified IP.

		protected Socket doInBackground(Void... voids) {
			Socket s = null;
			String ip = getConnectToIP();
			Integer port = getConnectToPort();

			try {
				s = new Socket(ip, port);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return s;
		}

		// After executing the doInBackground method, this is
		// automatically called, in the UI (main) thread to store
		// the socket in this app's persistent storage

		protected void onPostExecute(Socket s) {
			DE2_Class myApp = (DE2_Class) DE2_Link.this.getApplication();
			myApp.sock = s;
		}
	}

	// Called when the user closes a socket

	public void closeSocket(View view) {
		DE2_Class app = (DE2_Class) getApplication();
		Socket s = app.sock;
		try {
			s.getOutputStream().close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Construct an IP address

	public String getConnectToIP() {
		String addr = "192.168.1.1";
		return addr;
	}

	// Gets the Port

	public Integer getConnectToPort() {
		Integer port;
		port = 50002;
		return port;
	}

	// This is a timer Task. Be sure to work through the tutorials
	// on Timer Tasks before trying to understand this code.

	public class TCPReadTimerTask extends TimerTask {
		public void run() {
			DE2_Class app = (DE2_Class) getApplication();
			if (app.sock != null && app.sock.isConnected()
					&& !app.sock.isClosed()) {

				try {
					InputStream in = app.sock.getInputStream();

					// See if any bytes are available from the Middleman

					int bytes_avail = in.available();
					if (bytes_avail > 0) {

						// If so, read them in and create a sring

						byte buf[] = new byte[bytes_avail];
						in.read(buf);

						final String s = new String(buf, 0, bytes_avail,
								"US-ASCII");

						// As explained in the tutorials, the GUI can not be
						// updated in an asyncrhonous task. So, update the GUI
						// using the UI thread.

						String filename = "nodes.xml";
						FileOutputStream outputStream;

						outputStream = openFileOutput(filename,
								Context.MODE_PRIVATE);
						outputStream.write(s.getBytes());
						outputStream.close();

						runOnUiThread(new Runnable() {
							public void run() {
								EditText et = (EditText) findViewById(R.id.Transfer_Progress);
								et.setText(s);
							}
						});
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	void getCurrentLocation() {

	}

}
