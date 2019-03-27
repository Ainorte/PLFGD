package unice.plfgd.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import unice.plfgd.BuildConfig;
import unice.plfgd.R;
import unice.plfgd.tool.ActivityTools;
import unice.plfgd.tool.Configuration;
import unice.plfgd.tool.service.APIService;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		//Set up the fragment
		MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
		if (menuFragment == null) {
			menuFragment = MenuFragment.newInstance();
			ActivityTools.addFragmentToActivity(getSupportFragmentManager(), menuFragment, R.id.contentFrame);
		}

		//Set up presenter
		new MenuPresenter(menuFragment);
	}
}
