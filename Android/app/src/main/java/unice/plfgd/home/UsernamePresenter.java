package unice.plfgd.home;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import unice.plfgd.tool.Configuration;
import unice.plfgd.tool.service.APIService;
import unice.plfgd.tool.service.RemoteAPIImpl;

import static android.support.v4.util.Preconditions.checkNotNull;

public class UsernamePresenter implements UsernameContract.Presenter {

	private final UsernameContract.View mView;

	@SuppressLint("RestrictedApi")
	UsernamePresenter(@NonNull UsernameContract.View lobbyView) {
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
	public void onValid(String name) {
		Configuration.getInstance().set("username", name);
		mView.onValid();
	}
}
