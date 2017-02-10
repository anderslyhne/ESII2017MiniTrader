package mt.comm.impl;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import mt.Order;
import mt.comm.ClientComm;
import mt.comm.ClientSideMessage;
import mt.comm.common.CommUtils;
import mt.comm.socket.ClientCommSocket;

public class ClientCommImpl implements ClientComm {
	/**
	 * Class responsible for handling sockets and streams.
	 */
	private ClientCommSocket clientSocket;

	/**
	 * Stores the messages received from server.
	 */
	private BlockingQueue<ClientSideMessage> clientMessages;

	/**
	 * Default constructor.
	 */
	public ClientCommImpl() {
		super();
	}

	@Override
	public void connect(final String host, final String nickname) throws UnknownHostException, IOException {
		clientMessages = new ArrayBlockingQueue<>(CommUtils.BLOCKING_QUEUE_CAPACITY);
		clientSocket = new ClientCommSocket(host, nickname, clientMessages);
		clientSocket.connect();
	}

	@Override
	public boolean isConnected() {
		return clientSocket != null && clientSocket.isConnected();
	}

	@Override
	public void disconnect() {
		if (isConnected()) {
			clientSocket.disconnect();
			clientMessages.clear();
			clientMessages = null;
		}
	}

	@Override
	public ClientSideMessage getNextMessage() {
		ClientSideMessage nextMessage = null;
		try {
			nextMessage = clientMessages.take();
		} catch (InterruptedException e1) {
			System.out.println("ClientComm >> An error has thrown while taking a client message due to: " + CommUtils.getCause(e1));
		}
		return nextMessage;
	}

	@Override
	public boolean hasNextMessage() {
		return clientMessages != null && clientMessages.peek() != null;
	}

	@Override
	public void sendOrder(final Order order) {
		if (isConnected()) {
			clientSocket.sendOrder(order);
		}
	}
}
