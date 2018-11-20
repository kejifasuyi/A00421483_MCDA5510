package com.kg.mcda5510.service;

import java.sql.Connection;
import com.kg.mcda5510.entity.Transaction;
import com.kg.mcda5510.connect.ConnectionFactory;
import com.kg.mcda5510.dao.MySQLAccess;

public class TrxnWebService {
	
	private static MySQLAccess dao = new MySQLAccess();
	private static Connection connection;
	
	public void initConnection() throws Exception
	{
		ConnectionFactory factory = new ConnectionFactory();
		connection = factory.getConnection("mySQLJDBC");
	}
	
//	public void updateTransaction(int trxnID, String Name, String CardNumber,
//			 double unitPrice, int qty, double totalPrice, String expDate){
//	}
	public void createTransaction(Transaction trxn) {
		dao.createTransaction(connection, trxn);
	}
	
	public void updateTransaction(Transaction trxn) {
		dao.updateTransaction(connection);
	}
	
	
	public void getTransaction(int trxnID) {
		dao.getTransaction(connection, trxnID);;
	}
	
	public void removeTransaction(int trxnID) {
		dao.removeTransaction(connection, trxnID);
	}
	
}
