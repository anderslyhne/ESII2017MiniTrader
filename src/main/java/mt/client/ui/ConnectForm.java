package mt.client.ui;

import java.io.IOException;

import javax.swing.JOptionPane;

import mt.client.controller.Controller;

/**
 * Called by {@link MicroTraderClientUI} to send the user information in order
 * to connect to a Micro Trader Server.
 */
public class ConnectForm extends javax.swing.JDialog {

	/**
	 * Creates new form ConnectDialog
	 */
	public ConnectForm(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	@SuppressWarnings("unchecked")
	private void initComponents() {

		hostLabel = new javax.swing.JLabel();
		niknameLabel = new javax.swing.JLabel();
		hostTxt = new javax.swing.JTextField();
		nicknameTxt = new javax.swing.JTextField();
		connectBtn = new javax.swing.JButton();
		cancelBtn = new javax.swing.JButton();

		setTitle("Connect");
		setResizable(false);

		hostLabel.setText("Host:");

		niknameLabel.setText("Nickname:");

		connectBtn.setText("Connect");
		connectBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				connectBtnActionPerformed(evt);
			}
		});

		cancelBtn.setText("Cancel");
		cancelBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelBtnActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(6, 6, 6)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(0, 21, Short.MAX_VALUE).addComponent(connectBtn)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(cancelBtn).addGap(18, 18, 18))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(hostLabel, javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(niknameLabel, javax.swing.GroupLayout.Alignment.TRAILING))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addComponent(nicknameTxt).addGap(6, 6,
												6))
										.addGroup(layout.createSequentialGroup().addComponent(hostTxt)
												.addContainerGap()))))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(hostLabel).addComponent(hostTxt, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(niknameLabel).addComponent(nicknameTxt,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(connectBtn).addComponent(cancelBtn))
						.addContainerGap(7, Short.MAX_VALUE)));

		pack();
	}

	private void connectBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_connectBtnActionPerformed
		String errorMessage = "";
		String host = hostTxt.getText().trim();
		String nickname = nicknameTxt.getText().trim();

		if (host.isEmpty()) {
			errorMessage = "Host must be provided.";
		}

		if (nickname.isEmpty()) {
			errorMessage = "Nickname must be provided.";
		}

		if (!errorMessage.isEmpty()) {
			JOptionPane.showMessageDialog(this, errorMessage, "Warning", JOptionPane.WARNING_MESSAGE);
		} else {
			try {
				new Controller().connect(host, nickname);
				setVisible(false);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelBtnActionPerformed
		this.setVisible(false);
	}

	private javax.swing.JButton cancelBtn;
	private javax.swing.JButton connectBtn;
	private javax.swing.JLabel hostLabel;
	private javax.swing.JTextField hostTxt;
	private javax.swing.JTextField nicknameTxt;
	private javax.swing.JLabel niknameLabel;
}