package unice.plfgd.tool.service.local;

import unice.plfgd.common.net.Packet;

public interface Action {
	public void run(Packet payload);
}
