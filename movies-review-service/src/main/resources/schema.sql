CREATE SEQUENCE primary_key;

DROP TABLE IF EXISTS review;

CREATE TABLE review (
    id INTEGER PRIMARY KEY,
    movie_info_id INTEGER,
    review_comment VARCHAR(255),
    rating BIGINT,
    PRIMARY KEY (id)
);