package fr.sebgghb22.net.lg.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import fr.sebgghb22.net.CommandReceiverImpl;
import fr.sebgghb22.net.Packet;
import fr.sebgghb22.net.lg.packets.LgCommand;
import fr.sebgghb22.net.lg.packets.LgMonitorAddress;
import fr.sebgghb22.net.lg.packets.LgPacket;

public class LgCommandReceiver extends BroadcastReceiver implements CommandReceiverImpl {

	public static String COMMANDMSG = "BROADCAST.LG.COMMAND";

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.d("ATMO", "Receive Broadcast Intent");

		if (intent.getAction().equals(COMMANDMSG)) {

			String[] packetInfo = intent.getStringArrayExtra("PACKET");

			if (packetInfo.length == 2) {

				LgCommand c = LgCommand.valueOf(packetInfo[0]);
				String address = packetInfo[1];
				int id = intent.getIntExtra("ID", 0);
				LgPacket packet = new LgPacket(c, new LgMonitorAddress(address, id));
				new ServerTask().execute(packet);
			}
		}
	}

	private class ServerTask extends AsyncTask<Packet, Void, Void> {

		@Override
		protected Void doInBackground(Packet... params) {

			for (Packet p : params) {
				sendCommand(p);
			}
			return null;
		}

	}

	@Override
	public void sendCommand(Packet packetData) {
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(8080);

			byte[] buffer = new byte[packetData.getPacketData().length];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try {
				packet.setData(packetData.getPacketData());
				packet.setLength(packetData.getPacketData().length);
				packet.setPort(9761);
				packet.setAddress(packetData.getDestination().getInetAddress());
				ds.send(packet);
				ds.setSoTimeout(2 * 1000);
				ds.receive(packet);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SocketException e1) {
			e1.printStackTrace();
		} finally {
			ds.close();
			Log.d("ATMO", "Sent");
		}
	}

}
