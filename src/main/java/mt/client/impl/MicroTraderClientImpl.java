package mt.client.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import mt.client.MicroTraderClient;
import mt.client.Session;
import mt.client.ui.MicroTraderClientUI;
import mt.comm.ClientComm;
import mt.comm.impl.ClientCommImpl;

public class MicroTraderClientImpl implements MicroTraderClient {
	
	public static void main(String args[]) {
		ClientComm clientComm = new ClientCommImpl();
		MicroTraderClient client = new MicroTraderClientImpl();
		client.start(clientComm);
		Logger.getLogger(MicroTraderClientImpl.class.getName()).log(Level.INFO, "This is the end...");
	}

	@Override
	public void start(ClientComm clientComm) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MicroTraderClientUI.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MicroTraderClientUI.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MicroTraderClientUI.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MicroTraderClientUI.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		}

		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Starting the user interface.");
		MicroTraderClientUI ui = new MicroTraderClientUI();
		Session.clientComm = clientComm;
		ui.setVisible(true);

		try {
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Waiting for client to quit.");
			this.waitObject(ui);
		} catch (InterruptedException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}

		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Leaving the start method.");
	}

	public void waitObject(MicroTraderClientUI object) throws InterruptedException {
		synchronized (object) {
			object.wait();
		}
	}

}
