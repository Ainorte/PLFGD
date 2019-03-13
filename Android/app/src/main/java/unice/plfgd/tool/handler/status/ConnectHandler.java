package unice.plfgd.tool.handler.status;

import unice.plfgd.home.HomeContract;
import unice.plfgd.tool.Connexion;
import unice.plfgd.tool.handler.AbstractHandler;

public class ConnectHandler extends AbstractHandler {
	public ConnectHandler(Connexion connexion) {
		super(connexion);
	}

	@Override
	public void call(Object... args) {
		getConnexion().Identify();
		getConnexion().getPresenter(HomeContract.Presenter.class).setDrawActivity();
	}
}
