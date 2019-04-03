package unice.plfgd.tool.responsehandler;

import unice.plfgd.common.data.Game;
import unice.plfgd.common.data.packet.ResultDrawForme;
import unice.plfgd.draw.DrawContract;
import unice.plfgd.tool.service.APIService;

public class ResultDrawFormeHandler extends AbstractHandler {

	public ResultDrawFormeHandler(APIService svc) {
		super(svc);
	}

	@Override
	public void call(Object... args) {
		if (args.length >= 1) {
			ResultDrawForme detecForme = castValue(args[0], ResultDrawForme.class);

			Game game = APIService.getInstance().getActualGame();

			APIService.getInstance().resetGame();

			getPresenter(DrawContract.Presenter.class).resultSwitch(detecForme, game);
		}
	}
}
