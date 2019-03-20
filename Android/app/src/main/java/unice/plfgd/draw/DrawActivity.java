package unice.plfgd.draw;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import unice.plfgd.R;
import unice.plfgd.base.BasePresenter;
import unice.plfgd.common.data.Draw;
import unice.plfgd.tool.ActivityTools;

import java.io.Serializable;

public class DrawActivity extends AppCompatActivity {
	private static Draw canvasCache;

	private BasePresenter mPresenter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		//Set up the fragment
		DrawFragment drawFragment = (DrawFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
		if (drawFragment == null) {
			drawFragment = DrawFragment.newInstance();
			ActivityTools.addFragmentToActivity(getSupportFragmentManager(), drawFragment, R.id.contentFrame);
		}

		//Set up presenter
		mPresenter = new DrawPresenter(drawFragment);

		((DrawPresenter) mPresenter).setCanvas(canvasCache);
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		canvasCache = mPresenter.getDraw();
		super.onSaveInstanceState(outState);
	}

	public void setPresenter(BasePresenter presenter) {
		this.mPresenter = presenter;
	}
}
