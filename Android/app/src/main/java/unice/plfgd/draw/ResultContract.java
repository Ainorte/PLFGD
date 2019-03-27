package unice.plfgd.draw;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.base.BaseView;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.Game;

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
