package unice.plfgd.training;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.base.BaseView;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.Forme;
import unice.plfgd.draw.DrawCanvas;

public interface TrainingContract {
	interface View extends BaseView<Presenter> {

		void resetCanvas();

		void onReturn();
	}

	interface Presenter extends BasePresenter {
		void onResetCanvas();

		void onReturn();
	}
}
