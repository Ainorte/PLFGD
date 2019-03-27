package unice.plfgd.draw;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.base.BaseView;
import unice.plfgd.common.data.Game;
import unice.plfgd.common.net.Packet;

public interface ResultContract {
	interface View extends BaseView<Presenter> {
		void back();

		void changeFragment(Packet payload);

		DrawCanvas getCanvas();
	}

	interface Presenter extends BasePresenter {
		void back();

		DrawCanvas.OnSizeChange onDrawSizeChange();

		void setResult(Packet result);

		void setGame(Game game);

		void replay();

		void changeFragment(Packet payload);
	}
}
