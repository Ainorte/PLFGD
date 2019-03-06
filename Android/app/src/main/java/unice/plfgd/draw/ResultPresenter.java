package unice.plfgd.draw;

import android.support.annotation.NonNull;
import unice.plfgd.common.data.Draw;
import unice.plfgd.tool.Connexion;

public class ResultPresenter implements ResultContract.Presenter {

	private ResultContract.View mView;
	private Connexion connexion;
	private Draw result;

	public ResultPresenter(@NonNull ResultContract.View view) {
		this.mView = view;
		mView.setPresenter(this);

		this.connexion = Connexion.getInstance();
		connexion.setPresenter(this);
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

	@Override
	public Draw getDraw() {
		return result;
	}

	@Override
	public void setDraw(Draw draw) {
		this.result = draw;
	}
}
