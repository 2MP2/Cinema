CLEAR SCREEN;

---------------CZYSZCZENIE BAZY ABY MOC PONOWNIE ODPALIC SKRYPT ----------------
--JESLI TABELE NIE ISTNIALY MOGA POJAWIC SIE BLEDY ALE NIE WPLYWA TO NA POPRAWNE DZIALANIE SKRYPTU
DROP TABLE Movies cascade constraints;
DROP TABLE Seances cascade constraints;
DROP TABLE ScreeningRooms cascade constraints;
DROP TABLE Transactions cascade constraints;
DROP TABLE Employees cascade constraints;
DROP TABLE Customers cascade constraints;
DROP TABLE Positions cascade constraints;
DROP TABLE TeakenSeats cascade constraints;


DROP SEQUENCE MOVIES_ID_SEQ;
DROP SEQUENCE SCREENINGROOMS_ID_SEQ;
DROP SEQUENCE TRANSACTIONS_ID_SEQ;
DROP SEQUENCE EMPLOYEES_ID_SEQ;
DROP SEQUENCE CUSTOMERS_ID_SEQ;
DROP SEQUENCE POSITIONS_ID_SEQ;
DROP SEQUENCE TEAKENSEATS_ID_SEQ;
DROP SEQUENCE SEANCES_ID_SEQ;

drop user c##cinema_user;

BEGIN 
    DBMS_SCHEDULER.drop_job (job_name => 'clearTeakenSeats');
END;
/


--------------------------------TWORZENIE TABEL---------------------------------

CREATE TABLE Movies (
    id_movie number(10),
    title varchar2(50 BYTE) NOT NULL,
    length number(3) NOT NULL,
    distributor varchar2(50 BYTE) NOT NULL,
    borrow_date date NOT NULL, 
    return_date date,
    dub_sub_lec char(1) NOT NULL 
    CHECK (dub_sub_lec = 'D' OR dub_sub_lec = 'S' OR dub_sub_lec = 'L'),
    is3D number(1) NOT NULL CHECK(is3D = 0 OR is3D = 1),
    PRIMARY KEY(id_movie)
);

CREATE TABLE ScreeningRooms(
    id_screening_room number(10),
    amount_of_seats number(4) NOT NULL,
    amount_of_rows number(2) NOT NULL,
    amount_of_columns number(2) NOT NULL,
    PRIMARY KEY(id_screening_room)
);

CREATE TABLE Seances(
    id_seance number(10),
    start_time date NOT NULL,
    end_time date NOT NULL,
    ticket_price number(5,2) NOT NULL,
    id_movie number(10) NOT NULL,
    id_screening_room number (10) NOT NULL,
    PRIMARY KEY(id_seance),
    
    CONSTRAINT FK_Movies_Seances
        FOREIGN KEY (id_movie)
            REFERENCES Movies(id_movie),
    CONSTRAINT FK_ScreeningRooms_Seances
        FOREIGN KEY (id_screening_room)
            REFERENCES ScreeningRooms(id_screening_room)
);

CREATE TABLE Positions (
	id_position number(10) NOT NULL,
	name varchar2(30 BYTE) UNIQUE NOT NULL,
	salary number(7,2) NOT NULL,
	PRIMARY KEY (id_position)
);

CREATE TABLE Employees (
	id_employee number(10),
	login varchar2(50 BYTE) UNIQUE NOT NULL,
	password varchar2(50 BYTE) NOT NULL,
	name varchar2(30 BYTE)NOT NULL,
	surname varchar2(30 BYTE)NOT NULL,
	phone_number varchar2(9 BYTE) UNIQUE NOT NULL,
	hire_date date NOT NULL,
	fire_date date,
	id_position number(10) NOT NULL,
	PRIMARY KEY (id_employee),
    
    CONSTRAINT FK_Positions_Employees
        FOREIGN KEY (id_position)
            REFERENCES Positions(id_position)
);

CREATE TABLE Customers (
	id_customer number(10),
	login varchar2(50 BYTE) UNIQUE NOT NULL,
	password varchar2(50 BYTE) NOT NULL,
	name varchar2(30 BYTE) NOT NULL,
	surname varchar2(30 BYTE) NOT NULL,
	phone_number varchar2(9 BYTE) UNIQUE NOT NULL,
    is_active number(1) NOT NULL CHECK(is_active = 0 OR is_active = 1), 
	PRIMARY KEY (id_customer)
);

CREATE TABLE Transactions(
    id_transaction number(10),
    amount number(6,2) NOT NULL,
    amount_of_tickets number(3) NOT NULL,
    amount_of_reduced_tickets number(3) NOT NULL,
    id_seance number(10) NOT NULL,
    id_customer number(10),
    PRIMARY KEY(id_transaction),
    
    CONSTRAINT FK_Seances_Transactions
        FOREIGN KEY (id_seance)
            REFERENCES Seances(id_seance),
    CONSTRAINT FK_Customers_Transactions
        FOREIGN KEY (id_customer)
            REFERENCES Customers(id_customer) 
);

CREATE TABLE TeakenSeats (
	id_seat number(10),
	expiration_date date NOT NULL,
	row_identifier char(1) NOT NULL,
	column_identifier number(2) NOT NULL,
	reserved_or_teaken char(1) NOT NULL 
    CHECK(reserved_or_teaken = 'R' or  reserved_or_teaken = 'T'),
	id_seance number(10) NOT NULL,
	id_transaction number(10),
	id_customer number(10),
	PRIMARY KEY (id_seat),
    
    CONSTRAINT FK_Seances_TeakenSeats
        FOREIGN KEY (id_seance)
            REFERENCES Seances(id_seance),
    CONSTRAINT FK_Transactions_TeakenSeats
        FOREIGN KEY (id_transaction)
            REFERENCES Transactions(id_transaction),
    CONSTRAINT FK_Customers_TeakenSeats
        FOREIGN KEY (id_customer)
            REFERENCES Customers(id_customer)
);


-------------------------------AUTOINKREMENTACJA--------------------------------

CREATE SEQUENCE CUSTOMERS_ID_SEQ
    START WITH 1 INCREMENT BY 1 CACHE 20;
    
CREATE OR REPLACE TRIGGER CUSTOMERS_ID_TRG 
BEFORE INSERT ON CUSTOMERS 
REFERENCING NEW AS NEW
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF (INSERTING AND :NEW.ID_CUSTOMER IS NULL) THEN
      SELECT CUSTOMERS_ID_SEQ.NEXTVAL INTO :NEW.ID_CUSTOMER FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/

CREATE SEQUENCE EMPLOYEES_ID_SEQ
    START WITH 1 INCREMENT BY 1 CACHE 20;

create or replace TRIGGER EMPLOYEES_ID_TRG 
BEFORE INSERT ON EMPLOYEES 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.ID_EMPLOYEE IS NULL THEN
      SELECT EMPLOYEES_ID_SEQ.NEXTVAL INTO :NEW.ID_EMPLOYEE FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/

CREATE SEQUENCE MOVIES_ID_SEQ
    START WITH 1 INCREMENT BY 1 CACHE 20;

create or replace TRIGGER MOVIES_ID_TRG 
BEFORE INSERT ON MOVIES 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.ID_MOVIE IS NULL THEN
      SELECT MOVIES_ID_SEQ.NEXTVAL INTO :NEW.ID_MOVIE FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/

CREATE SEQUENCE POSITIONS_ID_SEQ
    START WITH 1 INCREMENT BY 1 CACHE 20;

create or replace TRIGGER POSITIONS_ID_TRG 
BEFORE INSERT ON POSITIONS 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.ID_POSITION IS NULL THEN
      SELECT POSITIONS_ID_SEQ.NEXTVAL INTO :NEW.ID_POSITION FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/

CREATE SEQUENCE SCREENINGROOMS_ID_SEQ
    START WITH 1 INCREMENT BY 1 CACHE 20;

create or replace TRIGGER SCREENINGROOMS_ID_TRG 
BEFORE INSERT ON SCREENINGROOMS 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.ID_SCREENING_ROOM IS NULL THEN
      SELECT SCREENINGROOMS_ID_SEQ.NEXTVAL INTO :NEW.ID_SCREENING_ROOM FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/

CREATE SEQUENCE SEANCES_ID_SEQ
    START WITH 1 INCREMENT BY 1 CACHE 20;

create or replace TRIGGER SEANCES_ID_TRG 
BEFORE INSERT ON SEANCES 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.ID_SEANCE IS NULL THEN
      SELECT SEANCES_ID_SEQ.NEXTVAL INTO :NEW.ID_SEANCE FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/

CREATE SEQUENCE TEAKENSEATS_ID_SEQ
    START WITH 1 INCREMENT BY 1 CACHE 20;

create or replace TRIGGER TEAKENSEATS_ID_TRG 
BEFORE INSERT ON TEAKENSEATS 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.ID_SEAT IS NULL THEN
      SELECT TEAKENSEATS_ID_SEQ.NEXTVAL INTO :NEW.ID_SEAT FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/

CREATE SEQUENCE TRANSACTIONS_ID_SEQ
    START WITH 1 INCREMENT BY 1 CACHE 20;

create or replace TRIGGER TRANSACTIONS_ID_TRG 
BEFORE INSERT ON TRANSACTIONS 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.ID_TRANSACTION IS NULL THEN
      SELECT TRANSACTIONS_ID_SEQ.NEXTVAL INTO :NEW.ID_TRANSACTION FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/


-----------------AUTOMATYCZNE USUWANIE MIEJSC Z TABELI TeakenSeats---------------

CREATE OR REPLACE PROCEDURE deleteTS
AS
BEGIN
    DELETE FROM TEAKENSEATS 
    WHERE EXPIRATION_DATE < SYSDATE;
    COMMIT;
END;
/

BEGIN 
    DBMS_SCHEDULER.create_job (
        job_name    => 'clearTeakenSeats',
        job_type    => 'PLSQL_BLOCK',
        job_action  => 'BEGIN deleteTS; END',
        start_date  => SYSTIMESTAMP,
        repeat_interval => 'FREQ=MINUTELY;INTERVAL=5',
        enabled      => TRUE
    );
END;
/



-----------------------DODAWANIE ZAREZERWOWANEGO MIEJSCA------------------------


CREATE OR REPLACE PROCEDURE insertResTeakenSeats(in_row_identifier char, in_column_identifier number,
	in_id_seance number, in_id_customer number)   
AS
in_expiration_date date;
BEGIN
    SELECT start_time - 1/24 INTO in_expiration_date FROM SEANCES 
    WHERE id_seance = in_id_seance;

    INSERT INTO TEAKENSEATS
    (EXPIRATION_DATE, ROW_IDENTIFIER, COLUMN_IDENTIFIER, RESERVED_OR_TEAKEN, ID_SEANCE, ID_TRANSACTION, ID_CUSTOMER)
    VALUES 
    (in_expiration_date, in_row_identifier, in_column_identifier,
    'R', in_id_seance, NULL, in_id_customer);
    
    COMMIT;
END;
/


-------------------------DODAWANIE KUPIONEGO MIEJSCA----------------------------

CREATE OR REPLACE PROCEDURE insertTeakenSeats(in_row_identifier char, in_column_identifier number,
	in_id_seance number, in_id_transaction number, in_id_customer number)   
AS
in_expiration_date date;
BEGIN
    SELECT end_time + 2/24 INTO in_expiration_date FROM SEANCES 
    WHERE id_seance = in_id_seance;

    INSERT INTO TEAKENSEATS
    (EXPIRATION_DATE, ROW_IDENTIFIER, COLUMN_IDENTIFIER, RESERVED_OR_TEAKEN, ID_SEANCE, ID_TRANSACTION, ID_CUSTOMER)
    VALUES 
    (in_expiration_date, in_row_identifier, in_column_identifier,
    'T', in_id_seance, in_id_transaction, in_id_customer);
    
    COMMIT;
END;
/

------------------------------DODAWANIE TRANSAKCJI------------------------------

CREATE OR REPLACE PROCEDURE insertTransactions(in_amount number, in_amount_of_tickets number,
    in_amount_of_reduced_tickets number, in_id_seance number, in_id_customer number)    
AS
BEGIN
    INSERT INTO Transactions
    (amount, amount_of_tickets, amount_of_reduced_tickets, id_seance, id_customer)
    VALUES 
    (in_amount, in_amount_of_tickets, in_amount_of_reduced_tickets, in_id_seance, in_id_customer);
    
    COMMIT;
END;
/

--------------------------------DODAWANIE FILMU---------------------------------

CREATE OR REPLACE PROCEDURE insertMovie (in_title varchar2,in_length number, in_distributor varchar2,
    in_borrow_date date, in_return_date date, in_dub_sub_lec char,in_is3D number)   
AS
BEGIN
    INSERT INTO Movies
    (title, length, distributor, borrow_date, return_date, dub_sub_lec, is3D)
    VALUES 
    (in_title, in_length, in_distributor, in_borrow_date, in_return_date, in_dub_sub_lec, in_is3D);
    
    COMMIT;
END;
/


--------------------------------DODAWANIE SEANSU--------------------------------

CREATE OR REPLACE PROCEDURE insertSeance (in_start_time date, in_end_time date, 
    in_ticket_price number, in_id_movie number, in_id_screening_room number)    
AS
    movie_length number; 
    new_end_time date;
BEGIN
    SELECT length INTO movie_length FROM movies WHERE id_movie = in_id_movie;
    
    IF in_end_time - in_start_time > movie_length/1440 THEN
        INSERT INTO Seances
        (start_time, end_time, ticket_price, id_movie, id_screening_room)
        VALUES 
        (in_start_time, in_end_time, in_ticket_price, in_id_movie, in_id_screening_room);
    ELSE
        new_end_time := in_start_time + movie_length/1440;
        INSERT INTO Seances
        (start_time, end_time, ticket_price, id_movie, id_screening_room)
        VALUES 
        (in_start_time, new_end_time, in_ticket_price, in_id_movie, in_id_screening_room);
    END IF;
    
    COMMIT;
END;
/


---------------------------------DODAWANIE SALI---------------------------------

CREATE OR REPLACE PROCEDURE insertScreeningRoom (in_amount_of_rows number, 
    in_amount_of_columns number)    
AS
    in_amount_of_seats number;
BEGIN
    in_amount_of_seats := in_amount_of_rows*in_amount_of_columns;
    INSERT INTO ScreeningRooms
    (amount_of_seats, amount_of_rows, amount_of_columns)
    VALUES 
    (in_amount_of_seats, in_amount_of_rows, in_amount_of_columns);
    
    COMMIT;
END;
/


--------------------------------DODAWANIE KLIENTA-------------------------------

CREATE OR REPLACE PROCEDURE insertCustomer (in_login varchar2, in_password varchar2, in_name varchar2,
    in_surname varchar2, in_phone_number varchar2)    
AS
BEGIN
    INSERT INTO Customers
    (login, password, name, surname, phone_number, is_active)
    VALUES 
    (in_login, in_password, in_name, in_surname, in_phone_number, 1);
    
    COMMIT;
END;
/


------------------------------DODAWANIE PRACOWNIKA------------------------------

CREATE OR REPLACE PROCEDURE insertEmployee ( in_login varchar2, in_password varchar2, in_name varchar2,
	in_surname varchar2, in_phone_number varchar2, in_hire_date date, in_id_position number)    
AS
BEGIN
    INSERT INTO Employees
    (login, password, name, surname, phone_number, hire_date, id_position)
    VALUES 
    (in_login, in_password, in_name, in_surname, in_phone_number, in_hire_date, in_id_position);
    
    COMMIT;
END;
/


------------------------------DODAWANIE STANOWISKA------------------------------

CREATE OR REPLACE PROCEDURE insertPosition (in_name varchar2, in_salary number)    
AS
BEGIN
    INSERT INTO Positions (name, salary)
    VALUES (in_name, in_salary);
    
    COMMIT;
END;
/



---------------------------DEZAKTYWACJA KONTA KLIENTA---------------------------

CREATE OR REPLACE PROCEDURE customerAccountDeactivation (in_id_customer number)    
AS
BEGIN
    UPDATE Customers
    SET is_active = 0
    WHERE id_customer = in_id_customer;
    
    COMMIT;
END;
/


----------------------MODYFIKACJA DANYCH KLIENTA-----------------------

CREATE OR REPLACE PROCEDURE changeCustomer (in_id_customer number, in_password varchar2,
	in_name varchar2, in_surname varchar2, in_phone_number varchar2)
AS
    not_null_password varchar2(50 BYTE);
BEGIN
    IF in_password is not null THEN
        UPDATE Customers
        SET  password = in_password
    WHERE id_customer = in_id_customer;
    END IF;

    UPDATE Customers
    SET  name = in_name,
    surname = in_surname,
    phone_number = in_phone_number
    WHERE id_customer = in_id_customer;
    
    COMMIT;
END;
/

-----------------------------ZWOLNIENIE PRACOWNIKA------------------------------

CREATE OR REPLACE PROCEDURE fireEmployee (in_id_employee number, in_fire_date date)    
AS
BEGIN
    UPDATE Employees
    SET fire_date = in_fire_date
    WHERE id_employee = in_id_employee;
    
    COMMIT;
END;
/


-------------------MODYFIKACJA NAZWY STANOWISKA PRACOWNICZEGO-------------------

CREATE OR REPLACE PROCEDURE changePositionName (in_id_position number, in_name varchar2)    
AS
BEGIN
    UPDATE Positions
    SET name = in_name
    WHERE id_position = in_id_position;
    
    COMMIT;
END;
/

-------------------MODYFIKACJA PENSJI STANOWISKA PRACOWNICZEGO------------------

CREATE OR REPLACE PROCEDURE changePositionSalary (in_id_position number, in_salary number)    
AS
BEGIN
    UPDATE Positions
    SET salary = in_salary
    WHERE id_position = in_id_position;
    
    COMMIT;
END;
/

---------------------------ZMIANA REZERWACJI NA ZAKUP---------------------------

CREATE OR REPLACE PROCEDURE changeReservationToTeaken (in_id_seat number, in_id_customer number)    
AS
BEGIN
    UPDATE TeakenSeats
    SET reserved_or_teaken  = 'T'
    WHERE id_seat = in_id_seat AND id_customer = in_id_customer;
    
    COMMIT;
END;
/

------------------------------USUNIECIE REZERWACJI------------------------------

CREATE OR REPLACE PROCEDURE deleteReservation (in_id_seat number, in_id_customer number)    
AS
BEGIN
    DELETE FROM TeakenSeats 
    WHERE (id_seat = in_id_seat AND id_customer = in_id_customer AND reserved_or_teaken  = 'R');
    
    COMMIT;
END;
/

------------------------------LOGOWANIE UZYTKOWNIKA-----------------------------
CREATE OR REPLACE FUNCTION logInCustomer(in_login IN VARCHAR2, in_password IN VARCHAR2)
RETURN NUMBER 
IS
    out_customer_id NUMBER(10);
BEGIN
    SELECT id_customer INTO out_customer_id FROM Customers WHERE login = in_login AND password = in_password AND is_active = 1;
    RETURN out_customer_id;
END;
/

------------------------------LOGOWANIE PRACOWNIKA------------------------------
CREATE OR REPLACE FUNCTION logInEmployee(in_login IN VARCHAR2, in_password IN VARCHAR2)
RETURN NUMBER 
IS
    out_employee_id NUMBER(10);
BEGIN
    SELECT id_employee INTO out_employee_id FROM Employees WHERE login = in_login AND password = in_password AND fire_date IS NULL;
    RETURN out_employee_id;
END;
/

---------------------------SPRAWDZENIE CZY KIEROWNIK----------------------------
CREATE OR REPLACE FUNCTION isEmployeeAnManager(in_id_employee IN NUMBER)
RETURN NUMBER 
IS
    manager_position_id NUMBER(10);
    employee_position_id NUMBER(10);
BEGIN
    SELECT id_position INTO manager_position_id FROM Positions WHERE name = 'kierownik' OR name = 'Kierownik' OR name = 'KIEROWNIK';
    SELECT id_position INTO employee_position_id FROM Employees WHERE id_employee = in_id_employee;
    IF (employee_position_id = manager_position_id)THEN
        RETURN 1;
    END IF;
    RETURN 0;
END;
/
--------------------------Poranie id najowszej tranzakcji ----------------------

CREATE OR REPLACE FUNCTION getTransactionId   
RETURN NUMBER
AS
    out_transaction_id NUMBER(10);
BEGIN
    
    select TRANSACTIONS_ID_SEQ.currval INTO out_transaction_id from dual;
    RETURN out_transaction_id;
    
END;
/

--------------------------TWORZENIE WIDOKU KLIENTOW ----------------------------
CREATE OR REPLACE VIEW ViewCustomers AS 
SELECT ID_CUSTOMER , NAME, SURNAME, PHONE_NUMBER, IS_ACTIVE FROM c##cinema.CUSTOMERS
WITH READ ONLY;
/

--------------------------TWORZENIE WIDOKU PRACOWNIKOW -------------------------
CREATE OR REPLACE VIEW ViewEmployees AS 
SELECT ID_EMPLOYEE , NAME, SURNAME, PHONE_NUMBER, HIRE_DATE, FIRE_DATE, ID_POSITION FROM c##cinema.EMPLOYEES
WITH READ ONLY;

--------------------------TWORZENIE WIDOKU FILMOW ------------------------------
CREATE OR REPLACE VIEW ViewMovies AS 
SELECT * FROM c##cinema.MOVIES WHERE return_date >= (SELECT SYSDATE FROM DUAL)
WITH READ ONLY;

--------------------------TWORZENIE WIDOKU POZYCJI -----------------------------
CREATE OR REPLACE VIEW ViewPositions AS 
SELECT * FROM c##cinema.POSITIONS
WITH READ ONLY;

--------------------------TWORZENIE WIDOKU SAL ---------------------------------
CREATE OR REPLACE VIEW ViewScreeningrooms AS 
SELECT * FROM c##cinema.SCREENINGROOMS
WITH READ ONLY;

--------------------------TWORZENIE WIDOKU SEANSOW -----------------------------
CREATE OR REPLACE VIEW ViewSeances AS 
SELECT * FROM c##cinema.SEANCES
WHERE end_time > (SELECT SYSDATE FROM DUAL)
WITH READ ONLY;

--------------------------TWORZENIE WIDOKU SIEDZEN -----------------------------
CREATE OR REPLACE VIEW ViewTeakenseats AS 
SELECT * FROM c##cinema.TEAKENSEATS
WITH READ ONLY;

--------------------------TWORZENIE WIDOKU Tranzakcji --------------------------
CREATE OR REPLACE VIEW ViewTransactions AS 
SELECT * FROM c##cinema.TRANSACTIONS
WITH READ ONLY;

--------------------------TWORZENIE UZYTKOWNIKA---------------------------------
create user c##cinema_user identified by cinema_user;
GRANT CONNECT TO c##cinema_user;

---uprawnienia do procedur---
GRANT execute on changeCustomer to c##cinema_user;
GRANT execute on changePositionName to c##cinema_user;
GRANT execute on changePositionSalary to c##cinema_user;
GRANT execute on changeReservationToTeaken to c##cinema_user;
GRANT execute on customerAccountDeactivation to c##cinema_user;
GRANT execute on deleteReservation to c##cinema_user;
GRANT execute on fireEmployee to c##cinema_user;
GRANT execute on insertCustomer to c##cinema_user;
GRANT execute on insertEmployee to c##cinema_user;
GRANT execute on insertMovie to c##cinema_user;
GRANT execute on insertPosition to c##cinema_user;
GRANT execute on insertResTeakenSeats to c##cinema_user;
GRANT execute on insertScreeningRoom to c##cinema_user;
GRANT execute on insertSeance to c##cinema_user;
GRANT execute on insertTeakenSeats to c##cinema_user;
GRANT execute on insertTransactions to c##cinema_user;
GRANT execute on logInCustomer to c##cinema_user;
GRANT execute on logInEmployee to c##cinema_user;
GRANT execute on isEmployeeAnManager to c##cinema_user;
GRANT execute on getTransactionId to c##cinema_user;

---uprawnienia do widokow---
GRANT select on ViewCustomers to c##cinema_user;
GRANT select on ViewEmployees to c##cinema_user;
GRANT select on ViewMovies to c##cinema_user;
GRANT select on ViewPositions to c##cinema_user;
GRANT select on ViewScreeningrooms to c##cinema_user;
GRANT select on ViewSeances to c##cinema_user;
GRANT select on ViewTeakenseats to c##cinema_user;
GRANT select on ViewTransactions to c##cinema_user;


/*
--------------------------------DODAWANIE FILMU---------------------------------
EXECUTE insertMovie('Superman',140, 'xx distributor', to_date('2022-01-02', 'YYYY-MM-DD'), to_date('2022-02-04', 'YYYY-MM-DD'), 'D', 0);
EXECUTE insertMovie('Spiderman',120, 'xx distributor', to_date('2022-01-04', 'YYYY-MM-DD'), to_date('2022-02-04', 'YYYY-MM-DD'), 'D', 0);
--SELECT * FROM Movies;


---------------------------------DODAWANIE SALI---------------------------------
EXECUTE insertScreeningRoom (8, 10);
EXECUTE insertScreeningRoom (7, 10);
EXECUTE insertScreeningRoom (8, 10);
--SELECT * FROM ScreeningRooms;


--------------------------------DODAWANIE SEANSU--------------------------------
EXECUTE insertSeance(to_date('2022-02-01-12-00', 'YYYY-MM-DD-HH24-MI'), to_date('2022-02-01-14-50', 'YYYY-MM-DD-HH24-MI'), 15, 1, 2);
EXECUTE insertSeance(to_date('2022-02-01-15-00', 'YYYY-MM-DD-HH24-MI'), to_date('2022-02-01-19-00', 'YYYY-MM-DD-HH24-MI'), 20, 2, 1);
--SELECT * FROM Seances;

------------------------------DODAWANIE STANOWISKA------------------------------
EXECUTE insertPosition('sprzedawca', 3000);
EXECUTE insertPosition('kierownik', 7000);
--SELECT * FROM Positions;


------------------------------DODAWANIE PRACOWNIKA------------------------------
EXECUTE insertEmployee('empl1', 'sed3', 'Julia', 'Nowako', '657345903', to_date('2020-01-02', 'YYYY-MM-DD'), 2);
EXECUTE insertEmployee('empl2', 'pwe4', 'Pawel', 'Towak', '923401903', to_date('2021-06-02', 'YYYY-MM-DD'), 1);
EXECUTE insertEmployee('empl3', 'p554', 'Julian', 'Nowak', '567401903', to_date('2021-06-02', 'YYYY-MM-DD'), 1);
--SELECT * FROM Employees;


--------------------------------DODAWANIE KLIENTA-------------------------------
EXECUTE insertCustomer('sdsd4', 's3erf', 'Jan', 'Nowak', '758345903');
EXECUTE insertCustomer('8dsdf', '45rrrf', 'Pawel', 'Nowak', '958301903');
EXECUTE insertCustomer('555df', '4678rf', 'Tomasz', 'Nowak', '954561903');
--SELECT * FROM Customers;


------------------------------DODAWANIE TRANSAKCJI------------------------------
EXECUTE insertTransactions(30, 2, 0, 1, 1);
EXECUTE insertTransactions(20, 1, 0, 2, 2);
--SELECT * FROM Transactions;


-----------------------DODAWANIE ZAREZERWOWANEGO MIEJSCA------------------------
EXECUTE insertResTeakenSeats('A', 5, 1, 1);
EXECUTE insertResTeakenSeats('A', 6, 1, 1);
EXECUTE insertResTeakenSeats('B', 4, 2, 2);
--SELECT * FROM TeakenSeats;


-------------------------DODAWANIE KUPIONEGO MIEJSCA----------------------------
EXECUTE insertTeakenSeats('A', 7, 1, NULL, NULL);
EXECUTE insertTeakenSeats('B', 2, 1, NULL, NULL);
EXECUTE insertTeakenSeats('B', 3, 1, NULL, NULL);
--SELECT * FROM TeakenSeats;


---------------------------DEZAKTYWACJA KONTA KLIENTA---------------------------
EXECUTE customerAccountDeactivation(2);
--SELECT * FROM Customers;


----------------------MODYFIKACJA DANYCH KLIENTA-----------------------
EXECUTE changeCustomer(3,'xxxxx', 'Juliusz', 'Slowacki', '888561903');
--SELECT * FROM Customers;


-----------------------------ZWOLNIENIE PRACOWNIKA------------------------------
EXECUTE fireEmployee(3, to_date('2022-01-02', 'YYYY-MM-DD'));
--SELECT * FROM Employees;


-------------------MODYFIKACJA NAZWY STANOWISKA PRACOWNICZEGO-------------------
EXECUTE changePositionName(1, 'kier1');
--SELECT * FROM Positions;


-------------------MODYFIKACJA PENSJI STANOWISKA PRACOWNICZEGO------------------
EXECUTE changePositionSalary(1, 4550);
--SELECT * FROM Positions;


---------------------------ZMIANA REZERWACJI NA ZAKUP---------------------------
EXECUTE changeReservationToTeaken(3, 2);
--SELECT * FROM TeakenSeats;


------------------------------USUNIECIE REZERWACJI------------------------------
EXECUTE deleteReservation(2, 1);
--SELECT * FROM TeakenSeats;

------------------------------LOGOWANIE UZYTKOWNIKA-----------------------------
SELECT logInCustomer('sdsd4', 's3erf') FROM DUAL;
SELECT logInCustomer('8dsdf', '45rrrf') FROM DUAL;

------------------------------LOGOWANIE PRACOWNIKA------------------------------
SELECT logInEmployee('empl3', 'p554') FROM DUAL;
SELECT logInEmployee('empl1', 'sed3') FROM DUAL;

---------------------------SPRAWDZENIE CZY KIEROWNIK----------------------------
SELECT isEmployeeAnManager(1) FROM DUAL;
SELECT isEmployeeAnManager(2) FROM DUAL;
SELECT isEmployeeAnManager(3) FROM DUAL;

*/