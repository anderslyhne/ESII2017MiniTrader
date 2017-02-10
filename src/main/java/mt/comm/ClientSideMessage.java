package mt.comm;

import mt.Order;

/**
 * Messages sent from the server to be recevied and processed by clients. A message contains 
 * a type (enum), and, depending on the type, either an {@link mt.Order} or an error message (a String).  
 *
 * @author alc
 */
public interface ClientSideMessage {
	/**
	 * Message types.
	 */
	public enum Type { 
		/**
		 * The message contains a new order or an update to an existing order. 
		 */
		ORDER,
		
		/**
		 * An error occurred.
		 */
		ERROR };
		
				
	/**
	 * Gets the type of the message.
	 * 
	 * @return type of the message.
	 */
	public Type getType();

	/**
	 * If message type is {@link Type#ORDER}, the method is a getter for the order.
	 * 
	 * @return the {@link Order} in the message.
	 */
	public Order getOrder();

	/**
	 * If the type of the message is {@link Type#ERROR}, the method returns the error message. 
	 *
	 * @return error message.
	 */
	public String getError();

}
