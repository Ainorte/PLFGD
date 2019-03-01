package unice.plfgd.base;

import unice.plfgd.tools.Connexion;

public interface BasePresenter {
	void start();
	void onSocketReset(Connexion.ResetSocketMessage message);
}
