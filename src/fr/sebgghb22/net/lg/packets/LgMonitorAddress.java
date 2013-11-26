package fr.sebgghb22.net.lg.packets;

import java.net.InetAddress;
import java.net.UnknownHostException;

import fr.sebgghb22.net.MonitorAddress;

public class LgMonitorAddress implements MonitorAddress {

	private final String inetAddress;
	private final int id;

	public LgMonitorAddress(String inetAddress, int id) {
		this.inetAddress = inetAddress;
		this.id = id;
	}

	@Override
	public InetAddress getInetAddress() {

		try {
			return InetAddress.getByName(inetAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getStringId() {
		if (id < 10) {
			return "0" + id;
		}
		return "" + id;
	}

}
