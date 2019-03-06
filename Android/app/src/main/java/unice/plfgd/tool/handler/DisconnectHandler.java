package unice.plfgd.tool.handler;

import io.socket.emitter.Emitter;
import unice.plfgd.tool.Connexion;

public class DisconnectHandler implements Emitter.Listener {

	Connexion connexion;

	public DisconnectHandler(Connexion connexion) {
		this.connexion = connexion;
	}

	@Override
	public void call(Object... args) {
		connexion.getPresenter().onSocketReset(Connexion.ResetSocketMessage.CONNEXION_LOST);
	}
}
