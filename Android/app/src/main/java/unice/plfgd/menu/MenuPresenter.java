package unice.plfgd.menu;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import unice.plfgd.draw.DrawActivity;
import unice.plfgd.tool.Configuration;
import unice.plfgd.tool.Game;
import unice.plfgd.tool.service.APIService;
import unice.plfgd.tool.service.RemoteAPIImpl;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MenuPresenter implements MenuContract.Presenter {

	private final MenuContract.View mView;

	@SuppressLint("RestrictedApi")
	MenuPresenter(@NonNull MenuContract.View lobbyView) {
		mView = checkNotNull(lobbyView);
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
	public void launchGame(Game game) {
		mView.blockInteration();
		switch (game){
			case DRAWFORME:

				mView.setActivity(DrawActivity.class);
				break;
			case SCT:
				mView.onSocketReset(RemoteAPIImpl.ResetSocketMessage.CONNEXION_LOST);
				break;
		}
	}

	@Override
	public String getUserName() {
		return Configuration.getInstance().getOrNull("username");
	}
}
