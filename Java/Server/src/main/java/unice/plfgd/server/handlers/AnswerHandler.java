package unice.plfgd.server.handlers;

import unice.plfgd.common.data.Answer;
import unice.plfgd.common.data.Result;
import unice.plfgd.common.net.*;
import unice.plfgd.server.Log;

public class AnswerHandler implements Handler {
	@Override
	public Exchange invoke(Dispatcher dispatcher, Packet data) {
		final Answer answer = (Answer) data;
		Log.log("The client guessed " + answer.getValue());

		return Exchange.with("result").payload(new Result(answer.getValue().equals("Nice")));
	}
}
