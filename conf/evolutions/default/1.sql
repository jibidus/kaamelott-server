# Users schema
 
# --- !Ups
 
CREATE TABLE Sentence (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    text varchar(255) NOT NULL,
    chatacter_id bigint(20) NOT NULL,
    episode_id bigint(20) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Character (
    id varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Episode (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    book varchar(10) NOT NULL,
    number int NOT NULL,
    title varchar(255) NOT NULL,
    PRIMARY KEY (id)
);
 
# --- !Downs
 
DROP TABLE Sentence;
DROP TABLE Character;
DROP TABLE Episode;