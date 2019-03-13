package unice.plfgd.training;

import android.support.annotation.NonNull;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.Forme;
import unice.plfgd.draw.DrawCanvas;
import unice.plfgd.draw.DrawContract;
import unice.plfgd.tool.Connexion;

public class TrainingPresenter implements TrainingContract.Presenter {

	private TrainingContract.View mView;
	private Connexion connexion;

	public TrainingPresenter(@NonNull TrainingContract.View view) {
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
	public void onResetCanvas() {
		mView.resetCanvas();
	}

	@Override
	public void onReturn() {
		mView.onReturn();
	}
}
