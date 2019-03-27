package unice.plfgd;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import unice.plfgd.tool.Configuration;

public class PreferencesActivity extends AppCompatActivity {
	private EditText mServerField, mUsernameField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Configuration c = Configuration.getInstance();
				c.set("serverURL", mServerField.getText().toString());
				c.set("username", mUsernameField.getText().toString());

				Snackbar.make(view, "Préférences sauvegardées", Snackbar.LENGTH_LONG).show();
			}
		});

		mServerField = findViewById(R.id.server_field);
		mUsernameField = findViewById(R.id.name_field);

		final Configuration conf = Configuration.getInstance();
		mServerField.setText(conf.getOrDefault("serverURL", ""));
		mUsernameField.setText(conf.getOrDefault("username",
				BuildConfig.SERVER_DOMAIN + ":" + BuildConfig.SERVER_PORT));
	}

}
