package mt.comm.socket;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

import mt.Order;
import mt.comm.ClientSideMessage;
import mt.comm.ClientSideMessage.Type;
import mt.comm.ServerSideMessage;
import mt.comm.common.CommUtils;

/**
 * This class is responsible for handling sockets and streams between the server
 * and a client.
 */
public class ClientCommSocket {
	/**
	 * The server socket TCP/IP port used to connect the client to the server.
	 */
	private static final int SERVER_SOCKET_TCP_IP_PORT = 9090;

	/**
	 * The socket for communication between the client and the server.
	 */
	private Socket socket;

	/**
	 * The input stream used to received client messages sent from server.
	 */
	private ObjectInputStream in;

	/**
	 * The output stream used to sent server messages to server.
	 */
	private ObjectOutputStream out;

	/**
	 * The server host in which the server socket is available to receive new
	 * connections.
	 */
	private String host;

	/**
	 * The client nickname.
	 */
	private String nickname;

	/**
	 * Indicates if the client is still connected.
	 */
	private boolean isConnected;

	/**
	 * Thread responsible for handling the client messages received from the
	 * server.
	 */
	private ClientCommThread clientCommThread;

	/**
	 * Reference to the queue used to store the messages received from the
	 * server.
	 */
	private BlockingQueue<ClientSideMessage> clientMessages;

	/**
	 * Constructor.
	 * 
	 * @param host
	 *            The server host in which the server socket is available to
	 *            receive new connections.
	 * @param nickname
	 *            The client nickname.
	 * @param clientMessages
	 *            Reference to the queue used to store the messages received
	 *            from the server.
	 * @throws UnknownHostException
	 *             Thrown if the server host is unknown.
	 * @throws IOException
	 *             Thrown if the socket could not be created.
	 */
	public ClientCommSocket(final String host, final String nickname, final BlockingQueue<ClientSideMessage> clientMessages) throws UnknownHostException, IOException {
		this.host = host;
		this.nickname = nickname;
		this.socket = new Socket(this.host, SERVER_SOCKET_TCP_IP_PORT);
		this.clientMessages = clientMessages;
		System.out.println(String.format("ClientComm >> Connecting '%s' to the server %s:%d...", nickname, this.host, SERVER_SOCKET_TCP_IP_PORT));
	}

	/**
	 * This method is used to create the streams, IN and OUT for the socket
	 * created previously and to send a connected message to the server. It's
	 * also used to create a new thread responsible for handling the client
	 * messages received from the server.
	 */
	public void connect() {
		try {
			System.out.println("ClientComm >> Trying to create the streams IN and OUT...");
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			System.out.println("ClientComm >> The streams IN and OUT were created sucessfully");
		} catch (IOException e) {
			System.out.println("ClientComm >> An error has occurred while creating the streams IN and OUT due to :" + CommUtils.getCause(e));
		}

		if (socket != null && out != null && in != null) {
			System.out.println(String.format("ClientComm >> The client '%s' is now connected to the server", nickname));
			ServerSideMessage serverMsg = CommUtils.createConnectMessage(this.nickname);
			try {
				System.out.println(String.format("ClientComm >> Client '%s' is sending %s", nickname, serverMsg));
				out.writeObject(serverMsg);
				isConnected = true;

				/* Creating a thread to receive client messages from the server. */
				clientCommThread = new ClientCommThread(nickname, socket, in, clientMessages);
				String clientCommThreadName = "ClientCommThread-" + nickname;
				clientCommThread.setName(clientCommThreadName);
				clientCommThread.start();
				System.out.println(String.format("ClientComm >> Starting thread '%s' to handle messages received from server to client '%s'", clientCommThreadName, nickname));
			} catch (IOException e) {
				System.out.println("ClientComm >> An error has occurred due to :" + e.getMessage());
				try {
					CommUtils.releaseResources(out, in, socket);
					clientMessages = null;
				} catch (IOException e1) {
					System.out.println("ClientComm >> An error has occurred while releasing resources due to :" + CommUtils.getCause(e));
				}
			}
		}
	}

	/**
	 * This method is used to send and client order to the server.
	 * 
	 * @param order
	 *            The order to be sent to the server.
	 */
	public void sendOrder(final Order order) {
		try {
			ServerSideMessage message = CommUtils.createOrderMessage(nickname, order);
			
			//Reset output stream due to socket cache
			out.reset();
			
			// Sending the message to the server.
			out.writeObject(message);
			System.out.println(String.format("ClientComm >> Client '%s' is sending %s", nickname, message));
		} catch (IOException e) {
			System.out.println("ClientComm >> An error has thrown while sending a server message due to: " + CommUtils.getCause(e));
		}
	}

	/**
	 * This method is used to disconnect a client from the server and to stop
	 * the started thread that is handling the messages received from server.
	 */
	public void disconnect() {
		if (isConnected) {
			try {
				isConnected = false;
				ServerSideMessage message = CommUtils.createDisconnectMessage(nickname);
				out.writeObject(message);
				System.out.println("ClientComm >> Sending " + message);

				clientCommThread.disconnect();
				CommUtils.releaseResources(out, in, socket);
				System.out.println(String.format("ClientComm >> Client '%s' is disconnected", nickname));
			} catch (IOException e) {
				System.out.println("ClientComm >> An error has thrown while disconnecting '" + nickname + "' due to: " + CommUtils.getCause(e));
			}
		}
	}

	/**
	 * This method is used to validate if the is still connected to the server.
	 * 
	 * @return True if the client still connected otherwise false.
	 */
	public boolean isConnected() {
		if(!clientCommThread.isConnected()){
			isConnected = false;
		}
		return isConnected && clientCommThread.isConnected();
	}
}

/**
 * This class implements the thread which handles the messages received from the
 * server.
 */
class ClientCommThread extends Thread {
	/**
	 * The client nickname.
	 */
	private String nickname;

	/**
	 * Reference to the socket created between the client and the server.
	 */
	private Socket socket;

	/**
	 * Reference to the input stream used to receive messages from server.
	 */
	private ObjectInputStream in;

	/**
	 * Indicates if this thread is still active.
	 */
	private boolean isConnected;

	/**
	 * Reference for the queue used for messages received from the server.
	 */
	private BlockingQueue<ClientSideMessage> clientMessages;

	/**
	 * Constructor.
	 * 
	 * @param nickame
	 *            The client nickname.
	 * @param socket
	 *            Reference to the socket created between the client and the
	 *            server.
	 * @param in
	 *            Reference to the input stream used to receive messages from
	 *            server.
	 * 
	 * @param clientMessages
	 *            Reference for the queue used for messages received from the
	 *            server.
	 */
	public ClientCommThread(final String nickame, final Socket socket, final ObjectInputStream in, final BlockingQueue<ClientSideMessage> clientMessages) {
		this.nickname = nickame;
		this.socket = socket;
		this.in = in;
		this.clientMessages = clientMessages;
	}

	@Override
	public void run() {
		if (socket != null && in != null) {
			this.isConnected = true;
			while (isConnected) {
				try {
					ClientSideMessage message = (ClientSideMessage) in.readObject();
					System.out.println(String.format("ClientComm >> Client '%s' is processing %s", nickname, message));
					clientMessages.put(message);
					if(Type.ERROR.equals(message.getType())){
						System.out.println(String.format("ClientComm >> Client '%s' was not allowed to connect", nickname, message));
						isConnected = false;
					}
				} catch (EOFException | SocketException e) {
					System.out.println(String.format("ClientComm >> The socket for client '%s' has been closed by sever", nickname));
					try {
						clientMessages.put(CommUtils.createErrorMessage(String.format(CommUtils.CONNECTION_CLOSED_BY_SERVER, nickname)));
					} catch (InterruptedException e1) {
						System.out.println("ClientComm >> It was not possible to process a client message due to : " + CommUtils.getCause(e));
					}
					isConnected = false;
				} catch (ClassNotFoundException | IOException | InterruptedException e) {
					System.out.println("ClientComm >> It was not possible to process a client message due to : " + CommUtils.getCause(e));
					isConnected = false;
				}
			}

			try {
				CommUtils.releaseResources(in, socket);
				clientMessages = null;
			} catch (IOException e) {
				System.out.println("ClientComm >> It was not possible to release socket and streams due to : " + CommUtils.getCause(e));
			}
		}
	}

	/**
	 * This method is used to stop the thread.
	 */
	public void disconnect() {
		isConnected = false;
	}
	
	/**
	 * This method is used to validate if this thread is still alive.
	 * 
	 * @return True if the client still connected otherwise false.
	 */
	public boolean isConnected() {
		return isConnected;
	}
}
