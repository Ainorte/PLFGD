package unice.plfgd.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import unice.plfgd.R;
import unice.plfgd.tool.service.RemoteAPIImpl;

public class UsernameFragment extends Fragment implements UsernameContract.View {

	private UsernameContract.Presenter mPresenter;
	private TextView mText;

	public UsernameFragment() {
		//Required
	}

	static UsernameFragment newInstance() {
		return new UsernameFragment();
	}

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.start();
	}

	@Override
	public void onSocketReset(RemoteAPIImpl.ResetSocketMessage message) {
		//nothing
	}

	@Override
	public UsernameContract.Presenter getPresenter() {
		return mPresenter;
	}

	@Override
	public void setPresenter(UsernameContract.Presenter presenter) {
		mPresenter = presenter;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.username_fragment, container, false);

		mText = view.findViewById(R.id.username_field);

		view.findViewById(R.id.valid_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPresenter.onValid(mText.getText().toString());
			}
		});

		return view;
	}

	@Override
	public void onValid() {
		Intent intent = new Intent(getContext(), HomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
