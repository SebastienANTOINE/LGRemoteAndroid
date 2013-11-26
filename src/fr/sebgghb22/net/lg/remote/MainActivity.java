package fr.sebgghb22.net.lg.remote;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import fr.sebgghb22.net.lg.packets.LgCommand;
import fr.sebgghb22.net.lg.services.LgCommandReceiver;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// startService(new Intent(getApplicationContext(), LgService.class));

		IntentFilter filter = new IntentFilter();
		filter.addAction(LgCommandReceiver.COMMANDMSG);
		filter.addCategory(Intent.CATEGORY_DEFAULT);

		registerReceiver(new LgCommandReceiver(), filter);

		final EditText address = (EditText) findViewById(R.id.address);
		final EditText idText = (EditText) findViewById(R.id.textId);

		Button power = (Button) findViewById(R.id.powerButton);
		power.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent commandIntent = new Intent(LgCommandReceiver.COMMANDMSG);
				commandIntent.putExtra("PACKET", new String[] { LgCommand.POWERON.name(), address.getText().toString() });
				commandIntent.putExtra("ID", Integer.valueOf(idText.getText().toString()));
				sendBroadcast(commandIntent);
			}
		});

		Button powerOff = (Button) findViewById(R.id.powerOffButton);
		powerOff.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent commandIntent = new Intent(LgCommandReceiver.COMMANDMSG);
				commandIntent.putExtra("PACKET", new String[] { LgCommand.POWEROFF.name(), address.getText().toString() });
				commandIntent.putExtra("ID", Integer.valueOf(idText.getText().toString()));
				sendBroadcast(commandIntent);
			}
		});
	}
}
