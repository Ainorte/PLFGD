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
import android.widget.EditText;
import unice.plfgd.R;
import unice.plfgd.draw.DrawActivity;
import unice.plfgd.tool.Configuration;
import unice.plfgd.tool.service.APIService;
import unice.plfgd.tool.service.LocalAPIImpl;
import unice.plfgd.tool.service.RemoteAPIImpl;

import java.util.Objects;

public class HomeFragment extends Fragment implements HomeContract.View {

	private HomeContract.Presenter mPresenter;

	private Button mConnectButton;

	private Button mEntButton;

	private EditText mServerField, mUsernameField;

	public HomeFragment() {
		//Required
	}

	static HomeFragment newInstance() {
		return new HomeFragment();
	}

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.start();
		resetInteraction();
	}



	@Override
	public void setPresenter(@NonNull HomeContract.Presenter presenter) {
		this.mPresenter = presenter;
	}

	@Override
	public HomeContract.Presenter getPresenter() {
		return mPresenter;
	}

	@Override
	public void onSocketReset(RemoteAPIImpl.ResetSocketMessage message) {

		switch (message) {
			case TIMEOUT:
				Snackbar.make(Objects.requireNonNull(getView()),
						R.string.host_unreachable, Snackbar.LENGTH_LONG).show();
				break;
			case CONNEXION_LOST:
				Snackbar.make(Objects.requireNonNull(getView()),
						R.string.connexionLost, Snackbar.LENGTH_LONG).show();
				break;
		}

		getView().post(new Runnable() {
			@Override
			public void run() {
				resetInteraction();
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
				String serverDomain = mServerField.getText().toString();
				String username = mUsernameField.getText().toString();

				if (serverDomain.isEmpty() || username.isEmpty()) {
					Snackbar.make(Objects.requireNonNull(getView()),
							R.string.nonemptyInputs, Snackbar.LENGTH_LONG).show();

					return;
				}

				mPresenter.initSocket(serverDomain, username);
			}
		});

		mServerField = view.findViewById(R.id.server_field);
		mUsernameField = view.findViewById(R.id.name_field);

		final Configuration conf = Configuration.getInstance();
		mServerField.setText(conf.getOrNull("serverURL"));
		mUsernameField.setText(conf.getOrNull("username"));

		mEntButton = view.findViewById(R.id.ent_button);

		mEntButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				APIService.getInstance().setClient(new LocalAPIImpl());
				mPresenter.setDrawActivity();
			}
		});

		return view;
	}

	@Override
	public void blockInteration() {
		mConnectButton.setEnabled(false);
		mEntButton.setEnabled(false);

		mConnectButton.setText(R.string.connecting);
	}

	@Override
	public void resetInteraction() {
		mConnectButton.setText(R.string.connect);
		mUsernameField.setEnabled(true);
		mServerField.setEnabled(true);
		mConnectButton.setEnabled(true);
		mEntButton.setEnabled(true);
	}

	@Override
	public void setDrawActivity() {
		Intent intent = new Intent(getContext(), DrawActivity.class);
		startActivity(intent);
	}

}
