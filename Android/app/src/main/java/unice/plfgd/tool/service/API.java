package unice.plfgd.tool.service;

import unice.plfgd.common.net.Packet;

public interface API {
	void sendMessage(String event, Packet payload);
}
