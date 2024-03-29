package unice.plfgd.draw;

import android.support.annotation.NonNull;
import unice.plfgd.common.data.Game;
import unice.plfgd.common.data.packet.DevinerFormeResult;
import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.common.data.packet.ResultDrawForme;
import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.service.APIService;
import unice.plfgd.tool.service.RemoteAPIImpl;

public class ResultPresenter implements ResultContract.Presenter {

	private ResultContract.View mView;
	private Draw result;
	private Game game;
	private boolean win;
	private Forme expected;

	public ResultPresenter(@NonNull ResultContract.View view) {
		this.mView = view;
		mView.setPresenter(this);
	}

	@Override
	public void start() {
		APIService.getInstance().setPresenter(this);
		APIService.getInstance().sendMessage("scoreUpdate",null);
	}

	@Override
	public void onSocketReset(RemoteAPIImpl.ResetSocketMessage message) {
		mView.onSocketReset(message);
	}

	@Override
	public void setScore(int score) {
		mView.setScore(score);
	}

	@Override
	public void back() {
	    mView.back();
	}

	@Override
	public void replay() {
		APIService.getInstance().launchGame(game);
	}

    @Override
    public void changeFragment(Packet payload) {
        mView.changeFragment(payload);
    }

	@Override
	public void setCommentary() {
		switch (game) {
			case DRAWFORME:
				mView.setCommentary(game, win, expected);
				break;
			case DEVINER:
				mView.setCommentary(game, win, Forme.UNKNOWN);
		}
	}


	@Override
	public void setResult(Packet result) {
		if (result instanceof ResultDrawForme) {
			ResultDrawForme recogForme = (ResultDrawForme) result;
			this.result = recogForme.getDraw();
			this.win = recogForme.isValidate();
			this.expected = recogForme.getExpected();
		}
		if (result instanceof DevinerFormeResult) {
			DevinerFormeResult devine = (DevinerFormeResult) result;
			this.win = devine.getHasWon();
		}
	}

    @Override
    public void setGame(Game game) {
        this.game = game;
    }


    @Override
	public DrawCanvas.OnSizeChange onDrawSizeChange() {
		return new DrawCanvas.OnSizeChange() {
			@Override
			public void onSizeChange() {
				if (result != null && result.getPoints() != null) {
					result = mView.getCanvas().drawResult(result);
				}
			}
		};
	}
}
