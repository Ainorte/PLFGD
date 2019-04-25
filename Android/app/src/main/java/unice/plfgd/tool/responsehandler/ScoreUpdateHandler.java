package unice.plfgd.tool.responsehandler;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.common.data.packet.Score;
import unice.plfgd.tool.service.APIService;

public class ScoreUpdateHandler extends AbstractHandler {

	public ScoreUpdateHandler(APIService svc) {
		super(svc);
	}

	@Override
	public void call(Object... args) {
		if (args.length >= 1) {
			int score = castValue(args[0], Score.class).getScore();

			APIService.getInstance().getPresenter(BasePresenter.class).setScore(score);
		}
	}
}
