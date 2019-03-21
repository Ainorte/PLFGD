package unice.plfgd.tool.service;

import android.util.Log;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.service.local.Action;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Local API implementation, to replace the remote WS server in case of offline mode.
 */
public class LocalAPIImpl implements API {
	private static final String TAG = "LOCAL_API_IMPLEMENTATION";
	private final Map<String, Action> routes = new HashMap<>();

	@Override
	public void sendMessage(String event, Packet payload) {
		if (routes.containsKey(event)) {
			Log.i(TAG, String.format("Route found for event %s, running action...", event));
			Objects.requireNonNull(routes.get(event)).run(payload);
		} else {
			Log.wtf(TAG, String.format("Route not found for event %s, ignoring", event));
		}
	}

	public LocalAPIImpl register(String event, Action action) {
		this.routes.put(event, action);
		return this;
	}
}
