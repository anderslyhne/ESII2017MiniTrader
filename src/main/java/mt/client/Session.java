package mt.client;

import java.util.ArrayList;
import java.util.List;

import mt.Order;
import mt.comm.ClientComm;

/**
 * Reponsible for keep the data used during an user Session.
 */
public class Session {

	public static String loggedUser = "";

	public static ClientComm clientComm = null;

	public static List<Order> orders = new ArrayList<>();

	public static List<Order> history = new ArrayList<>();

}