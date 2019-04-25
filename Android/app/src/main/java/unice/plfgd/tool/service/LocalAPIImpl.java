package unice.plfgd.tool.service;

import android.util.Log;
import unice.plfgd.common.action.*;
import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.responsehandler.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Local API implementation, to replace the remote WS server in case of offline mode.
 */
public class LocalAPIImpl implements API {
	private static final String TAG = "LOCAL_API_IMPLEMENTATION";
	private final Map<String, Action> routes = new HashMap<>();
    private UserStore cache;

	public LocalAPIImpl() {
		registerHandlers();
	}

    void setCache(UserStore cache) {
        this.cache = cache;
    }

	@Override
	public void sendMessage(String event, Packet payload) {
		if (routes.containsKey(event)) {
			Log.i(TAG, String.format("Route found for event %s, running action...", event));
			final Action action = Objects.requireNonNull(routes.get(event));
            Packet result = action.run(cache, payload);
			Log.i(TAG, "Obtained a result");
			action.getResultHandler().call(result);
		} else {
			Log.wtf(TAG, String.format("Route not found for event %s, ignoring", event));
		}
	}

	private void registerHandlers() {
		this.routes.put("ResultDrawFormeHandler", new ResultDrawFormeAction(new ResultDrawFormeHandler(APIService.getInstance())));
		this.routes.put("drawForme",new DrawFormeAction( new DrawFormeHandler(APIService.getInstance())));
		this.routes.put("resultDrawForme", new ResultDrawFormeAction(new ResultDrawFormeHandler(APIService.getInstance())));
		this.routes.put("sct", new SCTAction(new SCTHandler(APIService.getInstance())));
		this.routes.put("resultSCT",new ResultSCTAction(new ResultSCTHandler(APIService.getInstance())));
		this.routes.put("devinerFormeInit", new DevinerFormeInitAction(new DevinerFormeInitHandler(APIService.getInstance())));
		this.routes.put("devinerCheckDraw", new DevinerCheckDrawAction(new DevinerCheckDrawHandler(APIService.getInstance())));
		this.routes.put("scoreUpdate", new ScoreUpdateAction(new ScoreUpdateHandler(APIService.getInstance())));
	}
}
