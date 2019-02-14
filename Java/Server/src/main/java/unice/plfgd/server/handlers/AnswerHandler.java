package unice.plfgd.server.handlers;

import unice.plfgd.common.data.Answer;
import unice.plfgd.common.data.Result;
import unice.plfgd.common.net.Dispatcher;
import unice.plfgd.common.net.Exchange;
import unice.plfgd.common.net.Handler;
import unice.plfgd.common.net.Packet;
import unice.plfgd.server.Log;

public class AnswerHandler implements Handler {
	@Override
	public Exchange invoke(Dispatcher dispatcher, Packet data) {
		final Answer answer = (Answer) data;
		Log.log("The client guessed " + answer.getValue());

		return Exchange.with("result").payload(new Result(answer.getValue().equals("Nice")));
	}
}
