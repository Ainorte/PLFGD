package unice.plfgd.tool.handler.error;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.home.HomeContract;
import unice.plfgd.tool.Connexion;
import unice.plfgd.tool.handler.AbstractHandler;

public class TimeoutHandler extends AbstractHandler {
	public TimeoutHandler(Connexion connexion) {
		super(connexion);
	}

	@Override
	public void call(Object... args) {
		Connexion connexion = getConnexion();
		connexion.getPresenter(HomeContract.Presenter.class)
				.onSocketReset(Connexion.ResetSocketMessage.TIMEOUT);
		connexion.close();
	}
}
