package unice.plfgd.training;

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
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.Forme;
import unice.plfgd.draw.*;
import unice.plfgd.home.HomeActivity;
import unice.plfgd.tool.Connexion;

import java.util.Objects;

public class TrainingFragment extends Fragment implements TrainingContract.View {

	private TrainingContract.Presenter mPresenter;
	private TextView mOrder;
	private Button mReset;
	private DrawCanvas mCanvas;
	private Button mValid;
	public TrainingFragment() {
		//required
	}

	public static TrainingFragment newInstance() {
		return new TrainingFragment();
	}

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.start();
	}

	@Override
	public void setPresenter(TrainingContract.Presenter presenter) {
		this.mPresenter = presenter;
	}

	@Override
	public void onSocketReset(Connexion.ResetSocketMessage message) {
		Intent intent = new Intent(getContext(), HomeActivity.class);
		startActivity(intent);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.draw_fragment, container, false);

		mOrder = view.findViewById(R.id.draw_order);
		mOrder.setText(R.string.training);
		mReset = view.findViewById(R.id.draw_reset);
		mReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPresenter.onResetCanvas();
			}
		});
		mValid = view.findViewById(R.id.draw_valid);
		mValid.setText(R.string.back);
		mValid.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPresenter.onReturn();
			}
		});
		mCanvas = view.findViewById(R.id.draw_canvas);


		return view;
	}

	@Override
	public void resetCanvas() {
		mCanvas.clear();
	}

	@Override
	public void onReturn() {
		Objects.requireNonNull(getActivity()).onBackPressed();
	}
}
