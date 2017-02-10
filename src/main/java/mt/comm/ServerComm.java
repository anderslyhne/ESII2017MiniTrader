package mt.comm;

import mt.Order;

public interface ServerComm {

	/**
	 * Starts the MicroTrader server. The method should setup a server socket and begin to accept connections.
	 * This method must return immediately after a server socket has been setup, and another thread should
	 * started to listen for connections.
	 */
	public void start();
	
	/**
	 * Get the next message received from one of the clients. If no message 
	 * is has been received, the method blocks until a message 
	 * is received. If no message will ever be received, <b>null</b> is returned.
	 * 
	 * @return The next message sent by one of the connected clients to the server
	 */
	public ServerSideMessage getNextMessage();
	
	/** 
	 * Checks if a message from a client is pending. If {@link #hasNextMessage()} returns <b>true</b>, a call to {@link #getNextMessage} 
	 * will return immediately with the oldest message in the queue. If {@link #hasNextMessage()} returns <b>false</b>, a call to 
	 * {@link #getNextMessage} will block until a message has been received.
	 * 
	 * @return <b>true</b> if a message from a client is currently pending, otherwise <b>false</b>.
	 */
	public boolean hasNextMessage();

   /**
    * Send an order to a client.
    * 
    * @param receiversNickname nickname of the client to receive the order. If no client with the nickname is currently connected, no order is sent.
    * @param order order to send. 
    */
	public void sendOrder(String receiversNickname, Order order);

	/**
	 * Report an error to a client.
	 * 
	 * @param toNickname nickname of the client.
	 * @param error description of the error.
	 */
	public void sendError(String toNickname, String error);

	/**
	 * Checks if a client with a certain nickname is currently connected.
	 * 
	 * @param nickname nickname of the client to check.
	 * @return <b>true</b> if a client with the nickname is currently connected, otherwise <b>false</b>.
	 */
	public boolean clientIsConnected(String nickname);
	
	/**
	 * Disconnect a client.
	 * 
	 * @param nickname nickname of the client to disconnect.
	 */
	public void disconnectClient(String nickname);
}
