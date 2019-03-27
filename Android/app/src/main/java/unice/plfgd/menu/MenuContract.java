package unice.plfgd.menu;

import android.app.Activity;
import unice.plfgd.base.BasePresenter;
import unice.plfgd.base.BaseView;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.Game;

public interface MenuContract {
	interface View extends BaseView<Presenter> {
		void blockInteration();

		void resetInteraction();

		void setActivity(Class<? extends Activity> activity, Packet payload);
	}

	interface Presenter extends BasePresenter {
		void launchGame(Game game);

		void displayGame(Packet payload);

		String getUserName();
	}
}
