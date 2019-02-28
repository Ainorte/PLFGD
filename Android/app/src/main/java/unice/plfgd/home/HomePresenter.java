package unice.plfgd.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import unice.plfgd.tools.Connexion;

import static android.support.v4.util.Preconditions.checkNotNull;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mView;

    @SuppressLint("RestrictedApi")
    public HomePresenter(@NonNull HomeContract.View lobbyView) {
        mView = checkNotNull(lobbyView);

        mView.setPresenter(this);
    }

    @Override
	public void start() {

	}

    @Override
    public void initSocket() {
        Connexion.getInstance().openSocket(this);
    }

    @Override
    public void onSocketActive() {
        mView.onSocketActive();
    }
}
