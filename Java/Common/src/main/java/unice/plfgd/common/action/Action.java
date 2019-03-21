package unice.plfgd.common.action;

import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.responsehandler.AbstractHandler;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

public abstract class Action<T extends Packet, U extends Packet> {
	public abstract U run(T payload);

	public abstract AbstractHandler getResultHandler();
}
