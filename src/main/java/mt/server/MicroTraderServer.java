package mt.server;

import mt.comm.ServerComm;

/**
 * The server is responsible for matching orders from clients and for keeping clients up to date with unfulfilled orders.
 * 
 * @author alc
 *
 */

public interface MicroTraderServer {
	/**
	 * Starts the server. Should only exit when the server quits.
	 * 
	 * @param serverComm the object through which all communication with clients should take place.
	 */
	
	public void start(ServerComm serverComm);
}
