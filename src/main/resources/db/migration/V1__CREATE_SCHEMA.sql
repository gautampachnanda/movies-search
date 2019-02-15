CREATE SEQUENCE GENRES_SEQ START 1;
CREATE SEQUENCE PROGRAMMES_SEQ START 1;

CREATE TABLE PROGRAMME (
	ID bigint not null,
  	IMDB_ID varchar(255) not null,
	TITLE varchar(255) not null,
	TITLE_TYPE varchar(255) not null,
	PRIMARY_TITLE varchar(255) not null,
	ORIGINAL_TITLE varchar(255) not null,
	START_YEAR integer,
	END_YEAR integer ,
	RUNTIME_MINUTES integer,
	ADULT boolean not null,
	PRIMARY KEY (ID)
);

CREATE TABLE GENRE(
	ID bigint not null,
	PROGRAMME_ID bigint not null,
	NAME varchar(255) not null,
	FOREIGN KEY (PROGRAMME_ID) REFERENCES PROGRAMME (ID),
	PRIMARY KEY (ID)
);