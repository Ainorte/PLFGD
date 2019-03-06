package unice.plfgd.tool.handler.error;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.tool.Connexion;
import unice.plfgd.tool.handler.AbstractHandler;

public class DisconnectHandler extends AbstractHandler {
	public DisconnectHandler(Connexion connexion) {
		super(connexion);
	}

	@Override
	public void call(Object... args) {
		getConnexion().getPresenter(BasePresenter.class)
				.onSocketReset(Connexion.ResetSocketMessage.CONNEXION_LOST);
	}
}
