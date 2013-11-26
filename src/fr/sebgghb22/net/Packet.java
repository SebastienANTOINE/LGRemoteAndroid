package fr.sebgghb22.net;

public interface Packet {
	public byte[] getPacketData();
	public MonitorAddress getDestination();
}
