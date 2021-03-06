/*
    Create statements for project 1
*/
CREATE TABLE MANAGER(
    MAN_ID NUMBER(6) CONSTRAINT PK_MAN_ID PRIMARY KEY,
    NAME VARCHAR2(150),
	PSWRD VARCHAR2(255),
	USERNAME VARCHAR(255)
);

CREATE TABLE REIMBURSMENT(
    REIMB_ID NUMBER(5) CONSTRAINT PK_REIM PRIMARY KEY,
    EMP_ID NUMBER(6),
    STATUS VARCHAR2(25),
    DESCRIPTION VARCHAR2(255),
    RESOLVED_BY NUMBER(6)
);

CREATE TABLE EMPLOYEE(
    EMP_ID NUMBER(6) CONSTRAINT PK_EMP PRIMARY KEY,
    FNAME VARCHAR2(150),
    LNAME VARCHAR(150),
    MAN_ID NUMBER(6),
    PSWRD VARCHAR2(255),
	USERNAME VARCHAR(255)
);

-- ADD RELATIONSHIPS BETWEEN TABLES
ALTER TABLE REIMBURSMENT 
ADD CONSTRAINT FK_EMP_REIMB_ID FOREIGN KEY (EMP_ID) REFERENCES EMPLOYEE;

ALTER TABLE EMPLOYEE 
ADD CONSTRAINT FK_MAN_ID FOREIGN KEY (MAN_ID) REFERENCES MANAGER;

--TRIGGERS FOR PRIMARY KEYS
--EMPLOYEE AND USERNAME STORED PROCEDURE
CREATE SEQUENCE SQ_EMP_ID
START WITH 100
INCREMENT BY 1
NOCACHE;

CREATE OR REPLACE TRIGGER TR_INSERT_EMP_ID
BEFORE INSERT ON EMPLOYEE
FOR EACH ROW
BEGIN
    SELECT SQ_EMP_ID.NEXTVAL INTO :NEW.EMP_ID FROM DUAL;
END;
/

CREATE OR REPLACE PROCEDURE TR_INSERT_EMP_USERNAME
IS
    F_NAME VARCHAR2(100);
    EMPID NUMBER(6);
BEGIN
    SELECT last_number -1 INTO EMPID FROM user_sequences WHERE sequence_name = 'SQ_EMP_ID';
    SELECT FNAME INTO F_NAME FROM EMPLOYEE WHERE EMP_ID = EMPID;
    UPDATE EMPLOYEE SET USERNAME = concat(F_NAME, EMPID) WHERE EMP_ID = EMPID;
END;
/

 --MANAGER
CREATE SEQUENCE SQ_MAN_ID
START WITH 100
INCREMENT BY 1
NOCACHE;

CREATE OR REPLACE TRIGGER TR_INSERT_MAN_ID
BEFORE INSERT ON MANAGER
FOR EACH ROW
BEGIN
    SELECT SQ_MAN_ID.NEXTVAL INTO :NEW.MAN_ID FROM DUAL;
END;
/

CREATE OR REPLACE PROCEDURE TR_INSERT_MAN_USERNAME
IS
    F_NAME VARCHAR2(100);
    MANID NUMBER(6);
BEGIN
    SELECT last_number -1 INTO MANID FROM user_sequences WHERE sequence_name = 'SQ_MAN_ID';
    SELECT NAME INTO F_NAME FROM MANAGER WHERE MAN_ID = MANID;
    UPDATE MANAGER SET USERNAME = concat(F_NAME, MANID) WHERE MAN_ID = MANID;
END;
/

--REIMBURSMENT
CREATE OR REPLACE SEQUENCE SQ_RIEMB_ID
START WITH 100
INCREMENT BY 1;

CREATE OR REPLACE TRIGGER TR_INSERT_RIEMB_ID
BEFORE INSERT ON REIMBURSMENT
FOR EACH ROW
BEGIN
    SELECT SQ_RIEMB_ID.NEXTVAL INTO :NEW.REIMB_ID FROM DUAL;
END;
/


--TEST DATA: MANAGERS
insert into MANAGER (NAME, PSWD) values ('Francesca', 'McKeggie');
begin 
TR_INSERT_MAN_USERNAME;
end;
/
insert into MANAGER (NAME, PSWD) values ('Marchall', 'McKeaveney');
begin 
TR_INSERT_MAN_USERNAME;
end;
/
insert into MANAGER (NAME, PSWD) values ('Arron', 'Picker');
begin 
TR_INSERT_MAN_USERNAME;
end;
/
insert into MANAGER (NAME, PSWD) values ('Kalila', 'Waistell');
begin 
TR_INSERT_MAN_USERNAME;
end;
/
insert into MANAGER (NAME, PSWD) values ('Cointon', 'Piperley');
begin 
TR_INSERT_MAN_USERNAME;
end;
/
insert into MANAGER (NAME, PSWD) values ('Mollee', 'Eatherton');
begin 
TR_INSERT_MAN_USERNAME;
end;
/



--TEST DATA: EMPLOYEE
insert into EMPLOYEE (FNAME, LNAME, MAN_ID, PSWRD) values ('Lindsey', 'Gilluley', 100, 'Bryum');
begin 
TR_INSERT_EMP_USERNAME;
end;
/
insert into EMPLOYEE (FNAME, LNAME, MAN_ID, PSWRD) values ('Cecelia', 'Woodroffe', 101, 'Vernonia');
begin 
TR_INSERT_EMP_USERNAME;
end;
/
insert into EMPLOYEE (FNAME, LNAME, MAN_ID, PSWRD) values ('Brandea', 'McSperron', 102, 'Echinocereus');
begin 
TR_INSERT_EMP_USERNAME;
end;
/
insert into EMPLOYEE (FNAME, LNAME, MAN_ID, PSWRD) values ('Owen', 'Templeton', 103, 'Triplaris');
begin 
TR_INSERT_EMP_USERNAME;
end;
/
insert into EMPLOYEE (FNAME, LNAME, MAN_ID, PSWRD) values ('Tracie', 'Runchman', 104, 'Randia');
begin 
TR_INSERT_EMP_USERNAME;
end;
/


--TEST DATA REIMBURSMENT
insert into REIMBURSMENT (EMP_ID, STATUS, DESCRIPTION) values (100, 'pending', 'travel - less than 50');
insert into REIMBURSMENT (EMP_ID, STATUS, DESCRIPTION) values (101, 'pending', 'Training');
insert into REIMBURSMENT (EMP_ID, STATUS, DESCRIPTION, RESOLVED_BY) values (101, 'resolved', 'Supplies - max of 60', 101);
