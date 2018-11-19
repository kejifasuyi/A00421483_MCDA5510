package com.kg.mcda5510.dao;

/**
 * Original source code from 
 * http://www.vogella.com/tutorials/MySQLJava/article.html
 * 
**/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import com.kg.mcda5510.entity.Transaction;


public class MySQLAccess {
	private PreparedStatement preparedStatement = null;


//	public Collection<Transaction> getAllTransactions(Connection connection) {
//		Statement statement = null;
//		ResultSet resultSet = null;
//		Collection<Transaction> results = new ArrayList<Transaction>();
//		// Result set get the result of the SQL query
//		try {
//			// Statements allow to issue SQL queries to the database
//			statement = connection.createStatement();
//			resultSet = statement.executeQuery("select * from assignment2.transaction");
//			results = createTransaction(resultSet);
//
//			if (resultSet != null) {
//				resultSet.close();
//			}
//
//			if (statement != null) {
//				statement.close();
//			}
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			statement = null;
//			resultSet = null;
//		}
//		return results;
//
//	}

//	private Collection<Transaction> createTrxns(ResultSet resultSet) throws SQLException {
//		Collection<Transaction> results = new ArrayList<Transaction>();
//
//		// ResultSet is initially before the first data set
//		while (resultSet.next()) {
//			// It is possible to get the columns via name
//			// also possible to get the columns via the column number
//			// which starts at 1
//			// e.g. resultSet.getSTring(2);
//			Transaction trxn = new Transaction();
//			trxn.setNameOnCard(resultSet.getString("NameOnCard"));
//			trxn.setCardNumber(resultSet.getString("CardNumber"));
//			results.add(trxn);
//
//			// TODO
//			/*
//			 * String ID = resultSet.getString("ID"); String ExpDate =
//			 * resultSet.getString("ExpDate"); String UnitPrice =
//			 * resultSet.getString("UnitPrice"); Integer qty =
//			 * resultSet.getInt("Quantity"); String totalPrice =
//			 * resultSet.getString("TotalPrice"); Date createdOn =
//			 * resultSet.getDate("CreatedOn"); String createdBy =
//			 * resultSet.getString("CreatedBy");
//			 */
//		}
//		return results;
//	}
//	public void updateTransaction(Connection connection,Transaction trxn) {
//		// DO the update SQL here	
//	}
//	private PreparedStatement preparedStatement = null;
//	Transaction transaction = new Transaction();

	
	public Transaction createTrxns() throws Exception {

		Transaction transaction = new Transaction();

		// Validate the INT ID field
		String user_inputID = JOptionPane.showInputDialog("Enter a unique ID");
		String validatedID = validateInt(user_inputID);
		transaction.setID(Integer.valueOf(validatedID));

		// Validate the VARCHAR field
		String user_inputNameonCard = JOptionPane.showInputDialog("Enter the name on the card");
		String validatedNameonCard = validationCheck(user_inputNameonCard);
		transaction.setNameOnCard(validatedNameonCard);

		// Select Card Type
		String inputType = JOptionPane
				.showInputDialog("Select a Credit Card Type\n" + "1. MasterCard \n2. Visa \n3. American Express");
		String user_inputCardNum = JOptionPane.showInputDialog("Enter the number on the card");
		String validatedCardNum = validationCheck(user_inputCardNum);
		String cardType[] = selectCardType(inputType, validatedCardNum);
		transaction.setCardType(cardType[0]);
		transaction.setCardNumber(cardType[1]);

		// Validate the Decimal Field
		String user_inputPrice = JOptionPane.showInputDialog("Enter Unit price of product");
		String validatedPrice = validatePrice(user_inputPrice);
		try {
			transaction.setUnitPrice(Double.parseDouble(validatedPrice));
		} catch (NumberFormatException nfe) {
			Logger.getLogger("Main").log(Level.WARNING, nfe.getLocalizedMessage().toString());
		}

		// Validate the INT field
		String user_inputQty = JOptionPane.showInputDialog("Enter the total number of items");
		String validatedQty = validateInt(user_inputQty);
		try {
			transaction.setQty(Integer.valueOf(validatedQty));
		} catch (NumberFormatException nfe) {
			Logger.getLogger("Main").log(Level.WARNING, nfe.getLocalizedMessage().toString());
		}

		// Multiply the unit price by the qty to get the Total price
		transaction.setTotalPrice(transaction.getUnitPrice() * transaction.getQty());

		// Validate the VARCHAR field
		String user_inputExpDate = JOptionPane.showInputDialog("Enter the Expiry Date \n Format:MM/YYYY");
		String validatedExpDate = validationCheck(user_inputExpDate);
		String validatedFormat = dateCheck(validatedExpDate);
		transaction.setExpDate(validatedFormat);

		return transaction;
	}
		

	public void createTransaction(Connection connection, Transaction trxn) {

		try {
			preparedStatement = connection
					.prepareStatement("insert into assignment2.transaction values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			preparedStatement.setInt(1, trxn.getID());
			preparedStatement.setString(2, trxn.getNameOnCard());
			preparedStatement.setString(3, trxn.getCardNumber());
			preparedStatement.setString(4, trxn.getCardType());
			preparedStatement.setDouble(5, trxn.getUnitPrice());
			preparedStatement.setInt(6, trxn.getQty());
			preparedStatement.setDouble(7, trxn.getTotalPrice());
			preparedStatement.setString(8, trxn.getExpDate());
			preparedStatement.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setString(10, System.getProperty("user.name"));

			int status = preparedStatement.executeUpdate();

			if (status > 0) {
				Logger.getLogger("Main").log(Level.INFO, "This row was created successfully ");
				JOptionPane.showMessageDialog(null, "Created successfully");
				getTransaction(connection, trxn.getID());
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
			System.out.println("ID already taken. Try again");
		} catch (SQLException se) {
			Logger.getLogger("Main").log(Level.WARNING, se.getLocalizedMessage().toString());
			se.printStackTrace();
		} catch (Exception e) {
			Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
			e.printStackTrace();
		}
	}

	public void updateTransaction(Connection connect) {
		try {

			int oldID = Integer.valueOf(JOptionPane.showInputDialog("Enter the ID for the row you want to edit:"));
			Transaction trxn = createTrxns();
			preparedStatement = connect.prepareStatement("UPDATE assignment2.transaction SET ID = ?, "
					+ "NameOnCard = ?, CardNumber = ?, CreditCardType = ?, UnitPrice = ?, Quantity = ?, TotalPrice = ?, ExpDate = ?, CreatedOn = ?, CreatedBy = ? WHERE ID = ?");
			preparedStatement.setInt(1, trxn.getID());
			preparedStatement.setString(2, trxn.getNameOnCard());
			preparedStatement.setString(3, trxn.getCardNumber());
			preparedStatement.setString(4, trxn.getCardType());
			preparedStatement.setDouble(5, trxn.getUnitPrice());
			preparedStatement.setInt(6, trxn.getQty());
			preparedStatement.setDouble(7, trxn.getTotalPrice());
			preparedStatement.setString(8, trxn.getExpDate());
			preparedStatement.setDate(9, new java.sql.Date(System.currentTimeMillis()));
			preparedStatement.setString(10, System.getProperty("user.name"));
			preparedStatement.setInt(11, oldID);

			int status = preparedStatement.executeUpdate();

			if (status > 0) {
				Logger.getLogger("Main").log(Level.INFO, "This row was updated successfully!");
				JOptionPane.showMessageDialog(null, "Updated successfully");
				getTransaction(connect, trxn.getID());
			}

		} catch (SQLException se) {
			Logger.getLogger("Main").log(Level.WARNING, se.getLocalizedMessage().toString());
		} catch (Exception e) {
			Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
		}

	}

	public void removeTransaction(Connection connect, int trxnID) {
		try {
			PreparedStatement preparedStatement = null;

			preparedStatement = connect.prepareStatement("DELETE FROM assignment2.transaction WHERE ID = ?");
			preparedStatement.setInt(1, trxnID);

			int status = preparedStatement.executeUpdate();

			if (status > 0) {
				Logger.getLogger("Main").log(Level.INFO, "This row was deleted successfully!");
				JOptionPane.showMessageDialog(null, "Deleted successfully");
			} else {
				Logger.getLogger("Main").log(Level.INFO, "This row does not exist in the database!");
			}
		} catch (SQLException se) {
			Logger.getLogger("Main").log(Level.WARNING, se.getLocalizedMessage().toString());
			se.printStackTrace();
		} catch (Exception e) {
			Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
			e.printStackTrace();
		}

	}

	public void getTransaction(Connection connect, int trxnID) {
		try {
			PreparedStatement preparedStatement = null;

			preparedStatement = connect.prepareStatement("SELECT * FROM assignment2.transaction WHERE ID = ?");
			preparedStatement.setInt(1, trxnID);

			ResultSet rs = preparedStatement.executeQuery();
			 if(rs.next()) {
				 rs = preparedStatement.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = rs.getString(i);
					System.out.print(rsmd.getColumnName(i) + " : " + columnValue);
				}
				System.out.println("");
			}
			 }
			else {
				Logger.getLogger("Main").log(Level.INFO, "This row does not exist in the database!");
			}
			 
			
		} catch (SQLException se) {
			Logger.getLogger("Main").log(Level.WARNING, se.getLocalizedMessage().toString());
		} catch (Exception e) {
			System.out.println("This does not exist in the database");
			Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
			e.printStackTrace();
		}
	}

	public String validationCheck(String input) {

		String validatedStrings = null;
		int t = 0;

		if (!(input.isEmpty())) {

			for (int i = 0; i < input.length(); i++) {
				char x = input.charAt(i);

				if (x == ';' || x == ':' || x == '!' || x == '@' || x == '#' || x == '$' || x == '%' || x == '^'
						|| x == '*' || x == '+' || x == '?' || x == '<' || x == '>') {
					t += 1;
				}

				if (t == 0) {
					validatedStrings = input;
				}

				else {
					String again = JOptionPane.showInputDialog("Invalid character detected. Try again");
					t = 0;
					validatedStrings = validationCheck(again);
				}
			}
		} else {
			String again = JOptionPane.showInputDialog("Cannot leave this field empty. Please try again.");
			t = 0;
			validatedStrings = validationCheck(again);
		}

		return validatedStrings;
	}

	public String validateInt(String user_inputID) {
		String validatedID = null;
		if (user_inputID != null && user_inputID.matches("\\d+")) {
			validatedID = user_inputID;
		} else {
			String again = JOptionPane.showInputDialog("Not an integer. Please try again. Enter a unique ID");
			validatedID = validateInt(again);
		}
		return validatedID;
	}

	public String validatePrice(String user_inputPrice) {
		String validatedPrice = null;
		if (user_inputPrice != null && user_inputPrice.matches("[0-9]+([,.][0-9]{1,2})?")) {
			validatedPrice = user_inputPrice;
		} else {
			String again = JOptionPane.showInputDialog("Invalid character detected. Please try again.");
			validatedPrice = validatePrice(again);
		}
		return validatedPrice;
	}

	public String[] selectCardType(String inputType, String CardNo) {
		String cardType[] = new String[2];

		switch (inputType) {
		case "1":
			if ((CardNo.startsWith("51") || CardNo.startsWith("52") || CardNo.startsWith("53")
					|| CardNo.startsWith("54") || CardNo.startsWith("55")) && CardNo.length() == 16) {
				cardType[0] = "MasterCard";
				cardType[1] = CardNo;

			} else {
				String cardNoagain = JOptionPane.showInputDialog("Not a valid Mastercard Number");
				cardType = selectCardType(inputType, cardNoagain);
			}
			break;

		case "2":
			if (CardNo.startsWith("4") && CardNo.length() == 16) {
				cardType[0] = "Visa";
				cardType[1] = CardNo;
			} else {
				String cardNoagain = JOptionPane.showInputDialog("This is not a valid Visa card");
				cardType = selectCardType(inputType, cardNoagain);
			}
			break;

		case "3":
			if ((CardNo.startsWith("34") || CardNo.startsWith("37")) && CardNo.length() == 15) {
				cardType[0] = "American Express";
				cardType[1] = CardNo;
			} else {
				String cardNoagain = JOptionPane.showInputDialog("Not a valid American ExpressCard.Try again");
				cardType = selectCardType(inputType, cardNoagain);
			}
			break;
		default: {
			String again = JOptionPane.showInputDialog("Invalid Card Type");
			String noAgain = JOptionPane.showInputDialog("Card Number");
			cardType = selectCardType(again, noAgain);
		}
		}
		return cardType;
	}

	public String dateCheck(String dateInput) {
		String validatedDate = null;
		if (dateInput.contains("/")) {
			int yyyy = Integer.parseInt(dateInput.substring(dateInput.indexOf("/") + 1));
			int mm = Integer.parseInt(dateInput.substring(0, dateInput.indexOf("/")));

			if ((yyyy >= 2016 && yyyy <= 2031) && (mm > 0 && mm <= 12)) {
				validatedDate = mm + "/" + yyyy;
			} else {
				String again = JOptionPane.showInputDialog("Invalid Date. Try again");
				validatedDate = dateCheck(again);
			}
		} else {
			String again = JOptionPane.showInputDialog("Invalid Date. Try again");
			validatedDate = dateCheck(again);
		}

		return validatedDate;
	}

}
