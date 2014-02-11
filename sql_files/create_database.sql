USE Spidey
CREATE TABLE IF NOT EXISTS `Records`(
  `RecordID` INT(11) NOT NULL AUTO_INCREMENT,
  `URL` text NOT NULL,
  `Domain` text NOT NULL,
  `Args` text NOT NULL,
  `Title` text NOT NULL,
  `Body` text NOT NULL,
  `TimeStamp` TIMESTAMP NOT NULL,
  `UpdateTime` INT(11) NOT NULL,
  `Raw` text NOT NULL,
  `LinksTo` INT(11) NOT NULL,
  `LinksBack` INT(11) NOT NULL,
  `LoadTime` INT(11) NOT NULL,
  `LastUpdate` TIMESTAMP NOT NULL,
   PRIMARY KEY (`RecordID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;
