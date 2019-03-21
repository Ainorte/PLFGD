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
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.Forme;
import unice.plfgd.home.HomeActivity;
import unice.plfgd.tool.service.RemoteAPIImpl;

import java.util.Objects;

public class DrawFragment extends Fragment implements DrawContract.View {

	private DrawContract.Presenter mPresenter;
	private TextView mOrder;
	private Button mReset;
	private DrawCanvas mCanvas;
	private Button mValid;

	public DrawFragment() {
		//required
	}

	public static DrawFragment newInstance() {
		return new DrawFragment();
	}

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.start();
	}

	@Override
	public void setPresenter(DrawContract.Presenter presenter) {
		this.mPresenter = presenter;
	}

	@Override
	public void onSocketReset(RemoteAPIImpl.ResetSocketMessage message) {
		Intent intent = new Intent(getContext(), HomeActivity.class);
		startActivity(intent);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.draw_fragment, container, false);

		mOrder = view.findViewById(R.id.draw_order);
		mReset = view.findViewById(R.id.draw_reset);
		mReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPresenter.onResetCanvas();
			}
		});
		mValid = view.findViewById(R.id.draw_valid);
		mValid.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPresenter.onValid();
			}
		});
		mCanvas = view.findViewById(R.id.draw_canvas);


		return view;
	}

	@Override
	public void showOrder(final Forme forme) {
		Objects.requireNonNull(getView()).post(new Runnable() {
			@Override
			public void run() {
				CharSequence f = "";

				switch (forme) {
					case SQUARE:
						f = getText(R.string.square);
						break;
					case CIRCLE:
						f = getText(R.string.round);
						break;
					case TRIANGLE:
						f = getText(R.string.triangle);
						break;
					case POINT:
						f = getText(R.string.point);
						break;
					case SEGMENT:
						f = getText(R.string.segment);
						break;
				}

				mOrder.setText(String.format("%s %s", getText(R.string.draw), f));
			}
		});
	}

	@Override
	public void resetCanvas() {
		mCanvas.clear();
	}

	@Override
	public DrawCanvas getCanvas() {
		return mCanvas;
	}

	public void onSending() {
		Objects.requireNonNull(getView()).post(new Runnable() {
			@Override
			public void run() {
				mCanvas.setActive(false);
				mValid.setEnabled(false);
				mReset.setEnabled(false);
				mValid.setText(R.string.wait);
			}
		});
	}

	@Override
	public void resultSwitch(Draw draw) {
		FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();

		ResultFragment fragment = ResultFragment.newInstance();
		ResultPresenter presenter = new ResultPresenter(fragment);

		((DrawActivity) getActivity()).setPresenter(presenter);

		Bundle bundle = new Bundle();
		bundle.putSerializable("draw", draw);

		fragment.setArguments(bundle);

		transaction.replace(R.id.contentFrame, fragment);
		transaction.commit();
	}
}
