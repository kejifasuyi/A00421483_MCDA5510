package com.kg.mcda5510;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kg.mcda5510.dao.MySQLAccess;
import com.kg.mcda5510.entity.Transaction;

public class Assignment3 {


	public static void main(String[] args) {
		System.setProperty("java.util.logging.config.file", "./logging.properties");
		Scanner in = new Scanner(System.in);
		MySQLAccess dao = new MySQLAccess();
		try {

			int user_input;
			Transaction t = new Transaction();

			System.out.println(
					"What transaction would you like to perform today?\n1. Create New \n2. Update \n3. Delete \n4. View");
			user_input = in.nextInt();

			if (user_input == 1) {
				t = dao.createTrxns();
				dao.createTransaction(t);
			}

			else if (user_input == 2) {
				System.out.println("Enter the ID for the row you want to edit:");
				int oldID = in.nextInt();
				 t = dao.createTrxns();
				dao.updateTransaction(t, oldID);
			}

			else if (user_input == 3) {
				System.out.println("Enter the ID for the row you want to delete:");
				int trxnID = in.nextInt();

				try {
					dao.removeTransaction(trxnID);
				} catch (Exception e) {
					Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
					e.printStackTrace();
				}
			}

			else if (user_input == 4) {
				System.out.println("Enter the ID for the row you want to view:");
				int trxnID = in.nextInt();

				try {
					t = dao.getTransaction(trxnID);
					System.out.println(t.toString());
				} catch (Exception e) {
					Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
					e.printStackTrace();
				}
			} else {
				System.out.println("Enter a valid request please...");
			}

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		in.close();
	}
}
