package unice.plfgd.base;

import unice.plfgd.tool.Connexion;

public interface BaseView<T extends BasePresenter> {
	void setPresenter(T presenter);
	void onSocketReset(Connexion.ResetSocketMessage message);
}

