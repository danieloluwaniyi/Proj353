create table PROJECT353.SUBMISSIONS
(
	
	SUBMISSION_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
        USER_ID VARCHAR(15),
	RATING DOUBLE,
	SUBMISSION_DATE DATE default CURRENT DATE,
	SUBMISSION_CONTENT BLOB(2147483647),
        CONSTRAINT submissions_submission_id_pk PRIMARY KEY(SUBMISSION_ID)
);

CREATE TABLE PROJECT353.USER
(

FirstName VARCHAR2(15),
LastName VARCHAR2(15),
User_ID VARCHAR2(15),
Email VARCHAR2(30),
Password VARCHAR2(30),
Paid BOOLEAN,
NameOnCard VARCHAR2(30),
CreditCardNum VARCHAR(16),
ExpirationMonth NUMBER(12),
ExpirationYear NUMBER(4),
);






INSERT INTO PROJECT353.SUBMISSIONS(USER_ID, RATING, SUBMISSION_CONTENT)
VALUES('doluwan',5.0,NULL);