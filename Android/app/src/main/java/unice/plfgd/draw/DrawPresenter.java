package unice.plfgd.draw;

import android.support.annotation.NonNull;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.Forme;
import unice.plfgd.tool.service.RemoteAPIImpl;
import unice.plfgd.tool.service.APIService;

public class DrawPresenter implements DrawContract.Presenter {

	private DrawContract.View mView;

	public DrawPresenter(@NonNull DrawContract.View view) {
		this.mView = view;
		mView.setPresenter(this);
	}

	@Override
	public void start() {
		mView.showOrder(Forme.SQUARE);
		APIService.getInstance().setPresenter(this);
	}

	@Override
	public void onSocketReset(RemoteAPIImpl.ResetSocketMessage message) {
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

		APIService.getInstance().sendMessage("draw", canvas.getDraw().convertRefactor(100, 100));
	}

	@Override
	public void resultSwitch(Draw draw) {
		mView.resultSwitch(draw);
	}
}
