package unice.plfgd.tool.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import io.socket.emitter.Emitter;
import unice.plfgd.base.BasePresenter;
import unice.plfgd.common.data.Draw;
import unice.plfgd.draw.DrawContract;
import unice.plfgd.tool.Connexion;

public class DrawHandler implements Emitter.Listener {

	private Connexion connexion;

	public DrawHandler(Connexion connexion) {
		this.connexion = connexion;
	}

	@Override
	public void call(Object... args) {
		if (args.length >= 1) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JsonOrgModule());
			Draw draw = mapper.convertValue(args[0], Draw.class);

			BasePresenter presenter = connexion.getPresenter();
			if (presenter instanceof DrawContract.Presenter) {
				DrawContract.Presenter drawPresenter = (DrawContract.Presenter) presenter;
				drawPresenter.resultSwitch(draw);
			}
		}
	}
}
