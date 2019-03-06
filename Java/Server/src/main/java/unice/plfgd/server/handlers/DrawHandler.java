package unice.plfgd.server.handlers;

import unice.plfgd.common.data.Draw;
import unice.plfgd.common.net.Dispatcher;
import unice.plfgd.common.net.Exchange;
import unice.plfgd.common.net.Handler;
import unice.plfgd.common.net.Packet;
import unice.plfgd.server.Log;

import java.util.Arrays;

import static unice.plfgd.common.forme.Identifier.sanitize;

public class DrawHandler implements Handler {
	@Override
	public Exchange invoke(Dispatcher dispatcher, Packet data) {
		final Draw draw = (Draw) data;

		Log.log(Log.State.GREEN, sanitize(draw.getPoints(),100).toString());
		System.out.println("Reduced input from "+draw.getPoints().size()+" to "+ sanitize(draw.getPoints(),100).size());
		return Exchange.with("draw").payload(new Draw(sanitize(draw.getPoints(),100),draw.getLar(), draw.getHaut()));
	}
}
