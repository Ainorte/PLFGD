package unice.plfgd.draw;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import unice.plfgd.R;
import unice.plfgd.common.data.Game;
import unice.plfgd.common.net.Packet;
import unice.plfgd.home.HomeActivity;
import unice.plfgd.tool.service.RemoteAPIImpl;

import java.util.Objects;

public class ResultFragment extends Fragment implements ResultContract.View {

	private Button mReturn;
	private TextView mResponse;
	private TextView mComment;
	private DrawCanvas mCanvas;
	private Button mReplay;
	private ResultContract.Presenter mPresenter;

	public ResultFragment() {

	}

	public static ResultFragment newInstance() {
		return new ResultFragment();
	}

	@Override
	public void setPresenter(ResultContract.Presenter presenter) {
		mPresenter = presenter;
	}

	@Override
	public ResultContract.Presenter getPresenter() {
		return mPresenter;
	}

	@Override
	public void onSocketReset(RemoteAPIImpl.ResetSocketMessage message) {
		Intent intent = new Intent(getContext(), HomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.start();
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		if (getArguments() != null && mPresenter != null) {
			if (getArguments().getSerializable("result") != null) {
				mPresenter.setResult((Packet) getArguments().getSerializable("result"));
			}
			if(getArguments().getSerializable("game") != null){
			    mPresenter.setGame((Game) getArguments().getSerializable("game"));
            }
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.result_fragment, container, false);

		mReturn = view.findViewById(R.id.result_back);
		mReturn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPresenter.back();
			}
		});
		mReplay = view.findViewById(R.id.result_replay);
		mReplay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPresenter.replay();
			}
		});

		mResponse = view.findViewById(R.id.result_text);
		mResponse.setText(R.string.good_job);
		mComment = view.findViewById(R.id.result_comment);
		mComment.setText(R.string.good_comment);

		mCanvas = view.findViewById(R.id.result_canvas);
		mCanvas.setActive(false);
		mCanvas.setOnSizeChange(mPresenter.onDrawSizeChange());

		return view;
	}


	@Override
	public void back() {
		Objects.requireNonNull(getActivity()).onBackPressed();
	}

	@Override
	public void changeFragment(Packet payload){
		FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();

		DrawFragment fragment = DrawFragment.newInstance();
		DrawPresenter presenter = new DrawPresenter(fragment);

        Bundle bundle = new Bundle();
        bundle.putSerializable("payload", payload);

        fragment.setArguments(bundle);

		transaction.replace(R.id.contentFrame, fragment);
		transaction.commit();
	}

	@Override
	public DrawCanvas getCanvas() {
		return mCanvas;
	}
}
