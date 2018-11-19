/**
 * Transaction.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.kg.mcda5510.entity;

public class Transaction  implements java.io.Serializable {
    private int ID;

    private java.lang.String cardNumber;

    private java.lang.String cardType;

    private java.util.Calendar createdOn;

    private java.lang.String expDate;

    private java.lang.String nameOnCard;

    private int qty;

    private double totalPrice;

    private double unitPrice;

    public Transaction() {
    }

    public Transaction(
           int ID,
           java.lang.String cardNumber,
           java.lang.String cardType,
           java.util.Calendar createdOn,
           java.lang.String expDate,
           java.lang.String nameOnCard,
           int qty,
           double totalPrice,
           double unitPrice) {
           this.ID = ID;
           this.cardNumber = cardNumber;
           this.cardType = cardType;
           this.createdOn = createdOn;
           this.expDate = expDate;
           this.nameOnCard = nameOnCard;
           this.qty = qty;
           this.totalPrice = totalPrice;
           this.unitPrice = unitPrice;
    }


    /**
     * Gets the ID value for this Transaction.
     * 
     * @return ID
     */
    public int getID() {
        return ID;
    }


    /**
     * Sets the ID value for this Transaction.
     * 
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }


    /**
     * Gets the cardNumber value for this Transaction.
     * 
     * @return cardNumber
     */
    public java.lang.String getCardNumber() {
        return cardNumber;
    }


    /**
     * Sets the cardNumber value for this Transaction.
     * 
     * @param cardNumber
     */
    public void setCardNumber(java.lang.String cardNumber) {
        this.cardNumber = cardNumber;
    }


    /**
     * Gets the cardType value for this Transaction.
     * 
     * @return cardType
     */
    public java.lang.String getCardType() {
        return cardType;
    }


    /**
     * Sets the cardType value for this Transaction.
     * 
     * @param cardType
     */
    public void setCardType(java.lang.String cardType) {
        this.cardType = cardType;
    }


    /**
     * Gets the createdOn value for this Transaction.
     * 
     * @return createdOn
     */
    public java.util.Calendar getCreatedOn() {
        return createdOn;
    }


    /**
     * Sets the createdOn value for this Transaction.
     * 
     * @param createdOn
     */
    public void setCreatedOn(java.util.Calendar createdOn) {
        this.createdOn = createdOn;
    }


    /**
     * Gets the expDate value for this Transaction.
     * 
     * @return expDate
     */
    public java.lang.String getExpDate() {
        return expDate;
    }


    /**
     * Sets the expDate value for this Transaction.
     * 
     * @param expDate
     */
    public void setExpDate(java.lang.String expDate) {
        this.expDate = expDate;
    }


    /**
     * Gets the nameOnCard value for this Transaction.
     * 
     * @return nameOnCard
     */
    public java.lang.String getNameOnCard() {
        return nameOnCard;
    }


    /**
     * Sets the nameOnCard value for this Transaction.
     * 
     * @param nameOnCard
     */
    public void setNameOnCard(java.lang.String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }


    /**
     * Gets the qty value for this Transaction.
     * 
     * @return qty
     */
    public int getQty() {
        return qty;
    }


    /**
     * Sets the qty value for this Transaction.
     * 
     * @param qty
     */
    public void setQty(int qty) {
        this.qty = qty;
    }


    /**
     * Gets the totalPrice value for this Transaction.
     * 
     * @return totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }


    /**
     * Sets the totalPrice value for this Transaction.
     * 
     * @param totalPrice
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    /**
     * Gets the unitPrice value for this Transaction.
     * 
     * @return unitPrice
     */
    public double getUnitPrice() {
        return unitPrice;
    }


    /**
     * Sets the unitPrice value for this Transaction.
     * 
     * @param unitPrice
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Transaction)) return false;
        Transaction other = (Transaction) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.ID == other.getID() &&
            ((this.cardNumber==null && other.getCardNumber()==null) || 
             (this.cardNumber!=null &&
              this.cardNumber.equals(other.getCardNumber()))) &&
            ((this.cardType==null && other.getCardType()==null) || 
             (this.cardType!=null &&
              this.cardType.equals(other.getCardType()))) &&
            ((this.createdOn==null && other.getCreatedOn()==null) || 
             (this.createdOn!=null &&
              this.createdOn.equals(other.getCreatedOn()))) &&
            ((this.expDate==null && other.getExpDate()==null) || 
             (this.expDate!=null &&
              this.expDate.equals(other.getExpDate()))) &&
            ((this.nameOnCard==null && other.getNameOnCard()==null) || 
             (this.nameOnCard!=null &&
              this.nameOnCard.equals(other.getNameOnCard()))) &&
            this.qty == other.getQty() &&
            this.totalPrice == other.getTotalPrice() &&
            this.unitPrice == other.getUnitPrice();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getID();
        if (getCardNumber() != null) {
            _hashCode += getCardNumber().hashCode();
        }
        if (getCardType() != null) {
            _hashCode += getCardType().hashCode();
        }
        if (getCreatedOn() != null) {
            _hashCode += getCreatedOn().hashCode();
        }
        if (getExpDate() != null) {
            _hashCode += getExpDate().hashCode();
        }
        if (getNameOnCard() != null) {
            _hashCode += getNameOnCard().hashCode();
        }
        _hashCode += getQty();
        _hashCode += new Double(getTotalPrice()).hashCode();
        _hashCode += new Double(getUnitPrice()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Transaction.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://entity.mcda5510.kg.com", "Transaction"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.mcda5510.kg.com", "ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.mcda5510.kg.com", "cardNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.mcda5510.kg.com", "cardType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createdOn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.mcda5510.kg.com", "createdOn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.mcda5510.kg.com", "expDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameOnCard");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.mcda5510.kg.com", "nameOnCard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("qty");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.mcda5510.kg.com", "qty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.mcda5510.kg.com", "totalPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.mcda5510.kg.com", "unitPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
