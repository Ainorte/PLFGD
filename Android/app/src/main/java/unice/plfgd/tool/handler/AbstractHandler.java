package unice.plfgd.tool.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import io.socket.emitter.Emitter;
import unice.plfgd.base.BasePresenter;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.service.APIService;

public abstract class AbstractHandler implements Emitter.Listener {
	private final ObjectMapper om;
	private final APIService service;

	AbstractHandler(APIService svc) {
		this.service = svc;
		om = new ObjectMapper();
		om.registerModule(new JsonOrgModule());
	}

	<T extends Packet> T castValue(Object obj, Class<T> clazz) {
		return om.convertValue(obj, clazz);
	}

	protected  <T extends BasePresenter> T getPresenter(Class<T> obj) {
		return service.getPresenter(obj);
	}
}
