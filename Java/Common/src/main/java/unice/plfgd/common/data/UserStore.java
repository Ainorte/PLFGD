package unice.plfgd.common.data;

import unice.plfgd.common.net.Packet;

import java.util.HashMap;

public class UserStore {
	private String name;
	private int score;
	private Game currentGame;
	private HashMap<String, Packet> data;

	public void incrementScore() {
		this.score++;
	}

	public UserStore() {
		currentGame = Game.NONE;
		data = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Game getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(Game currentGame) {
		this.currentGame = currentGame;
	}

	public void resetGame() {
		data = new HashMap<>();
		currentGame = Game.NONE;
	}

	public void addOrReplaceData(String name, Packet packet) {
		data.put(name, packet);
	}

	public <T extends Packet> T getData(String name, Class<T> clazz) {
		Packet packet = data.get(name);
		if (packet != null && clazz.isInstance(packet)) {
			return (T) packet;
		}
		return null;

	}
}
