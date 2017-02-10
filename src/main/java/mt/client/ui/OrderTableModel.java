package mt.client.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.swing.table.DefaultTableModel;

import mt.Order;

public class OrderTableModel extends DefaultTableModel {

	public OrderTableModel(List<Order> orders) {
		addColumn("Id");
		addColumn("Nickname");
		addColumn("Stock");
		addColumn("Units");
		addColumn("Price");
		addColumn("Type");

		for (Order order : orders) {
			List<Object> row = new LinkedList<>();

			row.add(order.getServerOrderID());
			row.add(order.getNickname());
			row.add(order.getStock());
			row.add(String.format(Locale.ENGLISH, "%,d", order.getNumberOfUnits()));
			row.add(String.format(Locale.ENGLISH, "%,.2f", order.getPricePerUnit()));
			row.add(order.isBuyOrder() ? "Buy" : "Sell");

			addRow(row.toArray());
		}

	}

}