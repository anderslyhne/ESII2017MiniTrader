package mt.client;

import mt.comm.ClientComm;

/**
 * 
 * The MicroTraderClient should use the {@link ClientComm} interface to
 * communicate with the server.
 * 
 * @author alc
 *
 */

public interface MicroTraderClient {

	/**
	 * Should create the user interface, use the {@link ClientComm} to establish
	 * a connection to the server, send new {@link mt.Order}s to the server, and
	 * receive updates from the server. The method should only return when the
	 * client quits.
	 * 
	 * @param clientComm
	 *            Object for communicating with the server.
	 */
	public void start(ClientComm clientComm);
}
