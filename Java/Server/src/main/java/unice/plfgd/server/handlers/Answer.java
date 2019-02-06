package unice.plfgd.server.handlers;

import unice.plfgd.common.Move;
import unice.plfgd.common.net.Exchange;
import unice.plfgd.common.net.Packet;
import unice.plfgd.common.net.Question;
import unice.plfgd.server.Dispatcher;
import unice.plfgd.server.Log;

public class Answer implements Handler {
	@Override
	public Exchange invoke(Dispatcher dispatcher, Packet data) {
		final var answer = (unice.plfgd.common.net.Answer) data;
		Log.log("The client guessed " + answer.value);

		final int toFind = dispatcher.getToFind();
		if (answer.value == toFind) {
			Log.log("The client rightfully guessed");
			return null; // Shutting down
		}

		final boolean wasBigger = answer.value > toFind;
		dispatcher.getLastMoves().add(new Move(answer.value, wasBigger));
		Log.log("The client still needs to search");
		return Exchange.with("question").payload(new Question(wasBigger));
	}
}
