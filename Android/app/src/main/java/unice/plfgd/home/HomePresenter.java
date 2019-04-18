package unice.plfgd.home;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import unice.plfgd.tool.Configuration;
import unice.plfgd.tool.service.APIService;
import unice.plfgd.tool.service.LocalAPIImpl;
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
	public void setScore(int score) {
		mView.setScore(score);
	}

	@Override
	public void initSocket() {
        mView.blockInteraction();
		final RemoteAPIImpl conn = RemoteAPIImpl.getInstance();
		conn.openSocket(Configuration.getInstance());
		APIService.getInstance().setClient(conn);
	}

	@Override
	public void initLocal() {
		APIService.getInstance().setClient(new LocalAPIImpl());
		setMenuActivity();
	}

	@Override
	public String getUserName() {
		return Configuration.getInstance().getOrNull("username");
	}

	@Override
	public void setMenuActivity() {
		mView.setMenuActivity();
	}

}
