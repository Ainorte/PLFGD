package unice.plfgd.tool.responsehandler;

import unice.plfgd.tool.service.APIService;
import unice.plfgd.tool.service.RemoteAPIImpl;

public abstract class AbstractConnexionHandler extends AbstractHandler {
	private RemoteAPIImpl connexion;

	protected AbstractConnexionHandler(APIService svc) {
		super(svc);
	}

	public RemoteAPIImpl getConnexion() {
		return connexion;
	}

	public AbstractConnexionHandler setConnexion(RemoteAPIImpl c) {
		connexion = c;
		return this;
	}
}
