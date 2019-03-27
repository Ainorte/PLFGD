package unice.plfgd.tool.service;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.Game;

public class APIService {
	private static APIService instance;
	private API client;

	//
	private BasePresenter presenter;
	private Game actualGame;

	public static APIService getInstance() {
		if (instance == null) {
			instance = new APIService();
		}
		return instance;
	}

	public <T extends BasePresenter> T getPresenter(Class<T> obj) {
		return (obj.isInstance(presenter)) ? obj.cast(presenter) : null;
	}

	public void setClient(API client) {
		this.client = client;
	}

	public void setPresenter(BasePresenter presenter) {
		this.presenter = presenter;
	}

	public void lauchGame(Game game){
		actualGame = game;

		switch (actualGame){
			case DRAWFORME:
				sendMessage("drawForme",null);
				break;
			case SCT:
				//TODO
				break;
			default:
				//nothing
				break;
		}
	}

	public void sendResponse(Packet packet){

	}

	public void sendMessage(String event, Packet payload) {
		client.sendMessage(event, payload);
	}
}
