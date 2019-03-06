package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import unice.plfgd.common.net.Packet;

import java.lang.reflect.ParameterizedType;

public abstract class Handler<T extends Packet> implements DataListener<T> {

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

	@Override
	public abstract void onData(SocketIOClient client, T data, AckRequest ackSender) throws Exception;
}
