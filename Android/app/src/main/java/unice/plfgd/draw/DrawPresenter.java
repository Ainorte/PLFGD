package unice.plfgd.draw;

import android.os.Bundle;
import android.support.annotation.NonNull;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.Forme;
import unice.plfgd.tool.Connexion;

public class DrawPresenter implements DrawContract.Presenter {

	private DrawContract.View mView;
	private Bundle bundle;
	private Connexion connexion;

	public DrawPresenter(@NonNull DrawContract.View view) {
		this.mView = view;
		mView.setPresenter(this);

		this.connexion = Connexion.getInstance();
		if (connexion.isConnected()) {
			connexion.setPresenter(this);
		}
	}

	@Override
	public void start() {
		mView.showOrder(Forme.SQUARE);
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
	public void onValid() {
		// test pour recup le tableau des points et le mettre dans un TextView
		mView.onSending();

		DrawCanvas canvas = mView.getCanvas();

		if (connexion.isConnected()) {
			connexion.sendMessage("draw", canvas.getDraw().convertRefactor(100, 100));
		}
		else {
			resultSwitch(canvas.getDraw());
		}
	}

	@Override
	public void resultSwitch(Draw draw) {
		mView.resultSwitch(draw);
	}

	@Override
	public void setCanvas(Draw draw) {
		mView.setCanvas(draw);
	}

	@Override
	public Draw getDraw() {
		return mView.getDraw();
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}
}
