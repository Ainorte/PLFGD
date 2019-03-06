package unice.plfgd.tool.handler;

import unice.plfgd.common.data.Draw;
import unice.plfgd.draw.DrawContract;
import unice.plfgd.tool.Connexion;

public class DrawHandler extends AbstractHandler {
	public DrawHandler(Connexion connexion) {
		super(connexion);
	}

	@Override
	public void call(Object... args) {
		if (args.length >= 1) {
			Draw draw = castValue(args[0], Draw.class);

			getConnexion().getPresenter(DrawContract.Presenter.class).resultSwitch(draw);
		}
	}
}
