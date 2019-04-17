package unice.plfgd.draw;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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

public class SCTResultFragment extends Fragment implements SCTResultContract.View {

	private Button mReturn;
	private TextView mResponse;
	private DrawCanvas mServerCanvas;
	private DrawCanvas mClientCanvas;
	private Button mReplay;
	private SCTResultContract.Presenter mPresenter;

	public SCTResultFragment() {

	}

	public static SCTResultFragment newInstance() {
		return new SCTResultFragment();
	}

	@Override
	public void setPresenter(SCTResultContract.Presenter presenter) {
		mPresenter = presenter;
	}

	@Override
	public SCTResultContract.Presenter getPresenter() {
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
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.result);
		if (getArguments() != null && mPresenter != null) {
			if (getArguments().getSerializable("result") != null) {
				mPresenter.setServerResult((Packet) getArguments().getSerializable("result"));
			}
			if(getArguments().getSerializable("game") != null){
			    mPresenter.setGame((Game) getArguments().getSerializable("game"));
            }
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.sct_res_fragment, container, false);

		mReturn = view.findViewById(R.id.ctr_retour);
		mReturn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPresenter.back();
			}
		});
		mReplay = view.findViewById(R.id.ctr_rejouer);
		mReplay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPresenter.replay();
			}
		});

		mResponse = view.findViewById(R.id.ctr_res);

		mPresenter.setCommentary();

		mServerCanvas = view.findViewById(R.id.Canvas_serveur);
		mServerCanvas.setActive(false);
		mServerCanvas.setOnSizeChange(mPresenter.onServerDrawSizeChange());

		mClientCanvas = view.findViewById(R.id.Canvas_client);
		mClientCanvas.setActive(false);
		mClientCanvas.setOnSizeChange(mPresenter.onClientDrawSizeChange());

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
	public DrawCanvas getServerCanvas() {
		return mServerCanvas;
	}

	@Override
	public DrawCanvas getClientCanvas() {
		return mClientCanvas;
	}

	@Override
	public void setCommentary(Game game, Boolean win) {
		switch (game) {
			case SCT:
                if(win == null) {
                    mResponse.setText(R.string.equality);
                }
				else if (win) {
					mResponse.setText(R.string.you_win);
					mResponse.setTextColor(getResources().getColor(R.color.green));
				} else {
					mResponse.setText(R.string.you_loose);
					mResponse.setTextColor(getResources().getColor(R.color.red));
				}
		}
	}
}
