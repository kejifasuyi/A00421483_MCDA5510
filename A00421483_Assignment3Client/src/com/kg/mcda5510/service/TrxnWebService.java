/**
 * TrxnWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.kg.mcda5510.service;

public interface TrxnWebService extends java.rmi.Remote {
    public void createTransaction(com.kg.mcda5510.entity.Transaction trxn) throws java.rmi.RemoteException;
    public void updateTransaction(com.kg.mcda5510.entity.Transaction trxn) throws java.rmi.RemoteException;
    public void removeTransaction(int trxnID) throws java.rmi.RemoteException;
    public void initConnection() throws java.rmi.RemoteException;
    public void getTransaction(int trxnID) throws java.rmi.RemoteException;
}
