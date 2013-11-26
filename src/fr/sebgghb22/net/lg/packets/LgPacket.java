package fr.sebgghb22.net.lg.packets;

import java.nio.charset.Charset;
import java.util.Locale;

import android.annotation.SuppressLint;
import fr.sebgghb22.net.Hex;
import fr.sebgghb22.net.MonitorAddress;
import fr.sebgghb22.net.Packet;

public class LgPacket implements Packet {

	private LgCommand command;
	private MonitorAddress address;
	private final static String SPACE = " ";
	private final static String CR = "\r";

	public LgPacket(LgCommand command, MonitorAddress address) {
		this.command = command;
		this.address = address;
	}

	@Override
	public byte[] getPacketData() {

		String data = command.getCommand1() + command.getCommand2() + SPACE + address.getStringId() + SPACE + command.getData() + CR;
		return data.getBytes(Charset.forName("ASCII"));
	}

	@Override
	public MonitorAddress getDestination() {
		return address;
	}

	@SuppressLint("DefaultLocale")
	@Override
	public String toString() {
		return "Value : " + Hex.encodeHexString(getPacketData()).toUpperCase(Locale.getDefault()) + "\r\n" + "Good Ack value : " + Hex.encodeHexString(getAckValueNeeded());
	}

	public byte[] getAckValueNeeded() {
		String ack = command.getCommand2() + SPACE + address.getStringId() + SPACE + "OK" + command.getData() + "x";
		return ack.getBytes(Charset.forName("ASCII"));
	}

	public boolean isACKOk(byte[] ack) {
		String goodAck = command.getCommand2() + SPACE + address.getStringId() + SPACE + "OK" + command.getData() + "x";
		String receivedAck = Hex.encodeHexString(ack);
		return goodAck.equals(receivedAck);
	}

}
