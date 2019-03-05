package unice.plfgd.base;

import unice.plfgd.tool.Connexion;

public interface BaseView<T> {
	void setPresenter(T presenter);
	void onSocketReset(Connexion.ResetSocketMessage message);
}

