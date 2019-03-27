package unice.plfgd.draw;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import unice.plfgd.common.data.DetecForme;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.RecogForme;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.Game;
import unice.plfgd.tool.service.APIService;
import unice.plfgd.tool.service.RemoteAPIImpl;

public class ResultPresenter implements ResultContract.Presenter {

	private ResultContract.View mView;
	private Draw result;
	private Game game;

	public ResultPresenter(@NonNull ResultContract.View view) {
		this.mView = view;
		mView.setPresenter(this);
	}

	@Override
	public void start() {
		APIService.getInstance().setPresenter(this);
	}

	@Override
	public void onSocketReset(RemoteAPIImpl.ResetSocketMessage message) {
		mView.onSocketReset(message);
	}

	@Override
	public void back() {
	    mView.back();
	}

	@Override
	public void replay() {
		APIService.getInstance().lauchGame(game);
	}

    @Override
    public void changeFragment(Packet payload) {
        mView.changeFragment(payload);
    }


    @Override
	public void setResult(Packet result) {
		if(result instanceof DetecForme) {
			DetecForme recogForme = (DetecForme) result;
			this.result = recogForme.getDraw();
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
