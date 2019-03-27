package unice.plfgd.common.action;

import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.net.Packet;

public abstract class Action<T extends Packet, U extends Packet> {
    public abstract U run(UserStore store, T payload);

	public abstract Handler getResultHandler();
}
