package unice.plfgd.draw;

import android.support.annotation.NonNull;
import unice.plfgd.common.data.Draw;
import unice.plfgd.tool.service.APIService;
import unice.plfgd.tool.service.RemoteAPIImpl;

public class ResultPresenter implements ResultContract.Presenter {

	private ResultContract.View mView;
	private Draw result;

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
	public Draw getResult() {
		return result;
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
	public void setResult(Draw result) {
		this.result = result;
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
