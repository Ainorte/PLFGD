package unice.plfgd.server.handlers;

import unice.plfgd.common.net.Exchange;
import unice.plfgd.common.net.Packet;
import unice.plfgd.server.Dispatcher;

public interface Handler {
	Exchange invoke(Dispatcher dispatcher, Packet data);
}
