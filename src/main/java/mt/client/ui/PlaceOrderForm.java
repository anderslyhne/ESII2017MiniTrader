package mt.client.ui;

import javax.swing.JOptionPane;
import mt.Order;
import mt.client.controller.Controller;

/**
 * Screen that contains the fields to create a new order.
 *
 */
public class PlaceOrderForm extends javax.swing.JDialog {

	private Controller controller = new Controller();

	/**
	 * Creates new form PlaceOrderForm
	 */
	public PlaceOrderForm(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	@SuppressWarnings("unchecked")
	private void initComponents() {

		buttonGroup2 = new javax.swing.ButtonGroup();
		jPanel1 = new javax.swing.JPanel();
		nicknameLabel = new javax.swing.JLabel();
		stockLabel = new javax.swing.JLabel();
		numberLabel = new javax.swing.JLabel();
		priceLabel = new javax.swing.JLabel();
		operationLabel = new javax.swing.JLabel();
		stockTxt = new javax.swing.JTextField();
		buyRdBtn = new javax.swing.JRadioButton();
		sellRdBtn = new javax.swing.JRadioButton();
		numberOfUnitsTxt = new javax.swing.JTextField();
		pricePerUnitTxt = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		cancelBtn = new javax.swing.JButton();
		okBtn = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Place Order");

		jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		nicknameLabel.setText("Nickname:");

		stockLabel.setText("Stock:");

		numberLabel.setText("Number of units:");

		priceLabel.setText("Price per unit:");

		operationLabel.setText("Operation:");

		buttonGroup2.add(buyRdBtn);
		buyRdBtn.setText("Buy");

		buttonGroup2.add(sellRdBtn);
		sellRdBtn.setText("Sell");

		numberOfUnitsTxt.setColumns(5);

		jLabel1.setText(controller.getLoggedUser());

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout
						.createSequentialGroup().addGroup(jPanel1Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout
										.createSequentialGroup().addContainerGap()
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(nicknameLabel).addComponent(numberLabel).addComponent(
														stockLabel)
												.addComponent(priceLabel))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(stockTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 240,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(jPanel1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING,
																false)
														.addComponent(pricePerUnitTxt,
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(numberOfUnitsTxt,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE, 130,
																Short.MAX_VALUE))
												.addComponent(jLabel1)))
								.addGroup(jPanel1Layout.createSequentialGroup().addGap(46, 46, 46)
										.addComponent(operationLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(buyRdBtn)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(sellRdBtn)))
						.addContainerGap(46, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap(11, Short.MAX_VALUE)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(nicknameLabel).addComponent(jLabel1))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(stockLabel).addComponent(stockTxt, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(numberLabel).addComponent(numberOfUnitsTxt,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(priceLabel).addComponent(pricePerUnitTxt,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(operationLabel).addComponent(buyRdBtn).addComponent(sellRdBtn))));

		cancelBtn.setText("Cancel");
		cancelBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelBtnActionPerformed(evt);
			}
		});

		okBtn.setText("Ok");
		okBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				okBtnActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup().addComponent(okBtn)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 86,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));

		layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] { cancelBtn, okBtn });

		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(cancelBtn).addComponent(okBtn))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
	}

	private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {
		this.setVisible(false);
	}

	private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {
		String message = "";
		String stock = stockTxt.getText().trim();
		long numberOfUnits = 0;
		double pricePerUnit = 0;

		if (stock.isEmpty()) {
			message = (message.isEmpty() ? "" : message + "\n") + "Stock must be provided.";
		}

		if (numberOfUnitsTxt.getText().isEmpty()) {
			message = (message.isEmpty() ? "" : message + "\n") + "Number of units must be provided.";
		} else {
			try {
				numberOfUnits = Long.valueOf(numberOfUnitsTxt.getText().trim());
				if (numberOfUnits <= 0) {
					message = (message.isEmpty() ? "" : message + "\n") + "Number of units must be greater than 0.";
				} else if (numberOfUnits > Integer.MAX_VALUE) {
					message = (message.isEmpty() ? "" : message + "\n") + "Number of units must be less than "
							+ Integer.MAX_VALUE + ".";
				}
			} catch (NumberFormatException e) {
				message = (message.isEmpty() ? "" : message + "\n") + "Number of units must be an integer";
			}
		}

		if (pricePerUnitTxt.getText().isEmpty()) {
			message = (message.isEmpty() ? "" : message + "\n") + "Price per unit must be provided.";
		} else {
			try {
				pricePerUnit = Double.valueOf(pricePerUnitTxt.getText().trim());
				if (pricePerUnit <= 0) {
					message = (message.isEmpty() ? "" : message + "\n") + "Price per unit must be greater than 0.";
				} else if (pricePerUnit > Double.MAX_VALUE) {
					message = (message.isEmpty() ? "" : message + "\n") + "Price per unit must be less than "
							+ Integer.MAX_VALUE + ".";
				}
			} catch (NumberFormatException e) {
				message = (message.isEmpty() ? "" : message + "\n") + "Price per unit must be a number";
			}
		}

		if ((!buyRdBtn.isSelected() && (!sellRdBtn.isSelected()))) {
			message = (message.isEmpty() ? "" : message + "\n") + "Operation must be provided.";
		}

		if (!message.isEmpty()) {
			JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
		} else {
			try {
				if (buyRdBtn.isSelected()) {
					controller.sendOrder(
							Order.createBuyOrder(controller.getLoggedUser(), stock, (int) numberOfUnits, pricePerUnit));
				} else if (sellRdBtn.isSelected()) {
					controller.sendOrder(Order.createSellOrder(controller.getLoggedUser(), stock, (int) numberOfUnits,
							pricePerUnit));
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}

			this.setVisible(false);
		}

	}

	private javax.swing.ButtonGroup buttonGroup2;
	private javax.swing.JRadioButton buyRdBtn;
	private javax.swing.JButton cancelBtn;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JLabel nicknameLabel;
	private javax.swing.JLabel numberLabel;
	private javax.swing.JTextField numberOfUnitsTxt;
	private javax.swing.JButton okBtn;
	private javax.swing.JLabel operationLabel;
	private javax.swing.JLabel priceLabel;
	private javax.swing.JTextField pricePerUnitTxt;
	private javax.swing.JRadioButton sellRdBtn;
	private javax.swing.JLabel stockLabel;
	private javax.swing.JTextField stockTxt;

}
