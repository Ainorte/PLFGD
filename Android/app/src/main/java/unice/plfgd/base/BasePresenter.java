package unice.plfgd.base;

import unice.plfgd.common.data.Draw;
import unice.plfgd.tool.service.RemoteAPIImpl;

public interface BasePresenter {
	void start();

	void onSocketReset(RemoteAPIImpl.ResetSocketMessage message);

	Draw getResult();
}
