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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DE2_Link extends Activity {
	String addr;
	private SocketHandler Sock;
	String ipAddress;
	Integer port;
	Button download;
	ProgressBar progress_bar;
	TextView text;
	Boolean async_running = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.get_map);
		ipAddress  = "192.168.1.140";
		port = 50002;
		Sock = new SocketHandler(ipAddress,port);
		text = (TextView) findViewById(R.id.Transfer_Progress);
		progress_bar = (ProgressBar) findViewById(R.id.Map_DL_Progress_Bar);
		download = (Button) findViewById(R.id.download_maps_button);
		
		download.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				progress_bar.setProgress(0);
				startSocket(v);				
			}
		});
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.detectDiskReads().detectDiskWrites().detectNetwork()
		.penaltyLog().build());
		
		/*
		 * TCPReadTimerTask tcp_task = new TCPReadTimerTask(); Timer tcp_timer =
		 * new Timer(); tcp_timer.schedule(tcp_task, 3000, 500);
		 */
		
	}
	public void startSocket(View view) {		
		
		if (Sock.socket != null && Sock.socket.isConnected() && !Sock.socket.isClosed()) {
			text.setText("Socket already open");
			return;
		}
		if(!async_running){
			Sock.createSocket();
			Sock.writeSocket();
			Sock.readFromSocket();
			Sock.execute((Void) null);
		}
			
	}
	
	public class SocketHandler extends AsyncTask<Void, Void, Socket> {
		private Socket socket;
		private OutputStream out;
		private InputStream in;
		byte[] message;
		String ipAddress;
		Integer port;
		public SocketHandler(String ipAddress,int port){
			this.ipAddress = ipAddress;
			this.port = port;
			
			this.message = new byte[2];
			this.message[0] = 0x01;
			this.message[1] = 0x31;
			
		}
		protected void onPreExecute(){
			async_running=true;
			text.setText("beginning socket connection");
			progress_bar.setProgress(10);
		}
		public void createSocket(){
			try {
				this.socket = new Socket(ipAddress,port);
				out=socket.getOutputStream();
				in=socket.getInputStream();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		public void writeSocket(){
			text.setText("socket created");
			progress_bar.setProgress(25);
			text.setText("sending start signal to de2");
			try {
				out.write(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void readFromSocket(){
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
						
					}
					out.close();
					in.close();
					socket.close();
				
				} catch (IOException e) {
					e.printStackTrace();
			}
		}
		}
		protected Socket doInBackground(Void... voids) {
			
			try {

				progress_bar.setProgress(50);
				progress_bar.setProgress(90);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return this.socket;
		}
		protected void onPostExecute() {
			async_running=false;
			text.setText("closing socket connection");
			progress_bar.setProgress(100);
		}
	}
	public class TCPTask extends TimerTask {
			Socket socket;
			InputStream in;
			OutputStream out;
			public TCPTask(Socket s,InputStream i, OutputStream o){
				this.socket = s;
				this.in=i;
				this.out=o;
			}
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						text.setText("beginning to read file");
						progress_bar.setProgress(60);
					}
				});
				
		}
	}
}

