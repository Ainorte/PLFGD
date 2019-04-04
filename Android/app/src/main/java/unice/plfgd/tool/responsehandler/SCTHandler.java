package unice.plfgd.tool.responsehandler;

import unice.plfgd.common.data.Game;
import unice.plfgd.common.data.packet.ResultSCT;
import unice.plfgd.draw.ResultPresenter;
import unice.plfgd.draw.SCTResultPresenter;
import unice.plfgd.menu.MenuPresenter;
import unice.plfgd.tool.service.APIService;

public class SCTHandler extends AbstractHandler{

	public SCTHandler(APIService svc) {
		super(svc);
	}

	@Override
	public void call(Object... args) {

		MenuPresenter presenter = getPresenter(MenuPresenter.class);
		if(presenter != null) {
			presenter.displayGame(null);
		}
		else{
			SCTResultPresenter resultPresenter = getPresenter(SCTResultPresenter.class);
			resultPresenter.changeFragment(null);
		}
	}
}
