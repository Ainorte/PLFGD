package unice.plfgd.draw;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.base.BaseView;
import unice.plfgd.common.data.Game;
import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.common.forme.Forme;
import unice.plfgd.common.net.Packet;

public interface DrawContract {
	interface View extends BaseView<Presenter> {
		void showOrder(Forme forme);

		void resetCanvas();

		DrawCanvas getCanvas();

		void onSending();

		void resultSwitch(Packet result, Game game);
	}

	interface Presenter extends BasePresenter {
		void onResetCanvas();

		void onValid();

		void resultSwitch(Packet result, Game game);

		void setDraw(Draw draw);

		void setOrder(Forme forme);

		DrawCanvas.OnSizeChange onDrawSizeChange();

		android.view.View.OnTouchListener onCanvasTouch();
	}
}
