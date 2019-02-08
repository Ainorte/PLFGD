package unice.plfgd.common.net;

public interface Handler {
	Exchange invoke(Dispatcher dispatcher, Packet data);
}
