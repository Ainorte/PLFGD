package unice.plfgd.tool.responsehandler.status;

import unice.plfgd.home.HomeContract;
import unice.plfgd.tool.service.RemoteAPIImpl;
import unice.plfgd.tool.responsehandler.AbstractConnexionHandler;
import unice.plfgd.tool.service.APIService;

public class DisconnectHandler extends AbstractConnexionHandler {

	public DisconnectHandler(APIService svc) {
		super(svc);
	}

	@Override
	public void call(Object... args) {
		final HomeContract.Presenter presenter = getPresenter(HomeContract.Presenter.class);
		if (presenter != null) {
			presenter.onSocketReset(RemoteAPIImpl.ResetSocketMessage.CONNEXION_LOST);
		}
	}
}
