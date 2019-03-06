package unice.plfgd.tool.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import io.socket.emitter.Emitter;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.Connexion;

public abstract class AbstractHandler implements Emitter.Listener {
	private final Connexion connexion;
	private final ObjectMapper om;

	protected AbstractHandler(Connexion connexion) {
		this.connexion = connexion;
		om = new ObjectMapper();
		om.registerModule(new JsonOrgModule());
	}

	protected Connexion getConnexion() {
		return connexion;
	}

	<T extends Packet> T castValue(Object obj, Class<T> clazz) {
		return om.convertValue(obj, clazz);
	}
}
