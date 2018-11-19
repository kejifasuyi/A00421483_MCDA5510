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
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kg.mcda5510.entity.Transaction;


public class MySQLAccess {
	private PreparedStatement preparedStatement = null;
	Scanner in = new Scanner(System.in);

	
	public Transaction createTrxns() throws Exception {

		Transaction transaction = new Transaction();

		// Validate the INT ID field
		System.out.println("Enter a unique ID");
		String user_inputID = in.nextLine();
		String validatedID = validateInt(user_inputID);
		transaction.setID(Integer.valueOf(validatedID));

		// Validate the VARCHAR field
		System.out.println("Enter the name on the card");
		String user_inputNameonCard = in.nextLine();
		String validatedNameonCard = validationCheck(user_inputNameonCard);
		transaction.setNameOnCard(validatedNameonCard);

		// Select Card Type
		System.out.println("Select a Credit Card Type \n1. MasterCard \n2. Visa \n3. American Express");
		String inputType = in.nextLine();
		
		//Enter Card Number
		System.out.println("Enter the number on the card");
		String user_inputCardNum = in.nextLine();
		String validatedCardNum = validationCheck(user_inputCardNum);
		String cardType[] = selectCardType(inputType, validatedCardNum);
		transaction.setCardType(cardType[0]);
		transaction.setCardNumber(cardType[1]);

		// Validate the Decimal Field
		System.out.println("Enter Unit price of product");
		String user_inputPrice = in.nextLine();
		String validatedPrice = validatePrice(user_inputPrice);
		try {
			transaction.setUnitPrice(Double.parseDouble(validatedPrice));
		} catch (NumberFormatException nfe) {
			Logger.getLogger("Main").log(Level.WARNING, nfe.getLocalizedMessage().toString());
		}

		// Validate the INT field
		System.out.println("Enter the total number of items");
		String user_inputQty = in.nextLine();
		String validatedQty = validateInt(user_inputQty);
		try {
			transaction.setQty(Integer.valueOf(validatedQty));
		} catch (NumberFormatException nfe) {
			Logger.getLogger("Main").log(Level.WARNING, nfe.getLocalizedMessage().toString());
		}

		// Multiply the unit price by the quantity to get the Total price
		transaction.setTotalPrice(transaction.getUnitPrice() * transaction.getQty());

		// Validate the VARCHAR field
		System.out.println("Enter the Expiry Date \\n Format:MM/YYYY");
		String user_inputExpDate = in.nextLine();
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
				System.out.println("Created successfully");
				getTransaction(connection, trxn.getID());
				//return true;
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
			System.out.println("ID already taken. Update Instead?");
			updateTransaction(connection);
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
			System.out.println("Enter the ID for the row you want to edit:");
			int oldID = in.nextInt();
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
				System.out.println("Updated successfully");
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
				System.out.println("Deleted successfully");
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
					System.out.println("Invalid character detected. Try again");
					String again = in.nextLine();
					t = 0;
					validatedStrings = validationCheck(again);
				}
			}
		} else {
			System.out.println("Cannot leave this field empty. Please try again.");
			String again = in.nextLine();
			validatedStrings = validationCheck(again);
		}

		return validatedStrings;
	}

	public String validateInt(String user_inputID) {
		String validatedID = null;
		if (user_inputID != null && user_inputID.matches("\\d+")) {
			validatedID = user_inputID;
		} else {
			System.out.println("Not an integer. Please try again. Enter a unique ID");
			String again = in.nextLine();
			validatedID = validateInt(again);
		}
		return validatedID;
	}

	public String validatePrice(String user_inputPrice) {
		String validatedPrice = null;
		if (user_inputPrice != null && user_inputPrice.matches("[0-9]+([,.][0-9]{1,2})?")) {
			validatedPrice = user_inputPrice;
		} else {
			System.out.println("nvalid character detected. Please try again.");
			String again = in.nextLine();
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
				System.out.println("Not a valid Mastercard Number");
				String cardNoagain = in.nextLine();
				cardType = selectCardType(inputType, cardNoagain);
			}
			break;

		case "2":
			if (CardNo.startsWith("4") && CardNo.length() == 16) {
				cardType[0] = "Visa";
				cardType[1] = CardNo;
			} else {
				System.out.println("This is not a valid Visa card");
				String cardNoagain = in.nextLine();
				cardType = selectCardType(inputType, cardNoagain);
			}
			break;

		case "3":
			if ((CardNo.startsWith("34") || CardNo.startsWith("37")) && CardNo.length() == 15) {
				cardType[0] = "American Express";
				cardType[1] = CardNo;
			} else {
				System.out.println("Not a valid American ExpressCard.Try again");
				String cardNoagain = in.nextLine();
				cardType = selectCardType(inputType, cardNoagain);
			}
			break;
		default: {
			System.out.println("Invalid Card Type");
			String again = in.nextLine();
			System.out.println("Card Number");
			String noAgain = in.nextLine();
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
				System.out.println("Invalid Date. Try again");
				String again = in.nextLine();
				validatedDate = dateCheck(again);
			}
		} else {
			System.out.println("Invalid Date. Try again");
			String again = in.nextLine();
			validatedDate = dateCheck(again);
		}

		return validatedDate;
	}

}
