create table PROJECT353.SUBMISSIONS
(

	SUBMISSION_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
        USER_ID VARCHAR(15),
	RATING DOUBLE,
	SUBMISSION_DATE DATE default CURRENT DATE,
	SUBMISSION_CONTENT BLOB(2147483647),
        CONSTRAINT submissions_submission_id_pk PRIMARY KEY(SUBMISSION_ID)
);

CREATE TABLE Project353.Users (
FirstName VARCHAR(15),
LastName VARCHAR(15),
User_ID VARCHAR(15),
Email VARCHAR(30),
Password VARCHAR(30),
Paid BOOLEAN,
NameOnCard VARCHAR(30),
CreditCardNum VARCHAR(16),
securityCode VARCHAR(3),
ExpirationMonth INTEGER(2),
ExpirationYear INTEGER(4),
CONSTRAINT Usertable_User_ID_pk PRIMARY KEY (User_ID)
);



CREATE TABLE ORDERS(
    ORDER_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    User_ID VARCHAR2(15),
    SUBMISSION_ID VARCHAR2(5000),
    TOTAL NUMBER(50);
)




INSERT INTO PROJECT353.SUBMISSIONS(USER_ID, RATING, SUBMISSION_CONTENT)
VALUES('doluwan',5.0,NULL);

CREATE TABLE WINNERS(
        USER_ID VARCHAR2(15), SUBMISSION_ID NUMBER(12),
        CONSTRAIN winnter_pk PRIMARY KEY(USER_ID,SUBMISSION_ID)
);

CREATE TABLE ROYALTY(
    USER_ID VARVHAR(12), ROYALTY_AMT NUMBER(6) DEFAULT 0,
    CONSTRAIN royalty_pk PRIMARY KEY(USER_ID)
);



CREATE TABLE PURCHASE(
    USER_ID VARVHAR(12), ROYALTY_AMT NUMBER(6) DEFAULT 0,
    CONSTRAIN royalty_pk PRIMARY KEY(USER_ID)
);

create table PROJECT353.SUBMISSIONS
(
	
	SUBMISSION_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
        USER_ID VARCHAR(15),
	RATING DOUBLE,
	SUBMISSION_DATE DATE default CURRENT DATE,
	SUBMISSION_CONTENT BLOB(2147483647),
        PRICE DOUBLE default 35.0,
        CONSTRAINT submissions_submission_id_pk PRIMARY KEY(SUBMISSION_ID)
);

CREATE TABLE Project353.Users (
FirstName VARCHAR2(15),
LastName VARCHAR2(15),
User_ID VARCHAR2(15),
Email VARCHAR2(30),
Password VARCHAR2(30),
Paid BOOLEAN,
NameOnCard VARCHAR2(30),
CreditCardNum VARCHAR2(16),
ExpirationMonth NUMBER(12),
ExpirationYear NUMBER(4),
CONSTRAINT Usertable_User_ID_pk PRIMARY KEY (User_ID)
);



CREATE TABLE ORDERS(
    ORDER_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    User_ID VARCHAR2(15),
    SUBMISSION_ID VARCHAR2(5000),
    TOTAL NUMBER(50);
);




INSERT INTO PROJECT353.SUBMISSIONS(USER_ID, RATING, SUBMISSION_CONTENT)
VALUES('doluwan',5.0,NULL);


CREATE TABLE WINNERS(
        USER_ID VARCHAR2(15), SUBMISSION_ID NUMBER(12),
        CONSTRAIN winnter_pk PRIMARY KEY(USER_ID,SUBMISSION_ID)
);


CREATE TABLE ROYALTY(
    USER_ID VARVHAR(12), ROYALTY_AMT NUMBER(6) DEFAULT 0,
    CONSTRAIN royalty_pk PRIMARY KEY(USER_ID)
);



CREATE TABLE PURCHASE(
    USER_ID VARVHAR(12), ROYALTY_AMT NUMBER(6) DEFAULT 0,
    CONSTRAIN royalty_pk PRIMARY KEY(USER_ID)
);
