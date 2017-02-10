package mt.comm;

import mt.Order;

/**
 * Messages sent from clients to the server. A message contains a type (enum), the nickname of the sender (String), 
 * and, depending on the message type, a new order. 
 * <p>
 * 
 * @author alc
 */

public interface ServerSideMessage {
	public enum Type { 
		/**
		 * A client issues a new order.
		 */
		NEW_ORDER, 

		/**
		 * A new client connected.
		 */
		CONNECTED,

		/**
		 * A client disconnected.
		 */
		DISCONNECTED
	};
	
	/**
	 * Gets the type of the message.
	 * 
	 * @return type of the message.
	 */
	public Type   getType();

	/**
	 * Gets the sender nickname.
	 * 
	 * @return sender's nickname.
	 */
	public String getSenderNickname();

	/**
	 * Gets the order in the message.
	 * 
	 * @return order.
	 */
	public Order getOrder();
}
