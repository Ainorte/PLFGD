package unice.plfgd.draw;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.base.BaseView;
import unice.plfgd.common.data.Game;
import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.net.Packet;
import unice.plfgd.draw.DrawCanvas;

public interface SCTResultContract {
	interface View extends BaseView<Presenter> {
		void back();

		void changeFragment(Packet payload);

		DrawCanvas getClientCanvas();

		DrawCanvas getServerCanvas();

		void setCommentary(Game game, Boolean win);
	}

	interface Presenter extends BasePresenter {
		void back();

		DrawCanvas.OnSizeChange onClientDrawSizeChange();

		DrawCanvas.OnSizeChange onServerDrawSizeChange();

		void setServerResult(Packet serverResult);

		void setGame(Game game);

		void replay();

		void changeFragment(Packet payload);

		void setCommentary();
	}
}
