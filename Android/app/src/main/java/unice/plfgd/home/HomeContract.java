package unice.plfgd.home;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.base.BaseView;

public interface HomeContract {
	interface View extends BaseView<Presenter> {
		void blockInteraction();

		void resetInteraction();

		void setMenuActivity();
	}

	interface Presenter extends BasePresenter {
		void initSocket();

		void initLocal();

		String getUserName();

		void setMenuActivity();
	}
}
