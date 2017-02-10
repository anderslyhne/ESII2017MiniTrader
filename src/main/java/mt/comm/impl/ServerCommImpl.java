package mt.comm.impl;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import mt.Order;
import mt.comm.ServerComm;
import mt.comm.ServerSideMessage;
import mt.comm.common.CommUtils;
import mt.comm.socket.ServerCommSocket;

public class ServerCommImpl implements ServerComm {
	/**
	 * Class responsible for handling sockets and streams between several
	 * clients and this server.
	 */
	private ServerCommSocket serverSocket;

	/**
	 * Stores the messages received from clients.
	 */
	private BlockingQueue<ServerSideMessage> serverMessages;

	/**
	 * Default constructor.
	 */
	public ServerCommImpl() {
		this.serverMessages = new ArrayBlockingQueue<>(CommUtils.BLOCKING_QUEUE_CAPACITY);
	}

	@Override
	public void start() {
		try {
			serverSocket = new ServerCommSocket(this.serverMessages);
		} catch (IOException e) {
			String message = "ServerComm >> An error has occured while starting ServerComm due to: " + e.getMessage();
			System.out.println(message);
			throw new RuntimeException(message, e);
		}

		serverSocket.start();
	}

	@Override
	public ServerSideMessage getNextMessage() {
		ServerSideMessage nextMessage = null;
		try {
			nextMessage = serverMessages.take();
		} catch (InterruptedException e) {
			System.out.println("ServerComm >> An error has thrown while taking a server message due to: " + CommUtils.getCause(e));
		}
		return nextMessage;
	}

	@Override
	public boolean hasNextMessage() {
		return serverMessages.peek() != null;
	}

	@Override
	public void sendOrder(String receiversNickname, Order order) {
		serverSocket.sendOrder(receiversNickname, order);
	}

	@Override
	public void sendError(String toNickname, String error) {
		serverSocket.sendError(toNickname, error);
	}

	@Override
	public boolean clientIsConnected(String nickname) {
		return serverSocket.isClientConnected(nickname);
	}

	@Override
	public void disconnectClient(String nickname) {
		serverSocket.disconnectClient(nickname);
	}
}
