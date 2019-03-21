package unice.plfgd.tool.responsehandler;

import unice.plfgd.tool.service.RemoteAPIImpl;
import unice.plfgd.tool.service.APIService;

public abstract class AbstractConnexionHandler extends AbstractHandler {
	private RemoteAPIImpl connexion;

	protected AbstractConnexionHandler(APIService svc) {
		super(svc);
	}

	public AbstractConnexionHandler setConnexion(RemoteAPIImpl c) {
		connexion = c;
		return this;
	}

	public RemoteAPIImpl getConnexion() {
		return connexion;
	}
}
