package unice.plfgd.tool.service;

import android.util.Log;
import unice.plfgd.base.BasePresenter;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.Connexion;

public class APIService {
	private static APIService instance;

	public static APIService getInstance() {
		if (instance == null) {
			instance = new APIService();
		}
		return instance;
	}

	//

	public void modeOnline(Connexion client) {
		IS_ONLINE = true;
		onlineClient = client;
	}

	private boolean IS_ONLINE;
	private Connexion onlineClient;
	private BasePresenter presenter;

	public <T extends BasePresenter> T getPresenter(Class<T> obj) {
		return (obj.isInstance(presenter)) ? obj.cast(presenter) : null;
	}

	public void setPresenter(BasePresenter presenter) {
		this.presenter = presenter;
	}

	public void sendMessage(String event, Packet payload) {
		if (IS_ONLINE) {
			onlineClient.sendMessage(event, payload);
		} else {
			// TODO
			Log.wtf("APIService", "TODO: Offline mode");
		}
	}
}
