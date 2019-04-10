package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import org.junit.Before;
import org.junit.Test;
import unice.plfgd.common.net.Packet;

import static org.junit.Assert.assertEquals;

public class HandlerTest {

	private Handler<?> handler;

	@Before
	public void setUp() throws Exception {
		handler = new TestHandler();
	}

	@Test
	public void getGenericTypeClass() {
		assertEquals(handler.getGenericTypeClass(), TestPacket.class);
	}

	private class TestHandler extends Handler<TestPacket> {

		@Override
		public void onData(SocketIOClient client, TestPacket data, AckRequest ackSender) throws Exception {
			//Nothing
		}
	}

	private class TestPacket extends Packet {
		//Nothing
	}
}
