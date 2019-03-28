package unice.plfgd.tool.responsehandler;

import unice.plfgd.common.data.packet.FormeRequest;
import unice.plfgd.draw.ResultPresenter;
import unice.plfgd.menu.MenuPresenter;
import unice.plfgd.tool.service.APIService;

public class DrawFormeHandler extends AbstractHandler {
	public DrawFormeHandler(APIService svc) {
		super(svc);
	}

	@Override
	public void call(Object... args) {
		if (args.length >= 1) {
			FormeRequest forme = castValue(args[0], FormeRequest.class);
			MenuPresenter presenter = getPresenter(MenuPresenter.class);
			if(presenter != null) {
				presenter.displayGame(forme);
			}
			else{
				ResultPresenter resultPresenter = getPresenter(ResultPresenter.class);
				resultPresenter.changeFragment(forme);
			}
		}
	}
}
