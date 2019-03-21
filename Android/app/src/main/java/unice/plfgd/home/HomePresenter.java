package unice.plfgd.home;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import unice.plfgd.tool.Configuration;
import unice.plfgd.tool.service.APIService;
import unice.plfgd.tool.service.RemoteAPIImpl;

import static android.support.v4.util.Preconditions.checkNotNull;

public class HomePresenter implements HomeContract.Presenter {

	private final HomeContract.View mView;

	@SuppressLint("RestrictedApi")
	HomePresenter(@NonNull HomeContract.View lobbyView) {
		mView = checkNotNull(lobbyView);
		mView.setPresenter(this);
	}

	@Override
	public void start() {
		RemoteAPIImpl.getInstance().reset();
		APIService.getInstance().setPresenter(this);
	}

	@Override
	public void onSocketReset(RemoteAPIImpl.ResetSocketMessage message) {
		mView.onSocketReset(message);
	}

	@Override
	public void initSocket(String serverDomain, String username) {
		mView.initSocket();
		final Configuration instance = Configuration.getInstance();
		instance.set("serverDomain", serverDomain);
		instance.set("username", username);
		final RemoteAPIImpl conn = RemoteAPIImpl.getInstance();
		conn.openSocket(instance);
		APIService.getInstance().setClient(conn);
	}

	@Override
	public void setDrawActivity() {
		mView.setDrawActivity();
	}

}
