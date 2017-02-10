package mt.client.controller;

import mt.client.exception.AuthenticationException;
import mt.client.exception.ConnectionClosedException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mt.Order;
import mt.client.Session;
import mt.comm.ClientSideMessage;

/**
 * Class responsible for keeping the business logic for the Micro Trader User
 * Interface.
 */
public class Controller {

	/**
	 * Asks to the ClientComm to establish a connection to a MicroTrader server.
	 *
	 * @param host
	 *            Address of the server. For instance "localhost",
	 *            "192.168.1.1", and or similar.
	 * @param nickname
	 *            The nickname of the user.
	 * @throws UnknownHostException
	 *             Thrown if the host cannot be found.
	 * @throws IOException
	 *             Thrown if a connection cannot be established.
	 */
	public void connect(String host, String nickname) throws UnknownHostException, IOException {
		try {
			Session.clientComm.connect(host, nickname);
			Session.loggedUser = nickname;
		} catch (UnknownHostException uhe) {
			throw new UnknownHostException(String.format("Host '%s' not found", host));
		} catch (IOException ex) {
			throw new IOException("A connection could not be established.");
		}
	}

	/**
	 * Check if an user is currently connected to a host.
	 *
	 * @return <b>true</b> if currently connected to a host, <b>false</b>
	 *         otherwise.
	 */
	public boolean isConnected() {
		//TODO - Validar
		return !Session.loggedUser.isEmpty();
	}

	/**
	 * Send the disconnects order to the ClientComm.
	 */
	public void disconnect() {
		Session.loggedUser = "";
		Session.clientComm.disconnect();
	}

	/**
	 * Identify the currently logged user.
	 *
	 * @return the name of the logged user
	 */
	public String getLoggedUser() {
		return Session.loggedUser;
	}

	/**
	 * Asks the ClientComm interface to send an order to the server.
	 *
	 * @param order
	 *            The MicroTrader order to send.
	 */
	public void sendOrder(Order order) throws Exception {
		if (Session.clientComm.isConnected()) {
			Session.clientComm.sendOrder(order);
		} else {
			throw new Exception("You're not connected to any server.");
		}
	}

	public void sendBatchOrders() throws Exception {
		if (Session.clientComm.isConnected()) {
			for (int i = 1; i < 10; i++) {
				if (i % 2 == 0) {
					sendOrder(Order.createBuyOrder(getLoggedUser(), "AUTO#" + i, i, i));
				} else {
					sendOrder(Order.createSellOrder(getLoggedUser(), "AUTO#" + i, i, i));
				}
			}

		} else {
			throw new Exception("You're not connected to any server.");
		}
	}

	/**
	 * Browse the orders pending in the ClientComm interface.
	 *
	 * @throws Exception
	 *             Thrown if the user was disconnected from the server or if
	 *             there is some error message.
	 */
	public void browseMessages() throws Exception {
		while (Session.clientComm.hasNextMessage()) {
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Processing new messages");
			ClientSideMessage message = Session.clientComm.getNextMessage();
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Message received " + message);
			if (message == null) {
				Session.loggedUser = "";
				throw new Exception("You have been disconnected from the server.");
			} else if (message.getType() == ClientSideMessage.Type.ORDER) {
				int index = -1;

				for (Order order : Session.orders) {
					if (order.getServerOrderID() == message.getOrder().getServerOrderID()) {
						Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order found:" + order);
						index = Session.orders.indexOf(order);
					}
				}

				if (index != -1) {
					if (message.getOrder().getNumberOfUnits() == 0) {
						Logger.getLogger(this.getClass().getName()).log(Level.INFO,
								"Order fullfiled:" + message.getOrder());
						Session.orders.remove(index);
					} else {
						Logger.getLogger(this.getClass().getName()).log(Level.INFO,
								"Order updated:" + message.getOrder());
						Session.orders.get(index).setNumberOfUnits(message.getOrder().getNumberOfUnits());
					}
				} else if (message.getOrder().getNumberOfUnits() != 0) {
					Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order added:" + message.getOrder());
					Session.orders.add(message.getOrder());
					if (message.getOrder().getNickname().equalsIgnoreCase(Session.loggedUser)) {
						Session.history.add(message.getOrder());
					}
				}
			} else if (message.getType() == ClientSideMessage.Type.ERROR) {
				if (message.getError().matches("The nickname '[\\w]*' is already in use and connected.")) {
					this.disconnect();
					throw new AuthenticationException(message.getError());
				} else if (message.getError().matches("The connection for client '[\\w]*' was closed by the server.") 
						||message.getError().matches("ConnectTimerLimit")) {
					this.disconnect();
					throw new ConnectionClosedException(message.getError());
				}

				throw new Exception(message.getError());

			}
		}
	}

}
