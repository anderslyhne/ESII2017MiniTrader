package mt.test;

import mt.Order;
import mt.comm.ServerComm;
import mt.comm.ServerSideMessage;
import mt.comm.ServerSideMessage.Type;
import mt.server.MicroServer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test Class for MicroServer
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class MicroServerTest {
	
	private MicroServer ms;
	
	@Mock
	private ServerComm serverComm;
	
	@Mock
	private ServerSideMessage msg1;
	
	@Mock
	private ServerSideMessage msg2;
	
	@Mock
	private ServerSideMessage msg3;
	
	@Mock
	private ServerSideMessage msg4;
	
	@Mock
	private ServerSideMessage msg5;
	
	@Mock
	private ServerSideMessage msg6;
	
	@Mock
	private ServerSideMessage msg7;
	
	@Mock
	private ServerSideMessage msg8;
	
	@Mock
	private ServerSideMessage msg9;
	
	@Mock
	private ServerSideMessage msg10;
	
	@Mock
	private ServerSideMessage msg11;
	
	@Before
	public void setup(){
		ms = new MicroServer();
		
		when(msg1.getType()).thenReturn(Type.CONNECTED);
		when(msg1.getOrder()).thenReturn(null);
		when(msg1.getSenderNickname()).thenReturn("userA");
		
		when(msg2.getType()).thenReturn(Type.NEW_ORDER);
		when(msg2.getOrder()).thenReturn(Order.createSellOrder("userA", "MSFT", 10, 20.0));
		when(msg2.getSenderNickname()).thenReturn("userA");
		
		when(msg3.getType()).thenReturn(Type.CONNECTED);
		when(msg3.getOrder()).thenReturn(null);
		when(msg3.getSenderNickname()).thenReturn("userB");
		
		when(msg4.getType()).thenReturn(Type.NEW_ORDER);
		when(msg4.getOrder()).thenReturn(Order.createBuyOrder("userB", "MSFT", 5, 21.0));
		when(msg4.getSenderNickname()).thenReturn("userB");
		
		when(msg5.getType()).thenReturn(Type.DISCONNECTED);
		when(msg5.getOrder()).thenReturn(null);
		when(msg5.getSenderNickname()).thenReturn("userA");
		
		when(msg6.getType()).thenReturn(Type.DISCONNECTED);
		when(msg6.getOrder()).thenReturn(null);
		when(msg6.getSenderNickname()).thenReturn("userB");
		
		when(msg7.getType()).thenReturn(null);
		when(msg7.getOrder()).thenReturn(null);
		when(msg7.getSenderNickname()).thenReturn(null);
		
		when(msg8.getType()).thenReturn(Type.NEW_ORDER);
		when(msg8.getOrder()).thenReturn(Order.createBuyOrder("userB", "ISCTE", 15, 21.0));
		when(msg8.getSenderNickname()).thenReturn("userB");
		
		when(msg9.getType()).thenReturn(Type.NEW_ORDER);
		when(msg9.getOrder()).thenReturn(Order.createSellOrder("userA", "ISCTE", 10, 20.0));
		when(msg9.getSenderNickname()).thenReturn("userA");	
	
		when(msg10.getType()).thenReturn(Type.NEW_ORDER);
		when(msg10.getOrder()).thenReturn(null);
		when(msg10.getSenderNickname()).thenReturn("userA");	
		
		
	}
	
	@After
	public void tearDown(){
		ms = null;
		serverComm = null;
	}

	@Test
	public void testMicroServer() throws Exception {
		ms = null;
		ms = new MicroServer();
		Assert.assertNotNull(ms);
	}

	@Test
	public void testStart() throws Exception {
		when(serverComm.getNextMessage()).thenReturn(msg1).thenReturn(msg2).thenReturn(msg3).thenReturn(msg4).thenReturn(msg5).thenReturn(msg6).thenReturn(null);
		
		ms.start(serverComm);
		
		verify(serverComm, atLeastOnce()).sendOrder("userB", Order.createSellOrder("userA", "MSFT", 5, 20.0) );
		verify(serverComm, atLeastOnce()).sendOrder("userB", Order.createBuyOrder("userB", "MSFT", 0, 21.0) );
	}
	
	@Test
	public void testStart2() throws Exception {
		when(serverComm.getNextMessage()).thenReturn(msg2).thenReturn(null);
		
		ms.start(serverComm);
		
		verify(serverComm, atLeastOnce()).sendError(msg2.getSenderNickname(), "The user " + msg2.getSenderNickname() + " is not connected.");
	}
	
	@Test
	public void testStart3() throws Exception {
		when(serverComm.getNextMessage()).thenReturn(msg7).thenReturn(null);
		
		ms.start(serverComm);
		
		verify(serverComm, atLeastOnce()).sendError(null, "Type was not recognized");
	}
	
//	@Test
//	public void testStart4() throws Exception {		
//	when(serverComm.getNextMessage()).thenReturn(msg1).thenReturn(msg10).thenReturn(null);
//		
//		ms.start(serverComm);
//		
//		verify(serverComm, atLeastOnce()).sendError(msg10.getSenderNickname(), "There was no order in the message");
//	}
	
	@Test
	public void testStartProcessSellOrder() throws Exception {
		when(serverComm.getNextMessage()).thenReturn(msg3).thenReturn(msg8).thenReturn(msg1).thenReturn(msg9).thenReturn(null);
		
		ms.start(serverComm);
		verify(serverComm, atLeastOnce()).sendOrder("userB",Order.createBuyOrder("userB", "ISCTE", 5, 21.0) );
		
	}
	
	@Test
	public void testStartProcessBuyOrder() throws Exception {		
		when(serverComm.getNextMessage()).thenReturn(msg1).thenReturn(msg2).thenReturn(msg3).thenReturn(msg4).thenReturn(msg5).thenReturn(msg6).thenReturn(null);
		ms.start(serverComm);
		
		verify(serverComm, atLeastOnce()).sendOrder("userA", Order.createSellOrder("userA", "MSFT", 5, 20.0));
	}
	
	@Test
	public void testUserAlreadyConnected() throws Exception {		
		when(serverComm.getNextMessage()).thenReturn(msg1).thenReturn(msg1).thenReturn(null);
		ms.start(serverComm);
		
		verify(serverComm, atLeastOnce()).sendError(msg1.getSenderNickname(), "The user " + msg1.getSenderNickname() + " is already connected.");
	}
	
//	@Test
//	public void testProcessUserDisconnected() throws Exception {		
//		when(serverComm.getNextMessage()).thenReturn(msg1).thenReturn(msg2).thenReturn(msg3).thenReturn(msg4).thenReturn(msg8).thenReturn(msg9).thenReturn(msg10).thenReturn(msg5).thenReturn(msg6).thenReturn(null);
//		ms.start(serverComm);
//		
//		verify(serverComm, atLeastOnce()).sendOrder("userA", Order.createBuyOrder("userB", "ISCTE", 5, 21.0));
//	}
}
