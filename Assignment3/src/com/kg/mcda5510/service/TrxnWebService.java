package com.kg.mcda5510.service;

import com.kg.mcda5510.entity.Transaction;
import com.kg.mcda5510.dao.MySQLAccess;


public class TrxnWebService {
	
	private static MySQLAccess dao = new MySQLAccess();
	private static Transaction trxn;
	
		
	public boolean createTransaction(String id, String Name, String CardType, String CardNumber, 
			 String unitPrice, String qty, String expDate) {
		try {
			trxn = dao.createTrxns(id, Name, CardType, CardNumber, unitPrice, qty, expDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dao.createTransaction(trxn);
	}
	
	public boolean updateTransaction(int oldid, String id, String Name, String CardType, String CardNumber, 
			 String unitPrice, String qty, String expDate) {	
		try {
			
			trxn = dao.createTrxns(id, Name, CardType, CardNumber, unitPrice, qty, expDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dao.updateTransaction(trxn, oldid);
	}
	
	
	public Transaction getTransaction(int trxnID) {
		return dao.getTransaction(trxnID);
	}
	
	public boolean removeTransaction(int trxnID) {
		return dao.removeTransaction(trxnID);
	}
	
}
