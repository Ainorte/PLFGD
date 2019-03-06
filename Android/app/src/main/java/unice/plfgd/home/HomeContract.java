package unice.plfgd.home;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.base.BaseView;

public interface HomeContract {
	interface View extends BaseView<Presenter> {
		void initSocket();

		void onSocketActive();
	}

	interface Presenter extends BasePresenter {
		void initSocket(String serverURL, String username);

		void onSocketActive();
	}
}
