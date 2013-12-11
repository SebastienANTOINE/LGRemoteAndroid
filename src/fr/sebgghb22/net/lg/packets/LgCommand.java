package fr.sebgghb22.net.lg.packets;

public enum LgCommand {

	POWER("k", "a"),INPUT("k","b"),ASPECT("k","c"), SCREENMUTE("k","d"),VOLUMEMUTE("k","e"),VOLUME("k","f"),CONTRAST("k","g"),BRIGHTNESS("k","h"),COLOR("k","i"),TINT("k","j"),SHARPNESS("k","k"),
	OSD("k","l"),REMOTELOCK("k","m"),PIP("k","n"),PIPRATIO("k","o"),SPLITZOOM("k","p"),PIPPOSITION("k","q"),TREBLE("k","r"),BASS("k","s"),BALANCE("k","t"),COLORTEMP("k","u"),PIPINPUT("k","y"),
	ABNORMALSTATE("k","z"),ISM("j","p"),LOWPOWER("j","q"),AUTOCONF("j","u"),EQUALIZE("j","v"),KEY("m","c"),INPUTSELECT("x","b"),BLACKLIGHT("m","g"),TILINGMODE("d","d"),TILEHPOS("d","e"),
	TILEVPOS("d","f"),TILEHSIZE("d","g"),TILEVSIZE("d","h"),TILEID("d","i"),PICTUREMOD("d","x"),SOUNDMODE("d","y"),AUTOVOLUME("d","u"),SPEAKER("d","v"),AUTOSLEEP("f","g"),LANGUAGE("f","i"),
	IPSETTING("f","m"),LOGOLIGHT("f","p"),HPOSITION("f","q"),VPOSITION("f","r"),HSIZE("f","s"),VSIZE("f","t");
	
	private String command1, command2;
	private volatile String data="";

	private LgCommand(String command1, String command2) {
		this.command1 = command1;
		this.command2 = command2;
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

	public void setData(String data) {
		this.data = data;
	}
}
