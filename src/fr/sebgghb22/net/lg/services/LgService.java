package fr.sebgghb22.net.lg.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

@SuppressWarnings("deprecation")
public class LgService extends IntentService {

	private Runnable listener;
	private Executor executor;
	private String msg = "SEARCHLGMNT";

	public LgService() {
		super("network");
	}

	@Override
	public void onCreate() {
		super.onCreate();

		listener = new Runnable() {

			@Override
			public void run() {
				try {
					launchServerReceiver();
				} catch (SocketException e) {
					e.printStackTrace();
				}
			}
		};
	}

	protected void sendBroadcast() throws SocketException {
		Log.d("ATMO", "Sending broadcast discover message");
		DatagramSocket ds = new DatagramSocket();
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		try {
			packet.setData(msg.getBytes("ASCII"));
			packet.setLength(msg.getBytes("ASCII").length);
			packet.setPort(9760);
			packet.setAddress(getBroadcastAddress());
			ds.setBroadcast(true);
			ds.send(packet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void launchServerReceiver() throws SocketException {
		DatagramSocket ds = new DatagramSocket(9760);
		while (true) {
			byte[] buffer = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try {
				ds.receive(packet);
				Log.d("ATMO", "Receive data from " + packet.getAddress().getHostAddress());
				byte[] dataReceive = packet.getData();
				String dataMsg = new String(dataReceive, 0, packet.getLength(), Charset.forName("ASCII"));
				if (dataMsg.equals(msg)) {
					Log.d("ATMO", "Packet from server, nothing to do");
				} else {
					Log.d("ATMO", "Receive : " + dataMsg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		executor = Executors.newFixedThreadPool(3);
		executor.execute(listener);
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				try {
					sendBroadcast();
				} catch (SocketException e) {
					e.printStackTrace();
				}
			}
		}, 0, 5000);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	InetAddress getBroadcastAddress() throws IOException {
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		DhcpInfo dhcp = wifi.getDhcpInfo();
		int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
		byte[] quads = new byte[4];
		for (int k = 0; k < 4; k++)
			quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
		return InetAddress.getByAddress(quads);
	}
}
