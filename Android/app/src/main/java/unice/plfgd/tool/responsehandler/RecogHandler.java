package unice.plfgd.tool.responsehandler;

import unice.plfgd.common.data.DetecForme;
import unice.plfgd.draw.DrawContract;
import unice.plfgd.tool.Game;
import unice.plfgd.tool.service.APIService;

public class RecogHandler extends AbstractHandler {

	public RecogHandler(APIService svc) {
		super(svc);
	}

	@Override
	public void call(Object... args) {
		if (args.length >= 1) {
			DetecForme detecForme = castValue(args[0], DetecForme.class);

			Game game = APIService.getInstance().getActualGame();

			APIService.getInstance().resetGame();

			getPresenter(DrawContract.Presenter.class).resultSwitch(detecForme, game);
		}
	}
}
