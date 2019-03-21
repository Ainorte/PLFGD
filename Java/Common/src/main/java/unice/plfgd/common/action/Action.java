package unice.plfgd.common.action;

import unice.plfgd.common.net.Packet;

public abstract class Action<T extends Packet, U extends Packet> {
	public abstract U run(T payload);

	public abstract Handler getResultHandler();
}
