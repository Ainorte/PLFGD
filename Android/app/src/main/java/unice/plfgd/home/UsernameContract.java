package unice.plfgd.home;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.base.BaseView;

public interface UsernameContract {
	interface View extends BaseView<Presenter> {
		void onValid();
	}

	interface Presenter extends BasePresenter {
		void onValid(String name);
	}
}
