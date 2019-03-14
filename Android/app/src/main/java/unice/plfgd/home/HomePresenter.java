package unice.plfgd.home;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import unice.plfgd.tool.Configuration;
import unice.plfgd.tool.Connexion;
import unice.plfgd.tool.service.APIService;

import static android.support.v4.util.Preconditions.checkNotNull;

public class HomePresenter implements HomeContract.Presenter {

	private final HomeContract.View mView;

	@SuppressLint("RestrictedApi")
	HomePresenter(@NonNull HomeContract.View lobbyView) {
		mView = checkNotNull(lobbyView);
		mView.setPresenter(this);

		APIService.getInstance().setPresenter(this);
	}

	@Override
	public void start() {
		Connexion.getInstance().reset();
	}

	@Override
	public void onSocketReset(Connexion.ResetSocketMessage message) {
		mView.onSocketReset(message);
	}

	@Override
	public void initSocket(String serverDomain, String username) {
		mView.initSocket();
		final Configuration instance = Configuration.getInstance();
		instance.set("serverDomain", serverDomain);
		instance.set("username", username);
		final Connexion conn = Connexion.getInstance();
		conn.openSocket(instance);
		APIService.getInstance().modeOnline(conn);
	}

	@Override
	public void setDrawActivity() {
		mView.setDrawActivity();
	}

}
