package unice.plfgd.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import unice.plfgd.R;
import unice.plfgd.draw.DrawActivity;
import unice.plfgd.tool.Connexion;

public class HomeFragment extends Fragment implements HomeContract.View {

	private HomeContract.Presenter mPresenter;

	private Button mConnectButton;

	public HomeFragment() {
		//Required
	}

	public static HomeFragment newInstance(){
		return new HomeFragment();
	}

	@Override
	public void onResume(){
		super.onResume();
		mPresenter.start();
		mConnectButton.setText(R.string.connect);
		mConnectButton.setEnabled(true);
	}

	@Override
	public void setPresenter(@NonNull HomeContract.Presenter presenter) {
		this.mPresenter = presenter;
	}

	@Override
	public void onSocketReset(Connexion.ResetSocketMessage message) {

		switch (message){
			case TIMEOUT:
				Snackbar.make(getView(),R.string.host_unreachable,Snackbar.LENGTH_LONG).show();
				break;
			case CONNEXION_LOST:
				Snackbar.make(getView(),R.string.connexionLost,Snackbar.LENGTH_LONG).show();
				break;
		}

		getView().post(new Runnable() {
			@Override
			public void run() {
				mConnectButton.setText(R.string.connect);
				mConnectButton.setEnabled(true);
			}
		});
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_fragment, container, false);
		mConnectButton = view.findViewById(R.id.connect_button);
		mConnectButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPresenter.initSocket();
			}
		});
		return view;
	}

	@Override
	public void initSocket() {
		mConnectButton.setEnabled(false);
		mConnectButton.setText(R.string.connecting);
	}

	@Override
	public void onSocketActive() {
		Intent intent = new Intent(getContext(), DrawActivity.class);
		startActivity(intent);
	}
}