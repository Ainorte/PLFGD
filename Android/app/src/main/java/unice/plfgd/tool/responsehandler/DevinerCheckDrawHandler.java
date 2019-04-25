package unice.plfgd.tool.responsehandler;

import unice.plfgd.common.data.Game;
import unice.plfgd.common.data.packet.DevinerFormeResult;
import unice.plfgd.draw.DrawContract;
import unice.plfgd.tool.service.APIService;

public class DevinerCheckDrawHandler extends AbstractHandler {
    public DevinerCheckDrawHandler(APIService svc) {
        super(svc);
    }

    @Override
    public void call(Object... args) {
		DevinerFormeResult devine = castValue(args[0], DevinerFormeResult.class);

		if (devine.getHasWon() || !devine.getHasGuessedRight()) {
			Game game = APIService.getInstance().getActualGame();
			APIService.getInstance().resetGame();
			getPresenter(DrawContract.Presenter.class).resultSwitch(devine, game);
		} else {
			getPresenter(DrawContract.Presenter.class).switchNext();
		}
    }
}
