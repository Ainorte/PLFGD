package unice.plfgd.server.handler;

import com.corundumstudio.socketio.listener.DataListener;
import unice.plfgd.common.net.Packet;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

public abstract class Handler<T extends Packet> implements DataListener<T> {
	// One rubber duck won't be enough to debug this hellish class if you touch it.
	// Beware, more than one adventurer has gotten lost in this maze
	public static HashMap<String, Handler<? extends Packet>> buildHandlersMap() {
		return new HashMap<>();
	}

	public Class<T> getGenericTypeClass() {
		String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return (Class<T>) clazz;
	}
}
