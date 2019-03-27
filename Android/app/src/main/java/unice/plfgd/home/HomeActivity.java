package unice.plfgd.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import unice.plfgd.BuildConfig;
import unice.plfgd.R;
import unice.plfgd.tool.ActivityTools;
import unice.plfgd.tool.Configuration;
import unice.plfgd.tool.service.APIService;

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
		// TODO KICK THIS SHIT OUT WHEN WELCOME'S IMPLEMENTED
		conf.set("username", "Meow");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

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
