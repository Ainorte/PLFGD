package unice.plfgd.draw;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.base.BaseView;

public interface ResultContract {
	interface View extends BaseView<Presenter> {
		void back();

		void replay();
	}

	interface Presenter extends BasePresenter {
		void back();

		void replay();
	}
}
