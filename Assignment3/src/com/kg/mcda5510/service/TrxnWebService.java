package com.kg.mcda5510.service;

import com.kg.mcda5510.entity.Transaction;
import com.kg.mcda5510.dao.MySQLAccess;

public class TrxnWebService {

	private static MySQLAccess dao = new MySQLAccess();
	private static Transaction trxn;

	public String createTransaction(String id, String Name, String CardType, String CardNumber, String unitPrice,
			String qty, String expDate) {
		boolean response = false;
		String result = null;
		try {
			trxn = dao.createTrxns(id, Name, CardType, CardNumber, unitPrice, qty, expDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response = dao.createTransaction(trxn);
		if (response == true) {
			result = "Created Successfully";
		} else {
			result = "Try again";
		}
		return result;
	}

	public boolean updateTransaction(int oldid, String id, String Name, String CardType, String CardNumber,
			String unitPrice, String qty, String expDate) {
		try {

			trxn = dao.createTrxns(id, Name, CardType, CardNumber, unitPrice, qty, expDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dao.updateTransaction(trxn, oldid);
	}

	public String getTransaction(int trxnID) {
		return dao.getTransaction(trxnID);
	}

	public boolean removeTransaction(int trxnID) {
		return dao.removeTransaction(trxnID);
	}

}
