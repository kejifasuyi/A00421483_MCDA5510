package com.kg.mcda5510;

import java.sql.Connection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		Scanner in = new Scanner(System.in);
		MySQLAccess dao = new MySQLAccess();
		try {
			// Connection connection = getInstance();

			ConnectionFactory factory = new ConnectionFactory();
			Connection connection = factory.getConnection("mySQLJDBC");

			int user_input;
			Transaction t = new Transaction();

			System.out.println("What transaction would you like to perform today?\n1. Create New \n2. Update \n3. Delete \n4. View");
			user_input = in.nextInt();

			if (user_input == 1) {
				t = dao.createTrxns();
				dao.createTransaction(connection, t);
			}

			else if (user_input == 2) {
				dao.updateTransaction(connection);
			}

			else if (user_input == 3) {
				System.out.println("Enter the ID for the row you want to delete:");
				int trxnID  = in.nextInt();
				
				try {
					dao.removeTransaction(connection, trxnID);
				} catch (Exception e) {
					Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
					e.printStackTrace();
				}
			}

			else if (user_input == 4) {
				System.out.println("Enter the ID for the row you want to view:");
				int trxnID  = in.nextInt();
				
				try {
					dao.getTransaction(connection, trxnID);
				} catch (Exception e) {
					Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
					e.printStackTrace();
				}
			} else {
				System.out.println("Enter a valid request please...");
			}

			if (connection != null) {
				connection.close();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		in.close();
	}
}
