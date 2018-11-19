package com.kg.mcda5510;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import com.kg.mcda5510.connect.ConnectionFactory;
import com.kg.mcda5510.connect.MySQLJDBCConnection;
import com.kg.mcda5510.dao.MySQLAccess;
import com.kg.mcda5510.entity.Transaction;

public class Assignment2 {

	public static Connection single_instance;

	public static Connection getInstance() {
		if (single_instance == null) {
			MySQLJDBCConnection dbConnection = new MySQLJDBCConnection();
			single_instance = dbConnection.setupConnection();
		}

		return single_instance;
	}

	public static void main(String[] args) {
		System.setProperty("java.util.logging.config.file", "./logging.properties");
		MySQLAccess dao = new MySQLAccess();
		try {
			// Connection connection = getInstance();

			ConnectionFactory factory = new ConnectionFactory();
			Connection connection = factory.getConnection("mySQLJDBC");

			int user_input;
			Transaction t = new Transaction();

			user_input = Integer
					.valueOf(JOptionPane.showInputDialog("What transaction would you like to perform today?\n"
							+ "1. Create New \n2. Update \n3. Delete \n4. View"));

			if (user_input == 1) {
				t = dao.createTrxns();
				dao.createTransaction(connection, t);
			}

			else if (user_input == 2) {
				dao.updateTransaction(connection);
			}

			else if (user_input == 3) {
				int trxnID = Integer
						.valueOf(JOptionPane.showInputDialog("Enter the ID for the row you want to delete:"));
				try {
					dao.removeTransaction(connection, trxnID);
				} catch (Exception e) {
					Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
					e.printStackTrace();
				}
			}

			else if (user_input == 4) {
				int trxnID = Integer.valueOf(JOptionPane.showInputDialog("Enter the ID for the row you want to view:"));
				try {
					dao.getTransaction(connection, trxnID);
				} catch (Exception e) {
					Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Enter a valid request please...");
			}

//			Transaction trxns = dao.createTrxns();
//			//Collection<Transaction> trxns = dao.getAllTransactions(connection);
//
//			//for (Transaction trxn : trxns) 
//			{
//				trxns.setNameOnCard("New Value");
//				dao.updateTransaction(connection, trxns);
//				
//				System.out.println(trxns.toString());
//			}
//
		if (connection != null) {
				connection.close();
		}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
