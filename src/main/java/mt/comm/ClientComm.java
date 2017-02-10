package mt.comm;

import java.io.IOException;
import java.net.UnknownHostException;

import mt.Order;

/**
 * Communication interface for MicroTrader clients. MicroTrader clients must 
 * communicate with a MicroTrader server exclusively through this interface.
 * 
 * 
 * @author alc
 * @version 1
 */

public interface ClientComm {

	/**
	 * Establish a connection to a MicroTrader server. This method should be the first one called by 
	 * the client as no other operation (expect for {@link #isConnected()}) can be performed before
	 * a connection to the server has been established.
	 * 
	 * @param host                    Address of the server. For instance "localhost", "192.168.1.1", and or similar.
	 * @param nickname                The nickname of the user.
	 * @throws UnknownHostException   Thrown if the host cannot be found.
	 * @throws IOException            Thrown if a connection cannot be established.
	 */
	public void connect(String host, String nickname) throws UnknownHostException, IOException;

	/**
	 * Check if currently connected to a host.
	 * 
	 * @return <b>true</b> if currently connected to a host, <b>false</b> otherwise.
	 */
	public boolean isConnected();

	/**
	 * Disconnects from the server. Any sockets should be closed immediately. 
	 * Pending orders are not guaranteed to be delivered before disconnecting.
	 */	
	public void disconnect();
		
	/**
	 * Get the next message received from the server. If no message 
	 * is has been received, the method blocks until a message has 
	 * been received. If no message will ever be received, null is returned.
	 * 
	 * @return The next message sent by the server to the client or <b>null</b> 
	 *         if no message will ever be received (disconnected from server).
	 */
	public ClientSideMessage getNextMessage();
	
	/** 
	 * Checks if a message from the server is pending. If {@link #hasNextMessage()} returns true, a call to {@link #getNextMessage} 
	 * will return immediately with the oldest message in the queue. If {@link #hasNextMessage()} returns false, a call to {@link #getNextMessage} 
	 * will block until a message has been received.
	 * 
	 * @return <b>true</b> if a message from the server is currently pending, otherwise <b>false</b>.
	 */
	public boolean hasNextMessage();
	
	/**
	 * Send an order to the server which should then process the order.
	 * 
	 * @param order The MicroTrader order to send.
	 */
	public void sendOrder(Order order);
}
