package unice.plfgd.tool.responsehandler.status;

import unice.plfgd.home.HomeContract;
import unice.plfgd.tool.responsehandler.AbstractConnexionHandler;
import unice.plfgd.tool.service.APIService;

public class ConnectHandler extends AbstractConnexionHandler {
	public ConnectHandler(APIService svc) {
		super(svc);
	}

	@Override
	public void call(Object... args) {
		getConnexion().Identify();
		getPresenter(HomeContract.Presenter.class).setMenuActivity();
	}
}
