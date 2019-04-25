package unice.plfgd.draw;

import android.support.annotation.NonNull;
import unice.plfgd.common.data.Game;
import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.common.data.packet.ResultSCT;
import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.service.APIService;
import unice.plfgd.tool.service.RemoteAPIImpl;

public class SCTResultPresenter implements SCTResultContract.Presenter {

	private SCTResultContract.View mView;
	private Draw serverResult;
	private Draw clientResult;
	private Game game;
	private Boolean win;

	public SCTResultPresenter(@NonNull SCTResultContract.View view) {
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
	public DrawCanvas.OnSizeChange onClientDrawSizeChange() {
		return new DrawCanvas.OnSizeChange() {
			@Override
			public void onSizeChange() {
				if (clientResult != null && clientResult.getPoints() != null) {
					clientResult = mView.getClientCanvas().drawResult(clientResult);
				}
			}
		};
	}

	@Override
	public DrawCanvas.OnSizeChange onServerDrawSizeChange() {
		return new DrawCanvas.OnSizeChange() {
			@Override
			public void onSizeChange() {
				if (serverResult != null && serverResult.getPoints() != null) {
					serverResult = mView.getServerCanvas().drawResult(serverResult);
				}
			}
		};
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
			case SCT:
				mView.setCommentary(game, win);
				break;
		}
	}


	@Override
	public void setServerResult(Packet serverResult) {
		if (serverResult instanceof ResultSCT) {
			ResultSCT sct = (ResultSCT) serverResult;
			this.serverResult = sct.getEnemy();
			this.clientResult = sct.getPlayer();
			this.win = sct.getWin();
		}
	}

    @Override
    public void setGame(Game game) {
        this.game = game;
    }
}
