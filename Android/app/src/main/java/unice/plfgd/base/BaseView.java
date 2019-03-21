package unice.plfgd.base;

import unice.plfgd.tool.service.RemoteAPIImpl;

public interface BaseView<T extends BasePresenter> {
	void setPresenter(T presenter);

	void onSocketReset(RemoteAPIImpl.ResetSocketMessage message);
}

