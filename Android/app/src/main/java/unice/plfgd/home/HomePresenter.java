package unice.plfgd.home;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import unice.plfgd.common.data.User;
import unice.plfgd.tool.Connexion;

import static android.support.v4.util.Preconditions.checkNotNull;

public class HomePresenter implements HomeContract.Presenter {

	private final HomeContract.View mView;
	private Connexion connexion;

	@SuppressLint("RestrictedApi")
	HomePresenter(@NonNull HomeContract.View lobbyView) {
		mView = checkNotNull(lobbyView);
		mView.setPresenter(this);

		connexion = Connexion.getInstance();
		connexion.setPresenter(this);
	}

	@Override
	public void start() {
		connexion.reset();
	}

	@Override
	public void onSocketReset(Connexion.ResetSocketMessage message) {
		mView.onSocketReset(message);
	}

	@Override
	public void initSocket(String serverDomain, String username) {
		mView.initSocket();
		connexion.setServerURL(Connexion.getServerURL(serverDomain));
		connexion.openSocket(new User(username));
	}

	@Override
	public void onSocketActive() {
		mView.onSocketActive();
	}

	@Override
	public void setTrainingView() {
		mView.setTrainingView();
	}
}
