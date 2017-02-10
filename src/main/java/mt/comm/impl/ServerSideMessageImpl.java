package mt.comm.impl;

import java.io.Serializable;

import mt.Order;
import mt.comm.ServerSideMessage;

public class ServerSideMessageImpl implements ServerSideMessage, Serializable {
	/**
	 * Default serial version id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Message type.
	 */
	private Type type;

	/**
	 * Sender nickname.
	 */
	private String nickname;

	/**
	 * Order (sell/buy) to be sent to the server.
	 */
	private Order order;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            Type of the message.
	 * 
	 * @param nickname
	 *            The sender nickname.
	 * 
	 * @param order
	 *            The order (sell/buy) to be sent.
	 */
	public ServerSideMessageImpl(Type type, String nickname, Order order) {
		super();
		this.type = type;
		this.nickname = nickname;
		this.order = order;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getSenderNickname() {
		return nickname;
	}

	@Override
	public Order getOrder() {
		return order;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(final String nickname) {
		this.nickname = nickname;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	public void setOrder(final Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		final StringBuilder log = new StringBuilder();
		log.append("ServerSideMessage: [Type: ").append(type.toString()).append("] ");
		log.append("[Nickname: ").append(nickname).append("]");

		if (order != null) {
			log.append(" [Order: ").append(order).append("]");
		}
		return log.toString();
	}
}
