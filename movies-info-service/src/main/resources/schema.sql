CREATE SEQUENCE primary_key;

DROP TABLE IF EXISTS movie_info;

CREATE TABLE movie_info (
    id INTEGER PRIMARY KEY,
    movie_name VARCHAR(255),
    release_year INTEGER,
    release_date DATE,
    PRIMARY KEY (id)
);