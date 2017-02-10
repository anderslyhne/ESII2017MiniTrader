package mt;

import java.io.Serializable;

/**
 * An order can either be a buy order or a sell order of a certain number of units, of a certain stock at a certain price. 
 * Orders are created by clients and then sent to the server where they are assigned a unique ID (serverOrderID). The server
 * sends new orders and updates to existing orders to all clients.
 * 
 * @author alc
 */


public class Order implements Serializable {
	/**
	 * Nickname of the client who issued the order.
	 */
	private String   nickname;
	
	/**
	 * The name of the stock.
	 */
	private String   stock;
	
	/**
	 * Number of units 
	 */
	private int      numberOfUnits;
	
	/**
	 * Price per unit.
	 */
	private double   pricePerUnit;
	
	/**
	 * 
	 */
	private boolean  isBuyOrder;

	/**
	 * 
	 */
	private int      serverOrderID;
	
	/**
	 * Creates a buy order for a certain number of units of a stock at a certain price per unit.
	 * 
	 * @param nickname      Nickname of the client issuing the order
	 * @param stock         Name of the stock.
	 * @param numberOfUnits Number of units of the stock.
	 * @param pricePerUnit  Price per unit.
	 * 
	 * @return a new buy order.
	 */
	public static Order createBuyOrder(String nickname, String stock, int numberOfUnits, double pricePerUnit) {
		return new Order(nickname, true, stock, numberOfUnits, pricePerUnit);
	}
	

	/**
	 * Creates a sell order for a certain number of units of a stock at a certain price per unit.
	 * 
	 * @param nickname      Nickname of the client issuing the order
	 * @param stock         Name of the stock.
	 * @param numberOfUnits Number of units of the stock.
	 * @param pricePerUnit  Price per unit.
	 * 
	 * @return a new sell order.
	 */
	public static Order createSellOrder(String nickname, String stock, int numberOfUnits, double pricePerUnit) {
		return new Order(nickname, false, stock, numberOfUnits, pricePerUnit);
	}
	
	
	private Order(String nickname, boolean isBuyOrder, String stock, int numberOfUnits, double pricePerUnit
			) {
		super();
	
		this.nickname      = nickname;
		this.stock         = stock;
		this.numberOfUnits = numberOfUnits;
		this.pricePerUnit  = pricePerUnit;
		this.isBuyOrder    = isBuyOrder;
	}

	public int getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(int units) {
		this.numberOfUnits = units;
	}

	public String getNickname() {
		return nickname;
	}

	public String getStock() {
		return stock;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}
	
	public boolean isBuyOrder() {
		return isBuyOrder;
	}
	
	public boolean isSellOrder() {
		return !isBuyOrder;
	}
	
	public void setServerOrderID(int id) {
		serverOrderID = id;
	}	
	
	public int getServerOrderID() {
		return serverOrderID;
	}
	
	public String toString() {
		return (isSellOrder() ? "sell" : "buy") + " " + stock + ", " + numberOfUnits + " units at " + pricePerUnit + " EUR/unit (nickname: " + nickname + ", serverID: " + serverOrderID + ")"; 
	}
	
	/**
	 * In order to make tests we had to override this method.
	 * This change doesen't change it's behavior in the context of
	 * this project.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Order){
			Order o = (Order) obj;
			return o.getNickname().equals(nickname) && o.getNumberOfUnits() == numberOfUnits && o.getPricePerUnit() == pricePerUnit && o.getStock().equals(stock);
		}
		return super.equals(obj);
	}
}
