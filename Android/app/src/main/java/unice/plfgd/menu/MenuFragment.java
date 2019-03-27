package unice.plfgd.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import unice.plfgd.R;
import unice.plfgd.common.net.Packet;
import unice.plfgd.home.HomeActivity;
import unice.plfgd.tool.Game;
import unice.plfgd.tool.service.RemoteAPIImpl;

public class MenuFragment extends Fragment implements MenuContract.View {

	private MenuContract.Presenter mPresenter;

	private TextView mText;
	private Button mDrawForme;
	private Button mSCT;

	public MenuFragment() {
		//Required
	}

	static MenuFragment newInstance() {
		return new MenuFragment();
	}

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.start();
		resetInteraction();
	}



	@Override
	public void setPresenter(@NonNull MenuContract.Presenter presenter) {
		this.mPresenter = presenter;
	}

	@Override
	public MenuContract.Presenter getPresenter() {
		return mPresenter;
	}

	@Override
	public void onSocketReset(RemoteAPIImpl.ResetSocketMessage message) {
		Intent intent = new Intent(getContext(), HomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menu_fragment, container, false);

		mText = view.findViewById(R.id.accueil_text);
		mText.setText(String.format("%s %s %s", getResources().getString(R.string.welcome), mPresenter.getUserName(), getResources().getString(R.string.exclamationPoint)));

		mDrawForme = view.findViewById(R.id.but_des);
		mDrawForme.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPresenter.launchGame(Game.DRAWFORME);
			}
		});

		mSCT = view.findViewById(R.id.but_ctr);
		mSCT.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPresenter.launchGame(Game.SCT);
			}
		});
		return view;
	}

	@Override
	public void blockInteration() {
		mDrawForme.setEnabled(false);
		mSCT.setEnabled(false);
	}

	@Override
	public void resetInteraction() {
		mDrawForme.setEnabled(true);
		mSCT.setEnabled(true);
	}

	@Override
	public void setActivity(Class<? extends Activity> activity, Packet payload) {
		Intent intent = new Intent(getContext(), activity);
		intent.putExtra("payload", payload);
		startActivity(intent);
	}

}
