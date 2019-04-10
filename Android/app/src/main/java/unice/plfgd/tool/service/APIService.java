package unice.plfgd.tool.service;

import android.util.Log;
import unice.plfgd.base.BasePresenter;
import unice.plfgd.common.data.Game;
import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.net.Packet;

public class APIService {
	private static APIService instance;

    //
    private API client;
    private UserStore localCache = new UserStore();
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
        if (client instanceof LocalAPIImpl) {
            ((LocalAPIImpl) client).setCache(localCache);
        }
		this.client = client;
	}

	public void setPresenter(BasePresenter presenter) {
		this.presenter = presenter;
	}

	public void launchGame(Game game){
		actualGame = game;

		switch (actualGame){
			case DRAWFORME:
				sendMessage("drawForme",null);
				break;
			case SCT:
				sendMessage("sct", null);
				break;
			case DEVINER:
				break;
			default:
				//nothing
				break;
		}
	}

	public void sendResponse(Packet packet){
		switch (actualGame) {
			case DRAWFORME:
				sendMessage("resultDrawForme", packet);
				break;
			case SCT:
				sendMessage("resultSCT",packet);
				break;
		}
	}

	public Game getActualGame() {
		return actualGame;
	}

	public void resetGame(){
		actualGame = Game.NONE;
	}

	public void sendMessage(String event, Packet payload) {
		client.sendMessage(event, payload);
	}
}
