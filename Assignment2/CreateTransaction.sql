CREATE TABLE `transaction` (
  `ID` int(11) NOT NULL,
  `NameOnCard` varchar(256) DEFAULT NULL,
  `CardNumber` varchar(45) DEFAULT NULL,
  `CreditCardType` varchar(45) DEFAULT NULL,
  `UnitPrice` decimal(10,2) DEFAULT NULL,
  `Quantity` int(11) DEFAULT NULL,
  `TotalPrice` decimal(10,2) DEFAULT NULL,
  `ExpDate` varchar(16) DEFAULT NULL,
  `CreatedOn` datetime DEFAULT NULL,
  `CreatedBy` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB;