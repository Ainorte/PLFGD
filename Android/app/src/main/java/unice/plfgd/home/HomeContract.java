package unice.plfgd.home;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.base.BaseView;

public interface HomeContract {
	interface View extends BaseView<Presenter> {
		void blockInteration();

		void resetInteraction();

		void setDrawActivity();
	}

	interface Presenter extends BasePresenter {
		void initSocket(String serverURL, String username);

		void setDrawActivity();
	}
}
