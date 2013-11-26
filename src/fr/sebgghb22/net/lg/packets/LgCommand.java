package fr.sebgghb22.net.lg.packets;

public enum LgCommand  {

	POWERON("k", "a", "01"), POWEROFF("k", "a", "00");

	private String command1, command2;
	private String data;

	private LgCommand(String command1, String command2, String data) {
		this.command1 = command1;
		this.command2 = command2;
		this.data = data;
	}

	public String getCommand1() {
		return command1;
	}

	public String getCommand2() {
		return command2;
	}

	public String getData() {
		return data;
	}
}
