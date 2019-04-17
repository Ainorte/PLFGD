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
import unice.plfgd.common.data.packet.DevinerFormeResult;
import unice.plfgd.common.data.packet.FormeRequest;
import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.net.Packet;
import unice.plfgd.home.HomeActivity;
import unice.plfgd.tool.service.APIService;
import unice.plfgd.tool.service.RemoteAPIImpl;

import java.io.Serializable;
import java.util.Objects;

public class DrawFragment extends Fragment implements DrawContract.View {

	private DrawContract.Presenter mPresenter;
	private TextView mOrder;
	private Button mReset;
	private DrawCanvas draw;
	private Button mValid;

	public DrawFragment() {
		//required
		setRetainInstance(true);
	}

	public static DrawFragment newInstance() {
		return new DrawFragment();
	}

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.start();
		if (APIService.getInstance().getActualGame() == Game.DEVINER) {
			mPresenter.startTimer();
		}
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		AppCompatActivity activity = (AppCompatActivity) getActivity();
		switch (APIService.getInstance().getActualGame()) {
			case DRAWFORME:
				if (getArguments() != null && mPresenter != null) {
					Serializable serializable = getArguments().getSerializable("payload");
					if (serializable != null) {
						FormeRequest forme = (FormeRequest) serializable;
						mPresenter.setOrder(forme.getForme());
					}
				}
				activity.getSupportActionBar().setTitle(R.string.drawforme);
				break;
			case DEVINER:
				Serializable serializable = getArguments().getSerializable("payload");
				if (serializable != null) {
					DevinerFormeResult devine = (DevinerFormeResult) serializable;
					mPresenter.setDevine(devine);
				}
				activity.getSupportActionBar().setTitle(R.string.deviner_la_forme);
				break;
			case SCT:
				activity.getSupportActionBar().setTitle(R.string.sct);
				break;
			default:
				activity.getSupportActionBar().setTitle(R.string.app_name);
		}
	}

	@Override
	public void setPresenter(DrawContract.Presenter presenter) {
		this.mPresenter = presenter;
	}

	@Override
	public DrawContract.Presenter getPresenter() {
		return mPresenter;
	}

	@Override
	public void setScore(int score) {
		((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(String.format(getText(R.string.score).toString(), score));
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
		View view = inflater.inflate(R.layout.draw_fragment, container, false);

		mOrder = view.findViewById(R.id.draw_order);
		mReset = view.findViewById(R.id.draw_reset);
		mValid = view.findViewById(R.id.draw_valid);
		draw = view.findViewById(R.id.draw_canvas);
		draw.setOnSizeChange(mPresenter.onDrawSizeChange());

		mReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPresenter.onResetCanvas();
			}
		});
		draw.setOnTouchListener(mPresenter.onCanvasTouch());
		mValid.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPresenter.onValid();
			}
		});

		return view;
	}

	@Override
	public void showOrder(final Forme forme) {
		if (forme != null) {
			Objects.requireNonNull(getView()).post(new Runnable() {
				@Override
				public void run() {
					mOrder.setText(String.format("%s %s", getText(R.string.draw), forme.toString()));
				}
			});
		}
	}

	@Override
	public void showText(final String s){
		Objects.requireNonNull(getView()).post(new Runnable() {
			@Override
			public void run() {
				mOrder.setText(s);
			}
		});
	}

	public void hideButtons() {
		mReset.setVisibility(View.INVISIBLE);
		mValid.setVisibility(View.INVISIBLE);
		draw.setActive(false);
	}

	@Override
	public void showButtons() {
		mReset.setVisibility(View.VISIBLE);
		mValid.setVisibility(View.VISIBLE);
		draw.clear();
		draw.setActive(true);
	}

	@Override
	public void unlockButtons() {
		Objects.requireNonNull(getView()).post(new Runnable() {
			@Override
			public void run() {
				draw.setActive(true);
				mValid.setEnabled(true);
				mReset.setEnabled(true);
				mValid.setText(R.string.valid);
			}
		});
	}

	@Override
	public void showOrder(final Game game) {
		Objects.requireNonNull(getView()).post(new Runnable() {
			@Override
			public void run() {
				switch (game) {
					case SCT:
						mOrder.setText(R.string.sct);
						break;
					case DEVINER:
						mOrder.setText(mPresenter.getTextForDevine());
				}
			}
		});
	}

	@Override
	public void resetCanvas() {
		draw.clear();
	}

	@Override
	public DrawCanvas getCanvas() {
		return draw;
	}

	public void onSending() {
		Objects.requireNonNull(getView()).post(new Runnable() {
			@Override
			public void run() {
				draw.setActive(false);
				mValid.setEnabled(false);
				mReset.setEnabled(false);
				mValid.setText(R.string.wait);
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		mPresenter.stopTimer();
	}

	@Override
	public void resultSwitch(Packet result, Game game) {
		FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();

		ResultFragment fragment = ResultFragment.newInstance();
		ResultPresenter presenter = new ResultPresenter(fragment);

		Bundle bundle = new Bundle();
		bundle.putSerializable("result", result);
		bundle.putSerializable("game", game);

		fragment.setArguments(bundle);

		transaction.replace(R.id.contentFrame, fragment);
		transaction.commit();
	}

	@Override
	public void resultSCTSwitch(Packet result, Game game) {
		FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();

		SCTResultFragment fragment = SCTResultFragment.newInstance();
		SCTResultPresenter presenter = new SCTResultPresenter(fragment);

		Bundle bundle = new Bundle();
		bundle.putSerializable("result", result);
		bundle.putSerializable("game", game);

		fragment.setArguments(bundle);

		transaction.replace(R.id.contentFrame, fragment);
		transaction.commit();
	}
}
