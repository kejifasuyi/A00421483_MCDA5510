package com.kg.mcda5510.dao;

/**
 * Original source code from 
 * http://www.vogella.com/tutorials/MySQLJava/article.html
 * 
**/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.kg.mcda5510.connect.ConnectionFactory;
import com.kg.mcda5510.entity.Transaction;

public class MySQLAccess {
	private PreparedStatement preparedStatement = null;
	Scanner in = new Scanner(System.in);

	public static Logger logger;

	public MySQLAccess() {
		initLogger();
	}

	private void initLogger() {
		logger = Logger.getLogger("myLog");
		FileHandler fh;
		try {
			fh = new FileHandler(
					"/Users/Dunnyfashion/Documents/GitHub/A00421483_MCDA5510/Assignment3/Logs/A00421483Log.log");

			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Connection dbConnect() {
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.getConnection("mySQLJDBC");
		return connection;
	}

	public Transaction createTrxns(String id, String Name, String CardType, String CardNumber, String unitPrice,
			String qty, String expDate) throws Exception {

		Transaction transaction = new Transaction();

		// Validate the INT ID field
		String validatedID = validateInt(id);
		transaction.setID(Integer.valueOf(validatedID));

		// Validate the VARCHAR field
		String validatedNameonCard = validationCheck(Name);
		transaction.setNameOnCard(validatedNameonCard);

		// Enter Card Number & CardType
		String validatedCardNum = validationCheck(CardNumber);
		String cardType[] = selectCardType(CardType, validatedCardNum);
		transaction.setCardType(cardType[0]);
		transaction.setCardNumber(cardType[1]);

		// Validate the Decimal Field
		String validatedPrice = validatePrice(unitPrice);
		try {
			transaction.setUnitPrice(Double.parseDouble(validatedPrice));
		} catch (NumberFormatException nfe) {
			logger.log(Level.WARNING, nfe.getLocalizedMessage().toString());
		}

		// Validate the INT field
		String validatedQty = validateInt(qty);
		try {
			transaction.setQty(Integer.valueOf(validatedQty));
		} catch (NumberFormatException nfe) {
			logger.log(Level.WARNING, nfe.getLocalizedMessage().toString());
		}

		// Multiply the unit price by the quantity to get the Total price
		transaction.setTotalPrice(transaction.getUnitPrice() * transaction.getQty());

		// Validate the VARCHAR field
		String validatedExpDate = validationCheck(expDate);
		String validatedFormat = dateCheck(validatedExpDate);
		transaction.setExpDate(validatedFormat);

		return transaction;
	}

	public boolean createTransaction(Transaction trxn) {
		Connection connection = dbConnect();
		boolean success = false;
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
				logger.log(Level.INFO, "This row was created successfully ");
				System.out.println("Created successfully");
				getTransaction(trxn.getID());
				success = true;
			} else {
				success = false;
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			logger.log(Level.WARNING, e.getLocalizedMessage().toString());
			System.out.println("ID already taken. Update Instead? Y/N");
			String inn = in.nextLine();
			if (inn.equalsIgnoreCase("y")) {
				updateTransaction(trxn, trxn.getID());
			} else {
				System.out.println("Okay. Bye");
			}

		} catch (SQLException se) {
			logger.log(Level.WARNING, se.getLocalizedMessage().toString());
			se.printStackTrace();
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getLocalizedMessage().toString());
			e.printStackTrace();
		}
		return success;
	}

	public boolean updateTransaction(Transaction trxn, int oldID) {
		Connection connect = dbConnect();
		boolean success = false;
		try {
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
				logger.log(Level.INFO, "This row was updated successfully!");
				System.out.println("Updated successfully");
				getTransaction(trxn.getID());
				success = true;

			} else {
				success = false;
				logger.log(Level.INFO, "Update unsuccessful!");
				System.out.println("Update unsuccessful.");

			}

		} catch (SQLException se) {
			logger.log(Level.WARNING, se.getLocalizedMessage().toString());
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getLocalizedMessage().toString());
		}
		return success;
	}

	public boolean removeTransaction(int trxnID) {
		Connection connect = dbConnect();
		boolean success = false;
		try {
			PreparedStatement preparedStatement = null;

			preparedStatement = connect.prepareStatement("DELETE FROM assignment2.transaction WHERE ID = ?");
			preparedStatement.setInt(1, trxnID);

			int status = preparedStatement.executeUpdate();

			if (status > 0) {
				logger.log(Level.INFO, "This row was deleted successfully!");
				System.out.println("Deleted successfully");
				success = true;
				return success;
			} else {
				logger.log(Level.INFO, "This row does not exist in the database!");
				success = false;
				return success;
			}
		} catch (SQLException se) {
			logger.log(Level.WARNING, se.getLocalizedMessage().toString());
			se.printStackTrace();
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getLocalizedMessage().toString());
			e.printStackTrace();
		}
		return success;
	}

	public String getTransaction(int trxnID) {
		Transaction t = new Transaction();
		Connection connect = dbConnect();
		try {
			PreparedStatement preparedStatement = null;

			preparedStatement = connect.prepareStatement("SELECT * FROM assignment2.transaction WHERE ID = ?");
			preparedStatement.setInt(1, trxnID);

			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				rs = preparedStatement.executeQuery();

				while (rs.next()) {
					t.setID(rs.getInt("ID"));
					t.setNameOnCard(rs.getString("NameOnCard"));
					t.setCardNumber(rs.getString("CardNumber"));
					t.setCardType(rs.getString("CreditCardType"));
					t.setUnitPrice(rs.getDouble("UnitPrice"));
					t.setQty(rs.getInt("Quantity"));
					t.setTotalPrice(rs.getDouble("TotalPrice"));
					t.setExpDate(rs.getString("ExpDate"));
					t.setCreatedOn(rs.getDate("CreatedOn"));
				}

			} else {
				logger.log(Level.INFO, "This row does not exist in the database!");
			}

		} catch (SQLException se) {
			logger.log(Level.WARNING, se.getLocalizedMessage().toString());
		} catch (Exception e) {
			System.out.println("This does not exist in the database");
			logger.log(Level.WARNING, e.getLocalizedMessage().toString());
			e.printStackTrace();
		}
		return t.toString();
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
			System.out.println("Invalid character detected. Please try again.");
			String again = in.nextLine();
			validatedPrice = validatePrice(again);
		}
		return validatedPrice;
	}

	public String[] selectCardType(String inputType, String CardNo) {
		String cardType[] = new String[2];

		switch (inputType) {
		case "Mastercard":
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

		case "Visa":
			if (CardNo.startsWith("4") && CardNo.length() == 16) {
				cardType[0] = "Visa";
				cardType[1] = CardNo;
			} else {
				System.out.println("This is not a valid Visa card");
				String cardNoagain = in.nextLine();
				cardType = selectCardType(inputType, cardNoagain);
			}
			break;

		case "American Express":
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
