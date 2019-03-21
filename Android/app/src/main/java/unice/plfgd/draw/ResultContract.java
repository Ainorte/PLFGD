package unice.plfgd.draw;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.base.BaseView;
import unice.plfgd.common.data.Draw;

public interface ResultContract {
	interface View extends BaseView<Presenter> {
		void back();

		void replay();

		DrawCanvas getCanvas();
	}

	interface Presenter extends BasePresenter {
		void back();

		void replay();

		DrawCanvas.OnSizeChange onDrawSizeChange();

		void setResult(Draw result);
	}
}
