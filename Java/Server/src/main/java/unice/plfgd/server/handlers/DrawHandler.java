package unice.plfgd.server.handlers;

import unice.plfgd.common.data.Draw;
import unice.plfgd.common.net.Dispatcher;
import unice.plfgd.common.net.Exchange;
import unice.plfgd.common.net.Handler;
import unice.plfgd.common.net.Packet;
import unice.plfgd.server.Log;

import java.util.Arrays;

public class DrawHandler implements Handler {
	@Override
	public Exchange invoke(Dispatcher dispatcher, Packet data) {
		final Draw draw = (Draw) data;
		Log.log(Log.State.GREEN, draw.getPoints().toString());
		return Exchange.with("draw").payload(new Draw(draw.getPoints(),draw.getLar(), draw.getHaut()));
	}
}
