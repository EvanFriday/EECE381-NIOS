package com.NioSync.pathfinder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DE2_Link extends Activity {
	String addr;
	private SocketConnect SC;
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	String ipAddress;
	Integer port;
	Button download;
	ProgressBar progress_bar;
	TextView text;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		ipAddress  = "192.168.1.243";
		port = 50002;
		SC = new SocketConnect();
		text = (TextView) findViewById(R.id.Transfer_Progress);
		progress_bar = (ProgressBar) findViewById(R.id.Map_DL_Progress_Bar);
		download = (Button) findViewById(R.id.download_maps_button);
		
		download.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				progress_bar.setProgress(10);
				text.setText("Attempting to open Socket");
				openSocket(v);
				
				progress_bar.setProgress(35);
				text.setText("Sending Opening Message");
				sendMessage(v);
				
				progress_bar.setProgress(50);
				//TODO: Call our message receiver
				
				progress_bar.setProgress(75);
				text.setText("Closing Socket");
				closeSocket(v);
				progress_bar.setProgress(100);
				
			}
		});
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.detectDiskReads().detectDiskWrites().detectNetwork()
		.penaltyLog().build());
		
		/*
		 * TCPReadTimerTask tcp_task = new TCPReadTimerTask(); Timer tcp_timer =
		 * new Timer(); tcp_timer.schedule(tcp_task, 3000, 500);
		 */
		setContentView(R.layout.get_map);
	}
	public void openSocket(View view) {		
		
		if (socket != null && socket.isConnected() && !socket.isClosed()) {
			text.setText("Socket already open");
			return;
		}
		try {
			text.setText("Opening Socket");
			progress_bar.setProgress(25);
			SC.execute((Void) null);
			out = socket.getOutputStream();
			in = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void closeSocket(View view) {
		try {
			this.out.close();
			this.in.close();
			this.socket.close();
		} catch (IOException e) {
			text.setText("failed to close socket");
			e.printStackTrace();
		}
	}
	public void sendMessage(View view) {
			byte msg = 0x01;
			try {
				this.out.write(msg);
			} catch (IOException e) {
				text.setText("message failed to send to De2");
				e.printStackTrace();
			}
	}
	public class SocketConnect extends AsyncTask<Void, Void, Socket> {
		private Socket s;
		public SocketConnect(){
		}
		protected Socket doInBackground(Void... voids) {
			try {
				s = new Socket(ipAddress,port);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return s;
		}
		//Calls on close
		protected void onPostExecute(Socket s) {
			socket = s;
		}
	}
	public class TCPReadTimerTask extends TimerTask {
		public void run() {
			text.setText("reading in file");
			if (socket != null && socket.isConnected()
					&& !socket.isClosed()) {
				try {

					// See if any bytes are available from the Middleman
					int bytes_avail = in.available();
					if (bytes_avail > 0) {
						// If so, read them in and create a sring
						byte buf[] = new byte[bytes_avail];
						in.read(buf);
						final String s = new String(buf, 0, bytes_avail,"US-ASCII");
						// As explained in the tutorials, the GUI can not be
						// updated in an asyncrhonous task. So, update the GUI
						// using the UI thread.

						String filename = "nodes.xml";
						FileOutputStream outputStream;
						outputStream = openFileOutput(filename,Context.MODE_PRIVATE);
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
	/*
	 * GETTERS AND SETTERS
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	
}

