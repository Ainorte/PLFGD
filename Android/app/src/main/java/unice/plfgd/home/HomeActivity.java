package unice.plfgd.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import unice.plfgd.R;
import unice.plfgd.tool.ActivityTools;

public class HomeActivity extends AppCompatActivity {

	private HomePresenter mPresenter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		//Set up the fragment
		HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
		if(homeFragment == null){
			homeFragment = HomeFragment.newInstance();
			ActivityTools.addFragmentToActivity(getSupportFragmentManager(), homeFragment,R.id.contentFrame);
		}

		//Set up presenter
		mPresenter = new HomePresenter(homeFragment);
	}
}
