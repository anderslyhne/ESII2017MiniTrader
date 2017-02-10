package mt.comm.common;

import java.io.Closeable;
import java.io.IOException;

import mt.Order;
import mt.comm.ClientSideMessage;
import mt.comm.ServerSideMessage;
import mt.comm.ServerSideMessage.Type;
import mt.comm.impl.ClientSideMessageImpl;
import mt.comm.impl.ServerSideMessageImpl;

/**
 * This is an utility class with common methods that are being used within the
 * component networking, either for client and server side.
 */
public final class CommUtils {
	/**
	 * Message used to send to the client when a nickname is already in use and
	 * connected.
	 */
	public static final String USER_IS_ALREADY_CONNECTED = "The nickname '%s' is already in use and connected.";

	/**
	 * Message used to send to the client informing that an automatic trade
	 * behavior was detected.
	 */
	public static final String CONNECTION_CLOSED_BY_SERVER = "The connection for client '%s' was closed by the server.";

	/**
	 * Blocking queue capacity.
	 */
	public static final int BLOCKING_QUEUE_CAPACITY = 10;

	private CommUtils() {
		// Utility class
	}

	/**
	 * This method is used to create a server side message of type connected,
	 * when the ClientComm is started.
	 * 
	 * @param nickname
	 *            The nickname of the client that starts the ClientComm.
	 * @return An object representing a server side message of type connected.
	 */
	public static ServerSideMessage createConnectMessage(String nickname) {
		return new ServerSideMessageImpl(Type.CONNECTED, nickname, null);
	}

	/**
	 * This method is used to create a server side message of type disconnected,
	 * when the ClientComm is disconnected.
	 * 
	 * @param nickname
	 *            The nickname of the client that stops the ClientComm.
	 * @return An object representing a server side message of type
	 *         disconnected.
	 */
	public static ServerSideMessage createDisconnectMessage(String nickname) {
		return new ServerSideMessageImpl(Type.DISCONNECTED, nickname, null);
	}

	/**
	 * This method is used to create a server side message when a new order
	 * (sell, buy) is sent to the server.
	 * 
	 * @param nickname
	 *            The nickname of the client that is sending the new order.
	 * @param order
	 * @return An object representing a server side message of type new order.
	 */
	public static ServerSideMessage createOrderMessage(String nickname, Order order) {
		return new ServerSideMessageImpl(Type.NEW_ORDER, nickname, order);
	}

	/**
	 * This method is used to create a client side message for order (sell, buy)
	 * which is sent to the client.
	 * 
	 * @param order
	 *            Order to be sent.
	 * @return An object representing a client side message of type order.
	 */
	public static ClientSideMessage createOrderMessage(Order order) {
		return new ClientSideMessageImpl(ClientSideMessage.Type.ORDER, order);
	}

	/**
	 * This method is used to create an error client side message which is sent
	 * to the client.
	 * 
	 * @param errorMessage
	 *            Error message to be sent to the client.
	 * @return An object representing an error client side message.
	 */
	public static ClientSideMessage createErrorMessage(String errorMessage) {
		return new ClientSideMessageImpl(ClientSideMessage.Type.ERROR, errorMessage);
	}

	/**
	 * This method is used to release resources such as sockets and streams.
	 * 
	 * @param closables
	 *            Resources to be closed.
	 * @throws IOException
	 *             In an error has occurred while closing resources.
	 */
	public static void releaseResources(Closeable... closables) throws IOException {
		if (closables != null) {
			for (Closeable closeable : closables) {
				if (closeable != null) {
					closeable.close();
				}
			}
		}
	}

	/**
	 * This method is used to validate if a certain string is empty.
	 * 
	 * @param str
	 *            The string to be validated.
	 * @return True if empty, otherwise false.
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * This method is used to retrieve a message from an exception or its cause.
	 * 
	 * @param e
	 *            The exception from which the message should be retrieved.
	 * @return An exception message.
	 */
	public static String getCause(Exception e) {
		String message = e.getMessage();
		if (isEmpty(message)) {
			Throwable cause = e.getCause();
			if (cause != null) {
				message = cause.getMessage();
			}
		}
		return message;
	}
}
