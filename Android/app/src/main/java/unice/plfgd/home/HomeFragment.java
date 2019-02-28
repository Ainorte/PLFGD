package unice.plfgd.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import unice.plfgd.R;
import unice.plfgd.draw.DrawActivity;
import unice.plfgd.tools.Connexion;

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
	}

	@Override
	public void setPresenter(@NonNull HomeContract.Presenter presenter) {
		this.mPresenter = presenter;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_fragment, container, false);
		mConnectButton = view.findViewById(R.id.connect_button);
		mConnectButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				initSocket();
			}
		});
		return view;
	}

	@Override
	public void initSocket() {
		mConnectButton.setEnabled(false);
		mConnectButton.setText(R.string.connecting);
		mPresenter.initSocket();
	}

	@Override
	public void onSocketActive() {
		Intent intent = new Intent(getContext(), DrawActivity.class);
		startActivity(intent);
	}
}
