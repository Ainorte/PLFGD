package unice.plfgd.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import unice.plfgd.R;
import unice.plfgd.tool.ActivityTools;

public class MenuActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
		getSupportActionBar().setTitle(R.string.menu);

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
