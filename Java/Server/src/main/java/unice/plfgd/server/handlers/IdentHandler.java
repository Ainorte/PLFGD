package unice.plfgd.server.handlers;

import unice.plfgd.common.data.User;
import unice.plfgd.common.net.Dispatcher;
import unice.plfgd.common.net.Exchange;
import unice.plfgd.common.net.Handler;
import unice.plfgd.common.net.Packet;
import unice.plfgd.server.Log;

public class IdentHandler implements Handler {
	@Override
	public Exchange invoke(Dispatcher dispatcher, Packet data) {
		// We assume that it's right. Worst case scenario, it crashes and the handle loop catches it.
		final User remoteUser = (User) data;

		dispatcher.setRemoteClient(remoteUser);
		Log.log("The remote user is " + remoteUser.getName());

		// We send a "Question" request to the client
		//return Exchange.with("question").payload(new Question("Merci de saisir Nice."));
		return null;
	}
}
