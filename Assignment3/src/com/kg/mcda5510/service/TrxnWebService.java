package com.kg.mcda5510.service;

import com.kg.mcda5510.entity.Transaction;
import com.kg.mcda5510.dao.MySQLAccess;


public class TrxnWebService {
	
	private static MySQLAccess dao = new MySQLAccess();
	private static Transaction trxn;
	
		
	public boolean createTransaction(int id, String Name, String CardType, String CardNumber, 
			 double unitPrice, int qty, String expDate) {
		try {
		
			trxn = dao.createTrxns();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dao.createTransaction(trxn);
	}
	
	public boolean updateTransaction(int oldid, int id, String Name, String CardType, String CardNumber, 
			 double unitPrice, int qty, String expDate) {	
		try {
			
			trxn = dao.createTrxns();
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
