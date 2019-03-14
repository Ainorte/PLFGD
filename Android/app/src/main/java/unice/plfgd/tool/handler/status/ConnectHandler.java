package unice.plfgd.tool.handler.status;

import unice.plfgd.home.HomeContract;
import unice.plfgd.tool.Connexion;
import unice.plfgd.tool.handler.AbstractConnexionHandler;
import unice.plfgd.tool.handler.AbstractHandler;
import unice.plfgd.tool.service.APIService;

public class ConnectHandler extends AbstractConnexionHandler {
	public ConnectHandler(APIService svc) {
		super(svc);
	}

	@Override
	public void call(Object... args) {
		getConnexion().Identify();
		getPresenter(HomeContract.Presenter.class).setDrawActivity();
	}
}
