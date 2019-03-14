package unice.plfgd.tool.handler;

import unice.plfgd.tool.Connexion;
import unice.plfgd.tool.service.APIService;

public abstract class AbstractConnexionHandler extends AbstractHandler {
	private Connexion connexion;

	protected AbstractConnexionHandler(APIService svc) {
		super(svc);
	}

	public AbstractConnexionHandler setConnexion(Connexion c) {
		connexion = c;
		return this;
	}

	public Connexion getConnexion() {
		return connexion;
	}
}
