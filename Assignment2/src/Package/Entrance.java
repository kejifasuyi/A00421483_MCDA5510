package Package;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class Entrance {

	public static void main(String[] args) {
		System.setProperty("java.util.logging.config.file", "./logging.properties");
		int user_input;
		MySQLAccess dao = new MySQLAccess();
		Transaction t = new Transaction();
		Operations operations = new Operations();
		Ops o = new Ops();

		user_input = Integer.valueOf(JOptionPane.showInputDialog("What transaction would you like to perform today?\n"
				+ "1. Create New \n2. Update \n3. Delete \n4. View"));

		if (user_input == 1) {
			try {
				Connection connections = dao.setupConnection();
				t = operations.makeTransaction();
				o.createTransaction(t);

				if (connections != null) {
					connections.close();
				}

			} catch (Exception e) {
				Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
				e.printStackTrace();
			}

		}

		else if (user_input == 2) {
			try {
				Connection connections = dao.setupConnection();
				o.updateTransaction(t);

				if (connections != null) {
					connections.close();
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (user_input == 3) {
			int trxnID = Integer.valueOf(JOptionPane.showInputDialog("Enter the ID for the row you want to delete:"));
			try {
				o.removeTransaction(trxnID);
			} catch (Exception e) {
				Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
				e.printStackTrace();
			}
		}

		else if (user_input == 4) {
			int trxnID = Integer.valueOf(JOptionPane.showInputDialog("Enter the ID for the row you want to view:"));
			try {
				o.getTransaction(trxnID);
			} catch (Exception e) {
				Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Enter a valid request please...");
		}

	}
}
