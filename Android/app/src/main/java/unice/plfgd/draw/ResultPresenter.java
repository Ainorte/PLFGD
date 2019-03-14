package unice.plfgd.draw;

import android.support.annotation.NonNull;
import unice.plfgd.common.data.Draw;
import unice.plfgd.tool.Connexion;
import unice.plfgd.tool.service.APIService;

public class ResultPresenter implements ResultContract.Presenter {

	private ResultContract.View mView;

	public ResultPresenter(@NonNull ResultContract.View view) {
		this.mView = view;
		mView.setPresenter(this);

		APIService.getInstance().setPresenter(this);
	}

	@Override
	public void start() {

	}

	@Override
	public void onSocketReset(Connexion.ResetSocketMessage message) {
		mView.onSocketReset(message);
	}

	@Override
	public void back() {
		mView.back();
	}

	@Override
	public void replay() {
		mView.replay();
	}
}
