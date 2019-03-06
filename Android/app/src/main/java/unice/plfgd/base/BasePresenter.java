package unice.plfgd.base;

import unice.plfgd.tool.Connexion;

public interface BasePresenter {
	void start();

	void onSocketReset(Connexion.ResetSocketMessage message);
}
