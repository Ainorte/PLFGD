package unice.plfgd.base;

import unice.plfgd.common.data.Draw;
import unice.plfgd.tool.Connexion;

public interface BasePresenter {
	void start();

	void onSocketReset(Connexion.ResetSocketMessage message);

	Draw getDraw();
}
