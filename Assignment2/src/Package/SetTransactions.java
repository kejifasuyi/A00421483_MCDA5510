package Package;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class SetTransactions {

	public Transaction makeTransaction() throws Exception {

		Transaction transaction = new Transaction();
		Operations ops = new Operations();

		// Validate the INT ID field
		String user_inputID = JOptionPane.showInputDialog("Enter a unique ID");
		String validatedID = ops.validateInt(user_inputID);
		try {
			transaction.setID(Integer.valueOf(validatedID));
		} catch (NumberFormatException e) {
			Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
			e.printStackTrace();
		}

		// Validate the VARCHAR field
		String user_inputNameonCard = JOptionPane.showInputDialog("Enter the name on the card");
		String validatedNameonCard = ops.validationCheck(user_inputNameonCard);
		transaction.setNameOnCard(validatedNameonCard);

		// Select Card Type
		String inputType = JOptionPane
				.showInputDialog("Select a Credit Card Type\n" + "1. MasterCard \n2. Visa \n3. American Express");
		String user_inputCardNum = JOptionPane.showInputDialog("Enter the number on the card");
		String validatedCardNum = ops.validationCheck(user_inputCardNum);
		String cardType[] = ops.selectCardType(inputType, validatedCardNum);
		transaction.setCardType(cardType[0]);
		transaction.setCardNumber(cardType[1]);

		// Validate the Decimal Field
		String user_inputPrice = JOptionPane.showInputDialog("Enter Unit price of product");
		String validatedPrice = ops.validatePrice(user_inputPrice);
		try {
			transaction.setUnitPrice(Double.parseDouble(validatedPrice));
		} catch (NumberFormatException nfe) {
			Logger.getLogger("Main").log(Level.WARNING, nfe.getLocalizedMessage().toString());
		}

		// Validate the INT field
		String user_inputQty = JOptionPane.showInputDialog("Enter the total number of items");
		String validatedQty = ops.validateInt(user_inputQty);
		try {
			transaction.setQty(Integer.valueOf(validatedQty));
		} catch (NumberFormatException nfe) {
			Logger.getLogger("Main").log(Level.WARNING, nfe.getLocalizedMessage().toString());
		}

		// Multiply the unit price by the qty to get the Total price
		transaction.setTotalPrice(transaction.getUnitPrice() * transaction.getQty());

		// Validate the VARCHAR field
		String user_inputExpDate = JOptionPane.showInputDialog("Enter the Expiry Date \n Format:MM/YYYY");
		String validatedExpDate = ops.validationCheck(user_inputExpDate);
		String validatedFormat = ops.dateCheck(validatedExpDate);
		transaction.setExpDate(validatedFormat);

		return transaction;
	}
}
