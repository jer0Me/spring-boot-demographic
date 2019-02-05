CREATE TABLE PERSON (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
	NAME VARCHAR(255) NOT NULL,
	PPSN VARCHAR(8) NOT NULL,
	DATE_OF_BIRTH DATE,
	MOBILE_PHONE VARCHAR(9),
	CREATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE SEQUENCE PERSON_SEQUENCE START WITH 1 INCREMENT BY 1;
