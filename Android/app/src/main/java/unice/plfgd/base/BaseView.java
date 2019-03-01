package unice.plfgd.base;

import unice.plfgd.tools.Connexion;

public interface BaseView<T> {
	void setPresenter(T presenter);
	void onSocketReset(Connexion.ResetSocketMessage message);
}

