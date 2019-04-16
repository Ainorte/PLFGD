package unice.plfgd.server.handler;

import org.junit.Before;
import org.junit.Test;
import unice.plfgd.common.action.DevinerCheckDrawAction;
import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.data.packet.DevinerFormeResult;
import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.forme.forme.Point;
import unice.plfgd.common.forme.generation.GenerationFormes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class DevinerCheckDrawHandlerTest {
	private static final int DEFAULT_RATIO_SIZE = 100;
	/*
	 * Test checks:
	 * - No Forme > Should give win
	 * - 1 Forme > Should give win
	 * - 2 Forms created, 1 forme given > Shouldn't give win
	 */

	private DevinerCheckDrawAction action;
	private UserStore store;
	private List<List<Point>> toGuess;

	@Before
	public void initDatas() {
		action = new DevinerCheckDrawAction(null);
		store = new UserStore();
		toGuess = new ArrayList<>() {{
			add(GenerationFormes.generateEnumForme(
				Forme.RECTANGLE,
				DEFAULT_RATIO_SIZE,
				DEFAULT_RATIO_SIZE
			));
		}};
		store.addOrReplaceData("formes", new DevinerFormeResult(new ArrayList<>() {{
			add(Forme.RECTANGLE);
		}}));
	}

	// With logic, the same forme as generated should be matched when trying to guess.
	@Test
	public void testOneForme() {
		var res = action.run(store, new Draw(
			toGuess, DEFAULT_RATIO_SIZE, DEFAULT_RATIO_SIZE
		));

//		assertTrue(res.getHasWon());
	}

	@Test
	public void testOneInvalidForme() {
		var forme = GenerationFormes.generateEnumForme(
			Forme.RECTANGLE,
			DEFAULT_RATIO_SIZE,
			DEFAULT_RATIO_SIZE
		);
		store.addOrReplaceData("formes", new DevinerFormeResult(new ArrayList<>() {{
			add(Forme.RECTANGLE);
		}}));
		Collections.reverse(forme);
		var res = action.run(store, new Draw(
			new ArrayList<>() {{
				add(forme);
			}}, DEFAULT_RATIO_SIZE, DEFAULT_RATIO_SIZE
		));

		//assertTrue(res.getHasWon());
	}

	@Test
	public void testTwoFormes() {
		store.addOrReplaceData("formes", new DevinerFormeResult(new ArrayList<>() {{
			add(Forme.RECTANGLE);
			add(Forme.CIRCLE);
		}}));

		var res = action.run(store, new Draw(
			toGuess, DEFAULT_RATIO_SIZE, DEFAULT_RATIO_SIZE
		));

		//assertFalse(res.getHasWon());
	}
}
