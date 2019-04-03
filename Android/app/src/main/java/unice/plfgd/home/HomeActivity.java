package unice.plfgd.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import unice.plfgd.BuildConfig;
import unice.plfgd.R;
import unice.plfgd.tool.ActivityTools;
import unice.plfgd.tool.Configuration;

public class HomeActivity extends AppCompatActivity {

	private static final String NAME = "org.unice.plfgd.android";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		if (!Configuration.isInit()) {
			Configuration.init(getSharedPreferences(NAME, MODE_PRIVATE));
		}

		Configuration conf = Configuration.getInstance();
		if (!conf.has("serverURL")) { // default
			conf.set("serverURL", BuildConfig.SERVER_DOMAIN + ":" + BuildConfig.SERVER_PORT);
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		if (!conf.has("username")) {
			//Set up the fragment
			UsernameFragment usernameFragment = (UsernameFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

			if (usernameFragment == null) {
				usernameFragment = UsernameFragment.newInstance();
				ActivityTools.addFragmentToActivity(getSupportFragmentManager(), usernameFragment, R.id.contentFrame);
			}

			//Set up presenter
			new UsernamePresenter(usernameFragment);
		} else {
			//Set up the fragment
			HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

			if (homeFragment == null) {
				homeFragment = HomeFragment.newInstance();
				ActivityTools.addFragmentToActivity(getSupportFragmentManager(), homeFragment, R.id.contentFrame);
			}

			//Set up presenter
			new HomePresenter(homeFragment);
		}
	}
}
