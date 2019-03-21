package unice.plfgd.tool.service;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.common.net.Packet;

public class APIService {
	private static APIService instance;

	public static APIService getInstance() {
		if (instance == null) {
			instance = new APIService();
		}
		return instance;
	}

	//

	private API client;
	private BasePresenter presenter;

	public <T extends BasePresenter> T getPresenter(Class<T> obj) {
		return (obj.isInstance(presenter)) ? obj.cast(presenter) : null;
	}

	public void setClient(API client) {
		this.client = client;
	}

	public void setPresenter(BasePresenter presenter) {
		this.presenter = presenter;
	}

	public void sendMessage(String event, Packet payload) {
		client.sendMessage(event, payload);
	}
}