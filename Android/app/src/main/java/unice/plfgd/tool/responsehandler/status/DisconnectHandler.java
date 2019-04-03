package unice.plfgd.tool.responsehandler.status;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.home.HomeContract;
import unice.plfgd.tool.responsehandler.AbstractConnexionHandler;
import unice.plfgd.tool.service.APIService;
import unice.plfgd.tool.service.RemoteAPIImpl;

public class DisconnectHandler extends AbstractConnexionHandler {

	public DisconnectHandler(APIService svc) {
		super(svc);
	}

	@Override
	public void call(Object... args) {
		final BasePresenter presenter = getPresenter(BasePresenter.class);
		if (presenter != null) {
			presenter.onSocketReset(RemoteAPIImpl.ResetSocketMessage.CONNEXION_LOST);
		}
	}
}
