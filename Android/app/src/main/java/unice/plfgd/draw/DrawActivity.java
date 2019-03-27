package unice.plfgd.draw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import unice.plfgd.R;
import unice.plfgd.common.data.FormeRequest;
import unice.plfgd.tool.ActivityTools;

import java.io.Serializable;

public class DrawActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		//Set up the fragment
		Object fragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
		if (fragment == null) {
			DrawFragment drawFragment = DrawFragment.newInstance();
			drawFragment.setArguments(getIntent().getExtras());
			ActivityTools.addFragmentToActivity(getSupportFragmentManager(), drawFragment, R.id.contentFrame);

			//Set up presenter
			DrawPresenter drawPresenter = new DrawPresenter(drawFragment);
		}
	}
}
