package unice.plfgd.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import unice.plfgd.BuildConfig;
import unice.plfgd.R;
import unice.plfgd.tool.ActivityTools;
import unice.plfgd.tool.Configuration;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
	private static final String NAME = "org.unice.plfgd.android";

	private HomePresenter mPresenter;

	private void loadMissingDefaults() {
		Configuration c = Configuration.getInstance();

		HashMap<String, String> defaults = new HashMap<String, String>() {{
			put("username", BuildConfig.DEFAULT_NAME);
			put("serverURL", BuildConfig.SERVER_DOMAIN + BuildConfig.SERVER_PORT);
		}};

		for (Map.Entry<String, String> val : defaults.entrySet()) {
			if (!c.has(val.getKey())) {
				c.set(val.getKey(), val.getValue());
			}
		}
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		if (!Configuration.isInit()) {
			Configuration.init(getSharedPreferences(NAME, MODE_PRIVATE));
		}
		loadMissingDefaults();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		//Set up the fragment
		HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
		if (homeFragment == null) {
			homeFragment = HomeFragment.newInstance();
			ActivityTools.addFragmentToActivity(getSupportFragmentManager(), homeFragment, R.id.contentFrame);
		}

		//Set up presenter
		mPresenter = new HomePresenter(homeFragment);
	}
}
