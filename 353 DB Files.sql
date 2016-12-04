create table PROJECT353.SUBMISSIONS
(
	SUBMISSION_ID BIGINT  primary key GENERATED ALWAYS AS IDENTITY 
            (START WITH 1, INCREMENT BY 1),
	USER_ID VARCHAR(15),
	RATING DOUBLE,
	SUBMISSION_DATE DATE default CURRENT DATE,
	SUBMISSION_CONTENT BLOB(2147483647),
	PRICE DOUBLE default 35.0,
	RATERS INTEGER default 0
);

create table PROJECT353.USERS
(
	FIRSTNAME VARCHAR(15),
	LASTNAME VARCHAR(15),
	USER_ID VARCHAR(15) not null primary key,
	EMAIL VARCHAR(30),
	PASSWORD VARCHAR(30),
	PAID BOOLEAN,
	NAMEONCARD VARCHAR(30),
	CREDITCARDNUM VARCHAR(16),
	EXPIRATIONMONTH INTEGER,
	EXPIRATIONYEAR INTEGER,
	SECURITYCODE VARCHAR(3)
);



CREATE TABLE ORDERS(
    ORDER_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    User_ID VARCHAR2(15),
    SUBMISSION_ID VARCHAR2(5000),
    TOTAL NUMBER(50);
)



CREATE TABLE WINNERS(
        USER_ID VARCHAR2(15), SUBMISSION_ID NUMBER(12),
        CONSTRAIN winnter_pk PRIMARY KEY(USER_ID,SUBMISSION_ID)
);

CREATE TABLE ROYALTY(
    ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    USER_ID VARCHAR2(15),
    SUBMISSION_ID BIGINT,
    ROYALTY_AMOUNT NUMBER(5),
    ROYALTY_PAID BOOLEAN DEFAULT FALSE,
    CONSTRAINT royalty_id_pk PRIMARY KEY(ID)
);



CREATE TABLE PURCHASE(
    USER_ID VARVHAR(12), ROYALTY_AMT NUMBER(6) DEFAULT 0,
    CONSTRAIN royalty_pk PRIMARY KEY(USER_ID)
);
